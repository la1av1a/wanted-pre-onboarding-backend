package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardFindingResponseDTO;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardPostingRequestDTO;
import com.example.wantedpreonboardingbackend.common.exception.NoUpdateContentException;
import com.example.wantedpreonboardingbackend.member.application.MemberCRUDService;
import com.example.wantedpreonboardingbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public BoardFindingResponseDTO findBoardById(Long id){
        return boardCRUDService.findBoardById(id).toResponseDTO();
    }

    @Transactional
    public void updateBoard(Long id,String title,String content,Long memberId){
        Board board = boardCRUDService.findBoardByBoardIdAndMemberId(id,memberId);

        if(title == null && content == null){
            throw new NoUpdateContentException("수정할 내용이 없습니다.");
        }

        if(title != null){
            board.updateTitle(title);
        }

        if(content != null){
            board.updateContent(content);
        }
    }
}
