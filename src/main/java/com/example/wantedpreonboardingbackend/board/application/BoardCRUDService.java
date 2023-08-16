package com.example.wantedpreonboardingbackend.board.application;

import com.example.wantedpreonboardingbackend.board.domain.Board;
import com.example.wantedpreonboardingbackend.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardCRUDService {

    private final BoardRepository boardRepository;

    public void saveBoard(Board board){
        boardRepository.save(board);
    }
}
