package com.taskboard.taskboard.service.impl;

import com.taskboard.taskboard.dto.BoardMemberRequestDTO;
import com.taskboard.taskboard.dto.BoardMemberResponseDTO;
import com.taskboard.taskboard.entity.Board;
import com.taskboard.taskboard.entity.BoardMember;
import com.taskboard.taskboard.entity.User;
import com.taskboard.taskboard.repository.BoardMemberRepository;
import com.taskboard.taskboard.repository.BoardRepository;
import com.taskboard.taskboard.repository.UserRepository;
import com.taskboard.taskboard.service.BoardMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardMemberRepository boardMemberRepository;

    @Override
    public BoardMemberResponseDTO addMember(UUID boardId, BoardMemberRequestDTO request, String ownerUsername) {
        Board board = getBoardOwnedByUser(boardId, ownerUsername);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (boardMemberRepository.findByBoardAndUser(board, user).isPresent()) {
            throw new RuntimeException("User is already a member of this board");
        }

        BoardMember member = BoardMember.builder()
                .board(board)
                .user(user)
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        boardMemberRepository.save(member);

        return BoardMemberResponseDTO.map(member);
    }

    @Override
    public List<BoardMemberResponseDTO> getBoardMembers(UUID boardId, String ownerUsername) {
        Board board = getBoardOwnedByUser(boardId, ownerUsername);

        return boardMemberRepository.findByBoard(board).stream()
                .map(BoardMemberResponseDTO::map)
                .collect(Collectors.toList());
    }

    @Override
    public BoardMemberResponseDTO updateMemberRole(UUID boardId, UUID memberId, BoardMemberRequestDTO request, String ownerUsername) {
        Board board = getBoardOwnedByUser(boardId, ownerUsername);

        BoardMember member = boardMemberRepository.findById(memberId)
                .filter(m -> m.getBoard().equals(board))
                .orElseThrow(() -> new RuntimeException("Board member not found"));

        member.setRole(request.getRole());
        boardMemberRepository.save(member);

        return BoardMemberResponseDTO.map(member);
    }

    @Override
    public void removeMember(UUID boardId, UUID memberId, String ownerUsername) {
        Board board = getBoardOwnedByUser(boardId, ownerUsername);

        BoardMember member = boardMemberRepository.findById(memberId)
                .filter(m -> m.getBoard().equals(board))
                .orElseThrow(() -> new RuntimeException("Board member not found"));

        boardMemberRepository.delete(member);
    }

    private Board getBoardOwnedByUser(UUID boardId, String ownerUsername) {
        return boardRepository.findById(boardId)
                .filter(b -> b.getOwner().getUsername().equals(ownerUsername))
                .orElseThrow(() -> new RuntimeException("Board not found or access denied"));
    }
}
