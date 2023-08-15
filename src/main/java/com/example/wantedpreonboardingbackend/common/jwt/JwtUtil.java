package com.example.wantedpreonboardingbackend.common.jwt;

import com.example.wantedpreonboardingbackend.common.security.CustomUserDetails;
import com.example.wantedpreonboardingbackend.common.security.exception.UnAuthorizedException;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final JwtProvider jwtProvider;

    public String createToken(Subject subject) {
        Date now = new Date();
        long tokenValidity = jwtProvider.getTokenLive();

        return Jwts.builder()
            .setIssuedAt(now)
            .setSubject(subject.getId().toString())
            .claim("memberName", subject.getMemberName())
            .claim("role", subject.getRole())
            .setIssuedAt(new Date())
            .setExpiration(new Date(now.getTime() + tokenValidity))
            .signWith(jwtProvider.getSecretKey())
            .compact();
    }

    public Authentication getAuthenticationFromToken(String token) {
        Claims claims = extractClaimsFromToken(token);

        Subject subject = extractSubjectFromClaims(claims);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(subject.getRole().toString()));

        CustomUserDetails principal = CustomUserDetails.builder()
            .id(String.valueOf(subject.getId()))
            .userName(subject.getMemberName())
            .role(subject.getRole())
            .accessTokenExpiration(claims.getExpiration().getTime())
            .createdAt(claims.getIssuedAt())
            .role(subject.getRole())
            .build();

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }

    public Claims extractClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(jwtProvider.getSecretKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Subject extractSubjectFromClaims(Claims claims) {
        Long id = Long.valueOf(claims.getSubject());
        String memberName = claims.get("memberName", String.class);
        String role = claims.get("role", String.class);
        checkAuthorities(claims);

        return Subject.builder()
            .id(id)
            .memberName(memberName)
            .role(Role.valueOf(role))
            .build();
    }

    private void checkAuthorities(Claims claims) throws UnAuthorizedException {
        if (claims.get("role") == null) {
            throw new UnAuthorizedException("클레임이 존재하지 않습니다 클레임 : role");
        }
    }

    public boolean validateToken(String token) throws UnAuthorizedException {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtProvider.getSecretKey())
                .build()
                .parseClaimsJws(token);

        } catch (SignatureException | MalformedJwtException e) {
            e.printStackTrace();
            throw new UnAuthorizedException("유효하지 않은 토큰입니다");
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            throw new UnAuthorizedException("만료된 토큰입니다");
        } catch (JwtException e) {
            e.printStackTrace();
            throw new UnAuthorizedException("JWT 검증 중 예기치 않은 오류가 발생했습니다");
        }
        return true;
    }

    public Long getTokenLive() {
        return jwtProvider.getTokenLive();
    }
}
