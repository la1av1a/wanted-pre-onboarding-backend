package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.domain.BoardRepository;
import com.example.wantedpreonboardingbackend.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
        return boardRepository.findById(id).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Board findBoardByBoardIdAndMemberId(Long boardId,Long memberId){
        return boardRepository.findBoardByIdAndAuthorMemberId(boardId,memberId).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<Board> findBoardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public void deleteBoard(Board board){
        boardRepository.delete(board);
    }
}
