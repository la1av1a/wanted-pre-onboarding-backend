package com.example.wantedpreonboardingbackend.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {

    boolean existsMemberByMemberName(String user);

    Optional<Member> findMemberByMemberName(String memberName);

    Optional<Member> findMemberByMemberId(Long memberId);
}
