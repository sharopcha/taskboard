package com.taskboard.taskboard.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMemberResponseDTO {
    private UUID id;
    private UserResponseDTO user;
    private String role;
}
