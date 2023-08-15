package com.example.wantedpreonboardingbackend.member.presentation;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.wantedpreonboardingbackend.common.security.filter.PasswordEncoderConfig;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

@Import(PasswordEncoderConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void memberSignUp() throws Exception {

        //given
        String memberName = "test@example.com";
        String memberPw = "12345678";

        MemberRequestDTO requestDTO = new MemberRequestDTO(memberName, memberPw);

        //when
        ResultActions expectOk = request("/member/signUp", writeValueAsString(requestDTO));
        ResultActions expectConflict = request("/member/signUp", writeValueAsString(requestDTO));
        //then
        expectOk.andExpect(status().isOk());
        expectConflict.andExpect(status().isConflict());
    }

    @Test
    void memberSignIn() throws Exception {

        //given
        String memberName = "test1234@gmail.com";
        String memberPw = "12345678";

        MemberRequestDTO requestDTO = new MemberRequestDTO(memberName, memberPw);
        memberRepository.save(MemberRequestDTO.toEntity(requestDTO.memberName(),passwordEncoder.encode(requestDTO.memberPw()),
            Role.ROLE_USER));

        //when
        ResultActions expectOk = request("/member/signIn", writeValueAsString(requestDTO));

        //then
        expectOk.andExpect(status().isOk());
    }

    ResultActions request(String url, String content) throws Exception {

        return mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(content));
    }

    String writeValueAsString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}