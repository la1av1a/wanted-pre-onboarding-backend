package com.example.wantedpreonboardingbackend.board.presentation.dto;

import java.time.LocalDateTime;

public record BoardFindingResponseDTO (Long id, String title, String content, String author, LocalDateTime createdDate, LocalDateTime modifiedDate){

}
