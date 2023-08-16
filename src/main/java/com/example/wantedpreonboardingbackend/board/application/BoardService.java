package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardPostingRequestDTO;
import com.example.wantedpreonboardingbackend.member.application.MemberCRUDService;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardCRUDService boardCRUDService;
    private final MemberCRUDService memberCRUDService;

    public void posting(BoardPostingRequestDTO requestDTO,Long id){
        Member member = memberCRUDService.findMemberById(id);
        Board board = createBoard(member,requestDTO.title(),requestDTO.content());

        boardCRUDService.saveBoard(board);
    }

    public Board createBoard(Member author,String title,String content){

        return new Board(author,title,content);
    }
}
