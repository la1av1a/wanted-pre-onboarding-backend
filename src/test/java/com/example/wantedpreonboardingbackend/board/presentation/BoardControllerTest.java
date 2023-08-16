package com.example.wantedpreonboardingbackend.board.presentation;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.domain.BoardRepository;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardPostingRequestDTO;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepository;
import com.example.wantedpreonboardingbackend.member.domain.MemberRepositoryTest;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Import(MemberRepositoryTest.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Member member;
    @BeforeEach
    public void setup(){
        member = new Member("test@exmaple.com","12345678", Role.ROLE_USER);
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

    @Nested
    class PatchBoard {


        Board board;
        @BeforeEach
        void setup(){
            board = new Board(member,"original title","original content");
            boardRepository.save(board);
        }

        @WithUserDetails(value = "test@exmaple.com",setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        void patchBoardTitle() throws Exception {
            //given
            String expectedTitle = "updated title";

            //when
            mockMvc.perform(patch("/board/{boardId}",board.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("title",expectedTitle))
                .andExpect(status().isOk());

            Board savedBoard = boardRepository.findBoardByIdAndAuthorMemberId(board.getId(),member.getMemberId())
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));

            //then
            Assertions.assertThat(savedBoard.getTitle()).isEqualTo(expectedTitle);
        }

        @WithUserDetails(value = "test@exmaple.com",setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        void patchBoardContent() throws Exception {
            //given
            String expectedContent = "updated content";

            //when
            mockMvc.perform(patch("/board/{boardId}",board.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("content",expectedContent))
                .andExpect(status().isOk());

            Board savedBoard = boardRepository.findBoardByIdAndAuthorMemberId(board.getId(),member.getMemberId())
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다."));

            //then
            Assertions.assertThat(savedBoard.getContent()).isEqualTo(expectedContent);
        }

        @WithUserDetails(value = "test@exmaple.com",setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        void patchBoardAnyThing() throws Exception {

            //when
            ResultActions resultActions = mockMvc.perform(patch("/board/{boardId}",board.getId())
                    .contentType(MediaType.APPLICATION_JSON));

            //then
            resultActions.andExpect(status().isUnprocessableEntity());
        }
    }

    @Nested
    class PageableTest{

        @BeforeEach
        void setUp(){
            for(int i=0;i<22;i++){
                setBoard("title"+i,"content"+i);
            }
        }

        @Test
        void firstPageTest() throws Exception {
            mockMvc.perform(get("/board/")
                    .param("size","10"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("title21"));
        }

        @Test
        void middlePageTest() throws Exception {
            mockMvc.perform(get("/board/")
                    .param("size","10")
                    .param("page","1")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("title11"));
        }

        @Test
        void lastPageTest() throws Exception {
            mockMvc.perform(get("/board/")
                    .param("size","10")
                    .param("page","2")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2));
        }

        @Nested
        class DeleteBoard{

            @Test
            @WithUserDetails(value = "test@exmaple.com",setupBefore = TestExecutionEvent.TEST_EXECUTION)
            void deleteExpectSuccess() throws Exception {
                //given
                Board savedBoard = setBoard("title","content");

                //when
                mockMvc.perform(delete("/board/{boardId}",savedBoard.getId()))
                    .andExpect(status().isOk());

                //then
                Assertions.assertThat(boardRepository.findById(savedBoard.getId()).isEmpty()).isTrue();
            }
            @Test
            @WithUserDetails(value = "test@exmaple.com",setupBefore = TestExecutionEvent.TEST_EXECUTION)
            void deleteExpectFail() throws Exception {
                //when
                ResultActions resultActions = mockMvc.perform(delete("/board/{boardId}",65550505L));

                //then
                resultActions.andExpect(status().isNotFound());
            }
        }

        private Board setBoard(String title,String content){
            Board board = new Board(member,title,content);
            return boardRepository.save(board);
        }
    }
}