package com.taskboard.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDTO {
    @NotBlank
    private String title;

    private String description;

    private UUID assigneeId;

    private LocalDate dueDate;

    @NotNull
    private int position;
}
