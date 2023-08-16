package com.example.wantedpreonboardingbackend.board.presentation;

import com.example.wantedpreonboardingbackend.board.application.BoardService;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardPostingRequestDTO;
import com.example.wantedpreonboardingbackend.common.response.ResponseDTO;
import com.example.wantedpreonboardingbackend.common.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시글 Controller
 */

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/")
    public ResponseEntity<ResponseDTO<Void>> posting(
        @RequestBody @Valid BoardPostingRequestDTO requestDTO,
        @AuthenticationPrincipal CustomUserDetails userDetails){

        boardService.posting(requestDTO,Long.parseLong(userDetails.getId()));

        return new ResponseEntity<>(ResponseDTO.ofSuccess("DD"), HttpStatus.OK);
    }
}
