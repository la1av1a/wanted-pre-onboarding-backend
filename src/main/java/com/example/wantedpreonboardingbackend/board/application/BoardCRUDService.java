package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardCRUDService {

    private final BoardRepository boardRepository;

    @Transactional
    public void saveBoard(Board board){
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Board findBoardById(Long id){
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Board findBoardByBoardIdAndMemberId(Long boardId,Long memberId){
        return boardRepository.findBoardByIdAndAuthorMemberId(boardId,memberId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }
}
