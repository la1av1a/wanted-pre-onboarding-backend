package com.example.wantedpreonboardingbackend.member.presentation;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.wantedpreonboardingbackend.member.presentation.dto.MemberSignUpRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void memberSignUp() throws Exception {

        //given
        String memberName = "test@example.com";
        String memberPw = "1234";

        MemberSignUpRequestDTO requestDTO = new MemberSignUpRequestDTO(memberName, memberPw);

        //when
        ResultActions expectOk = request("/member/signUp", writeValueAsString(requestDTO));
        ResultActions expectConflict = request("/member/signUp", writeValueAsString(requestDTO));
        //then
        expectOk.andExpect(status().isOk());
        expectConflict.andExpect(status().isConflict());
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