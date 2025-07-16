package com.taskboard.taskboard.dto;

import com.taskboard.taskboard.entity.Column;
import com.taskboard.taskboard.entity.Task;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private UserResponseDTO assignee;
    private LocalDate dueDate;
    private int position;
    private List<CommentResponseDTO> comments;

    public static TaskResponseDTO map(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .assignee(task.getAssignee() != null ? UserResponseDTO.map(task.getAssignee()) : null)
                .dueDate(task.getDueDate())
                .position(task.getPosition())
                .build();
    }
}
