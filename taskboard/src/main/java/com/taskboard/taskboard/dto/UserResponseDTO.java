package com.taskboard.taskboard.dto;

import com.taskboard.taskboard.entity.User;
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

    public static UserResponseDTO map(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}