package com.example.wantedpreonboardingbackend.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {

    boolean existsMemberByMemberName(String user);
}
