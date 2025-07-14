package com.taskboard.taskboard.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    private UUID id;
    private String content;
    private UserResponseDTO author;
    private LocalDateTime createdAt;
}
