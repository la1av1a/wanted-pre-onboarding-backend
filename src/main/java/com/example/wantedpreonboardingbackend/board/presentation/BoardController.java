package com.example.wantedpreonboardingbackend.board.presentation;

import com.example.wantedpreonboardingbackend.board.application.BoardService;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardFindingResponseDTO;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardListResponseDTO;
import com.example.wantedpreonboardingbackend.board.presentation.dto.BoardPostingRequestDTO;
import com.example.wantedpreonboardingbackend.common.response.ResponseDTO;
import com.example.wantedpreonboardingbackend.common.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시글 Controller
 */

@Tag(name = "게시글 관련 API", description = "게시글 관련 API")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @Operation(summary = "게시글 작성", description = "게시글 작성 API")
    @PostMapping("/")
    public ResponseEntity<ResponseDTO<Void>> posting(
        @RequestBody @Valid BoardPostingRequestDTO requestDTO,
        @AuthenticationPrincipal CustomUserDetails userDetails){

        boardService.posting(requestDTO,Long.parseLong(userDetails.getId()));

        return new ResponseEntity<>(ResponseDTO.ofSuccess("게시글 작성에 성공하였습니다."), HttpStatus.OK);
    }

    @Operation(summary = "게시글 조회", description = "게시글 조회 API")
    @GetMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<BoardFindingResponseDTO>> getBoard(
        @PathVariable(value = "boardId") Long boardId
    ){
        return new ResponseEntity<>(new ResponseDTO<>(boardService.findBoardById(boardId),
            "게시글 조회에 성공하였습니다."), HttpStatus.OK);
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정 API")
    @PatchMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<Void>> patchBoard(
        @PathVariable(value = "boardId") Long boardId,
        @RequestParam(value = "title", required = false) String title,
        @RequestParam(value = "content", required = false) String content,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        boardService.updateBoard(boardId,title,content,Long.parseLong(userDetails.getId()));

        return new ResponseEntity<>(ResponseDTO.ofSuccess("게시글 수정에 성공하였습니다."), HttpStatus.OK);
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회 API")
    @GetMapping("/")
    public ResponseEntity<ResponseDTO<List<BoardListResponseDTO>>> getBoardList(@PageableDefault(sort = "id",direction = Direction.DESC) Pageable pageable){
        return new ResponseEntity<>(new ResponseDTO<>(boardService.findBoardList(pageable),"게시판 조회에 성공하였습니다."), HttpStatus.OK);
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ResponseDTO<Void>> deleteBoard(
        @PathVariable(value = "boardId") Long boardId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        boardService.deleteBoard(boardId,Long.parseLong(userDetails.getId()));

        return new ResponseEntity<>(ResponseDTO.ofSuccess("게시글 삭제에 성공하였습니다."), HttpStatus.OK);
    }
}
