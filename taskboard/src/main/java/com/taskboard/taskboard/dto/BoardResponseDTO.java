package com.taskboard.taskboard.dto;

import lombok.*;

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
}
