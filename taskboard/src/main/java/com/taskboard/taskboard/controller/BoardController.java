package com.taskboard.taskboard.controller;

import com.taskboard.taskboard.dto.BoardRequestDTO;
import com.taskboard.taskboard.dto.BoardResponseDTO;
import com.taskboard.taskboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDTO> createBoard(
            @RequestBody BoardRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        BoardResponseDTO response = boardService.createBoard(request, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponseDTO>> getAllBoards(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<BoardResponseDTO> boards = boardService.getAllBoards(userDetails.getUsername());
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> getBoardById(
            @PathVariable UUID boardId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        BoardResponseDTO board = boardService.getBoardById(boardId, userDetails.getUsername());
        return ResponseEntity.ok(board);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponseDTO> updateBoard(
            @PathVariable UUID boardId,
            @RequestBody BoardRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        BoardResponseDTO updatedBoard = boardService.updateBoard(boardId, request, userDetails.getUsername());
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable UUID boardId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        boardService.deleteBoard(boardId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
