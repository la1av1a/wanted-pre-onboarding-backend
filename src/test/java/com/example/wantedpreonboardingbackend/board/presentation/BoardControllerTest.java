package com.example.wantedpreonboardingbackend.board.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardPostingRequestDTO;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepositoryTest;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Import(MemberRepositoryTest.class)
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(){
        Member member = new Member("test@exmaple.com","12345678", Role.ROLE_USER);
        memberRepository.save(member);
    }

    @WithUserDetails(value = "test@exmaple.com",setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void posting() throws Exception {
        //given
        String expectedTitle = "test title";
        String expectedContent = "test content";

        BoardPostingRequestDTO requestDTO = new BoardPostingRequestDTO(expectedTitle,expectedContent);

        //when
        ResultActions resultActions = mockMvc.perform(post("/board/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestDTO)));

        //then
        resultActions.andExpect(status().isOk());
    }
}