package com.taskboard.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SigninRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
