package com.taskboard.taskboard.service;

import com.taskboard.taskboard.dto.BoardMemberRequestDTO;
import com.taskboard.taskboard.dto.BoardMemberResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BoardMemberService {

    BoardMemberResponseDTO addMember(UUID boardId, BoardMemberRequestDTO request, String ownerUsername);
    List<BoardMemberResponseDTO> getBoardMembers(UUID boardId, String ownerUsername);
    BoardMemberResponseDTO updateMemberRole(UUID boardId, UUID memberId, BoardMemberRequestDTO request, String ownerUsername);
    void removeMember(UUID boardId, UUID memberId, String ownerUsername);
}
