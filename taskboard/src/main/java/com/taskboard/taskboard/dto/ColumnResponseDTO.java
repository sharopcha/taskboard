package com.taskboard.taskboard.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnResponseDTO {
    private UUID id;
    private String name;
    private int position;
    private List<TaskResponseDTO> tasks;
}
