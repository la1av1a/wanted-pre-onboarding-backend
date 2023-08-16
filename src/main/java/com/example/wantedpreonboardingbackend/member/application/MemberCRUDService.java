package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCRUDService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public boolean isMemberExist(String userName){
        return memberRepository.existsMemberByMemberName(userName);
    }

    @Transactional
    public void saveMember(Member member){
        memberRepository.save(member);
    }
}
