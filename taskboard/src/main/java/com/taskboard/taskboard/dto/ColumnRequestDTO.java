package com.taskboard.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private int position;
}
