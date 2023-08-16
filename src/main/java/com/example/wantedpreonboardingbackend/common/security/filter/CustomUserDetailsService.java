package com.example.wantedpreonboardingbackend.common.security.filter;

import com.example.wantedpreonboardingbackend.common.security.CustomUserDetails;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        Member member = memberRepository.findMemberByMemberName(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return new CustomUserDetails(String.valueOf(member.getMemberId()),username,null, Role.ROLE_USER,null);
    }
}
