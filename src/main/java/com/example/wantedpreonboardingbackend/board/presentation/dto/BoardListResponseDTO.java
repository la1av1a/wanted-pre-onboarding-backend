package com.example.wantedpreonboardingbackend.board.presentation.dto;

import java.time.LocalDateTime;

public record BoardListResponseDTO(Long boardId, String author, String title, String content,
                                   LocalDateTime createdDate, LocalDateTime lastModifiedDate){

}
