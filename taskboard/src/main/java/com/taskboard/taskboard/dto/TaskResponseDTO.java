package com.taskboard.taskboard.dto;

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
}
