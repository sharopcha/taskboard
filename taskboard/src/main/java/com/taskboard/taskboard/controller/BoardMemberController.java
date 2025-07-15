package com.taskboard.taskboard.controller;

import com.taskboard.taskboard.dto.BoardMemberRequestDTO;
import com.taskboard.taskboard.dto.BoardMemberResponseDTO;
import com.taskboard.taskboard.service.BoardMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards/{boardId}/members")
@RequiredArgsConstructor
public class BoardMemberController {

    private final BoardMemberService boardMemberService;

    @PostMapping
    public ResponseEntity<BoardMemberResponseDTO> addMember(
            @PathVariable UUID boardId,
            @RequestBody BoardMemberRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        BoardMemberResponseDTO response = boardMemberService.addMember(boardId, request, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BoardMemberResponseDTO>> getMembers(
            @PathVariable UUID boardId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<BoardMemberResponseDTO> members = boardMemberService.getBoardMembers(boardId, userDetails.getUsername());
        return ResponseEntity.ok(members);
    }

    @PutMapping
    public ResponseEntity<BoardMemberResponseDTO> updateMemberRole(
            @PathVariable UUID boardId,
            @Validated @RequestBody BoardMemberRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        BoardMemberResponseDTO updatedMember = boardMemberService.updateMemberRole(boardId, request.getUserId(), request, userDetails.getUsername());
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable UUID boardId,
            @PathVariable UUID memberId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        boardMemberService.removeMember(boardId, memberId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
