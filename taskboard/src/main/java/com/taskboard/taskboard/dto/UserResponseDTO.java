package com.taskboard.taskboard.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private String displayName;
    private String avatarUrl;
}