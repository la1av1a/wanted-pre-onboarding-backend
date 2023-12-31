package com.example.wantedpreonboardingbackend.member.application;

import com.example.wantedpreonboardingbackend.common.exception.MemberNotFoundException;
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

    @Transactional(readOnly = true)
    public Member findMemberByMemberName(String memberName){
        return memberRepository.findMemberByMemberName(memberName).orElseThrow
            (()->new MemberNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Member findMemberById(Long id){
        return memberRepository.findMemberByMemberId(id).orElseThrow
            (()->new MemberNotFoundException("존재하지 않는 유저입니다."));
    }
}
