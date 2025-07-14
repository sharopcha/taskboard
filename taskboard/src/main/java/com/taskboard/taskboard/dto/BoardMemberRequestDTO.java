package com.taskboard.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMemberRequestDTO {
    @NotNull
    private UUID userId;

    @NotBlank
    private String role; // owner, editor, viewer
}
