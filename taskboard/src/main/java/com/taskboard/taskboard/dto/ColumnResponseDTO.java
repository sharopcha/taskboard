package com.taskboard.taskboard.dto;

import com.taskboard.taskboard.entity.Column;
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

    public static ColumnResponseDTO map(Column column) {
        return ColumnResponseDTO.builder()
                .id(column.getId())
                .name(column.getName())
                .position(column.getPosition())
                .tasks(column.getTasks() != null
                        ? column.getTasks().stream()
                        .map(TaskResponseDTO::map)
                        .toList()
                        : List.of())
                .build();
    }
}
