package com.taskboard.taskboard.dto;

import com.taskboard.taskboard.entity.BoardMember;
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

    public static BoardMemberResponseDTO map(BoardMember member) {
        return BoardMemberResponseDTO.builder()
                .id(member.getId())
                .user(UserResponseDTO.map(member.getUser()))
                .role(member.getRole())
                .build();
    }
}
