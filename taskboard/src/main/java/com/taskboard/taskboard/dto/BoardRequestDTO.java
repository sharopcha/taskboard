package com.taskboard.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestDTO {
    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;
}
