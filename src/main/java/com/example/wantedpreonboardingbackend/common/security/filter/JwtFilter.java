package com.example.wantedpreonboardingbackend.common.security.filter;

import com.example.wantedpreonboardingbackend.common.jwt.JwtUtil;
import com.example.wantedpreonboardingbackend.common.response.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromHeader(request);
            if (StringUtils.hasText(jwt) && jwtUtil.validateToken(jwt)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtUtil.getAuthenticationFromToken(jwt).getPrincipal(), null, jwtUtil.getAuthenticationFromToken(jwt).getAuthorities());

                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ResponseEntity<ResponseDTO<Void>> responseDto = new ResponseEntity(new ResponseDTO<>(null, ex.getMessage()), HttpStatus.UNAUTHORIZED);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writer().writeValue(response.getWriter(), responseDto);
            return;
        }
        filterChain.doFilter(request, response);
    }

    protected String getJwtFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }
}
