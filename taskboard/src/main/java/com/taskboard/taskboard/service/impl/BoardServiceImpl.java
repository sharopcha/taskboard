package com.taskboard.taskboard.service.impl;

import com.taskboard.taskboard.dto.*;
import com.taskboard.taskboard.entity.Board;
import com.taskboard.taskboard.entity.User;
import com.taskboard.taskboard.repository.BoardRepository;
import com.taskboard.taskboard.repository.UserRepository;
import com.taskboard.taskboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public BoardResponseDTO createBoard(BoardRequestDTO request, String ownerUsername) {
        User owner = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Board board = Board.builder()
                .name(request.getName())
                .description(request.getDescription())
                .owner(owner)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        boardRepository.save(board);

        return BoardResponseDTO.map(board);
    }

    @Override
    public List<BoardResponseDTO> getAllBoards(String ownerUsername) {
        User owner = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return boardRepository.findByOwner(owner).stream()
                .map(BoardResponseDTO::map)
                .collect(Collectors.toList());
    }

    @Override
    public BoardResponseDTO getBoardById(UUID boardId, String ownerUsername) {
        Board board = getBoardOwnedByUser(boardId, ownerUsername);
        return BoardResponseDTO.map(board);
    }

    @Override
    public BoardResponseDTO updateBoard(UUID boardId, BoardRequestDTO request, String ownerUsername) {
        Board board = getBoardOwnedByUser(boardId, ownerUsername);

        if(request.getName() != null && !request.getName().isBlank()) {
            board.setName(request.getName());
        }

        if(request.getDescription() != null && !request.getDescription().isBlank()) {
            board.setDescription(request.getDescription());
        }

        board.setUpdatedAt(LocalDateTime.now());

        boardRepository.save(board);

        return BoardResponseDTO.map(board);
    }

    @Override
    public void deleteBoard(UUID boardId, String ownerUsername) {
        Board board = getBoardOwnedByUser(boardId, ownerUsername);
        boardRepository.delete(board);
    }

    private Board getBoardOwnedByUser(UUID boardId, String ownerUsername) {
        User owner = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return boardRepository.findById(boardId)
                .filter(b -> b.getOwner().equals(owner))
                .orElseThrow(() -> new RuntimeException("Board not found or access denied"));
    }
}
