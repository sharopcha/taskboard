package com.taskboard.taskboard.service;

import com.taskboard.taskboard.dto.BoardRequestDTO;
import com.taskboard.taskboard.dto.BoardResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BoardService {
    BoardResponseDTO createBoard(BoardRequestDTO request, String ownerUsername);
    List<BoardResponseDTO> getAllBoards(String ownerUsername);
    BoardResponseDTO getBoardById(UUID boardId, String ownerUsername);
    BoardResponseDTO updateBoard(UUID boardId, BoardRequestDTO request, String ownerUsername);
    void deleteBoard(UUID boardId, String ownerUsername);
}
