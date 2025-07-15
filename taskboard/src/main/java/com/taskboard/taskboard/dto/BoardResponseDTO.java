package com.taskboard.taskboard.dto;

import com.taskboard.taskboard.entity.Board;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private UserResponseDTO owner;
    private List<BoardMemberResponseDTO> members;
    private List<ColumnResponseDTO> columns;

    public static BoardResponseDTO map(Board board) {
        if (board == null) {
            return null;
        }
        return BoardResponseDTO.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .owner(board.getOwner() != null ? UserResponseDTO.map(board.getOwner()) : null)
                .members(board.getMembers() != null
                        ? board.getMembers().stream()
                        .map(BoardMemberResponseDTO::map)
                        .toList()
                        : Collections.emptyList())
                .columns(board.getColumns() != null
                        ? board.getColumns().stream()
                        .map(ColumnResponseDTO::map)
                        .toList()
                        : Collections.emptyList())
                .build();
    }
}
