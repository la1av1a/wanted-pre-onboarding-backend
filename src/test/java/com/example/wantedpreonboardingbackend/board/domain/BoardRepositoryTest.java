package com.example.wantedpreonboardingbackend.board.domain;

import com.example.wantedpreonboardingbackend.member.domain.Member;
import com.example.wantedpreonboardingbackend.member.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void save() {

        //given
        String memberName = "testName";
        Member member = new Member(memberName,"password", Role.ROLE_USER);

        String title = "testTitle";
        String content = "testContent";
        Board board = new Board(member,title,content);

        //when
        Board savedBoard = boardRepository.save(board);

        //then
        Assertions.assertThat(savedBoard.getId()).isNotNull();
        Assertions.assertThat(savedBoard.getAuthor().getMemberName()).isEqualTo(memberName);
        Assertions.assertThat(savedBoard.getTitle()).isEqualTo(title);
    }

    @Test
    void findById() {
        //given
        String memberName = "testName@example.com";
        Member member = new Member(memberName,"password", Role.ROLE_USER);

        String title = "testTitle";
        String content = "testContent";
        Board board = new Board(member,title,content);

        //when
        Board savedBoard = boardRepository.save(board);

        Board foundBoard = boardRepository.findById(savedBoard.getId()).orElseThrow(()
            -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Assertions.assertThat(foundBoard.getId()).isEqualTo(savedBoard.getId());
        Assertions.assertThat(foundBoard.getAuthor().getMemberName()).isEqualTo(memberName);
        Assertions.assertThat(foundBoard.getTitle()).isEqualTo(title);
    }
}