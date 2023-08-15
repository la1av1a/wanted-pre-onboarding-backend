package com.example.wantedpreonboardingbackend.member.domain;

import com.example.wantedpreonboardingbackend.common.security.filter.PasswordEncoderConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

@Import(PasswordEncoderConfig.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void existsUserByUserName() {

        //given
        Member member = createMember("example@example.com",passwordEncoder.encode("12345678"),Role.ROLE_USER);
        save(member);

        String testUserName = "example@example.com";
        String notExistUserName = "notexist@example.com";

        //when
        boolean expectExist = memberRepository.existsMemberByMemberName(testUserName);
        boolean expectNotExist = memberRepository.existsMemberByMemberName(notExistUserName);

        //then
        Assertions.assertThat(expectExist).isTrue();
        Assertions.assertThat(expectNotExist).isFalse();
    }

    @Test
    void memberSaveTest(){
        //given
        String memberName = "test@example.com";
        String memberPw = "password";
        Member member = createMember(memberName,memberPw,Role.ROLE_USER);

        //when
        Member savedMember = save(member);

        //then
        Assertions.assertThat(savedMember.getMemberName()).isEqualTo(memberName);
        Assertions.assertThat(passwordEncoder.matches(memberPw,savedMember.getMemberPw())).isTrue();
        Assertions.assertThat(savedMember.getRole()).isEqualTo(Role.ROLE_USER);
    }

    Member save(Member member){
       return memberRepository.save(member);
    }

    Member createMember(String memberName, String password,Role role){

        return new Member(memberName,passwordEncoder.encode(password),role);
    }
}