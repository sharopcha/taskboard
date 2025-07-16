package com.taskboard.taskboard.controller;

import com.taskboard.taskboard.dto.ColumnRequestDTO;
import com.taskboard.taskboard.dto.ColumnResponseDTO;
import com.taskboard.taskboard.service.ColumnService;
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
public class ColumnController {

    private final ColumnService columnService;

    @PostMapping("/{boardId}/columns")
    public ResponseEntity<ColumnResponseDTO> createColumn(
            @PathVariable UUID boardId,
            @RequestBody ColumnRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        ColumnResponseDTO response = columnService.createColumn(boardId, request, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{boardId}/columns")
    public ResponseEntity<List<ColumnResponseDTO>> getColumnsForBoard(
            @PathVariable UUID boardId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        List<ColumnResponseDTO> columns = columnService.getColumnsForBoard(boardId, userDetails.getUsername());
        return ResponseEntity.ok(columns);
    }

    @PutMapping("/columns/{columnId}")
    public ResponseEntity<ColumnResponseDTO> updateColumn(
            @PathVariable UUID columnId,
            @RequestBody ColumnRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        ColumnResponseDTO updated = columnService.updateColumn(columnId, request, userDetails.getUsername());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/columns/{columnId}")
    public ResponseEntity<Void> deleteColumn(
            @PathVariable UUID columnId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        columnService.deleteColumn(columnId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
