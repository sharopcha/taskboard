package com.taskboard.taskboard.service.impl;

import com.taskboard.taskboard.dto.ColumnRequestDTO;
import com.taskboard.taskboard.dto.ColumnResponseDTO;
import com.taskboard.taskboard.entity.Board;
import com.taskboard.taskboard.entity.BoardMember;
import com.taskboard.taskboard.entity.Column;
import com.taskboard.taskboard.repository.BoardMemberRepository;
import com.taskboard.taskboard.repository.BoardRepository;
import com.taskboard.taskboard.repository.ColumnRepository;
import com.taskboard.taskboard.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository columnRepository;
    private final BoardRepository boardRepository;
    private final BoardMemberRepository boardMemberRepository;

    @Override
    public ColumnResponseDTO createColumn(UUID boardId, ColumnRequestDTO request, String requesterUsername) {
        Board board = getBoardIfAuthorized(boardId, requesterUsername, true);

        Column column = Column.builder()
                .board(board)
                .name(request.getName())
                .position(request.getPosition() == 0 ? getNextPosition(board) : request.getPosition())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        columnRepository.save(column);

        return ColumnResponseDTO.map(column);
    }

    @Override
    public List<ColumnResponseDTO> getColumnsForBoard(UUID boardId, String requesterUsername) {
        Board board = getBoardIfAuthorized(boardId, requesterUsername, false);

        return columnRepository.findByBoardOrderByPositionAsc(board).stream()
                .map(ColumnResponseDTO::map)
                .collect(Collectors.toList());
    }

    @Override
    public ColumnResponseDTO updateColumn(UUID columnId, ColumnRequestDTO request, String requesterUsername) {
        Column column = getColumnIfAuthorized(columnId, requesterUsername);

        column.setName(request.getName());
        column.setPosition(request.getPosition());
        column.setUpdatedAt(LocalDateTime.now());

        columnRepository.save(column);

        return ColumnResponseDTO.map(column);
    }

    @Override
    public void deleteColumn(UUID columnId, String requesterUsername) {
        Column column = getColumnIfAuthorized(columnId, requesterUsername);

        columnRepository.delete(column);
    }

    // Helper: Check board access
    private Board getBoardIfAuthorized(UUID boardId, String username, boolean requireWrite) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        if (board.getOwner().getUsername().equals(username)) {
            return board; // Owner has full access
        }

        BoardMember member = boardMemberRepository.findByBoardAndUser(board, board.getOwner())
                .orElseThrow(() -> new RuntimeException("Access denied"));

        if (requireWrite && member.getRole().equalsIgnoreCase("viewer")) {
            throw new RuntimeException("Access denied: Viewers cannot modify columns");
        }

        return board;
    }

    // Helper: Check column access
    private Column getColumnIfAuthorized(UUID columnId, String username) {
        Column column = columnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        return getBoardIfAuthorized(column.getBoard().getId(), username, true)
                .getColumns()
                .stream()
                .filter(c -> c.getId().equals(columnId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Access denied"));
    }

    // Helper: Determine next position
    private int getNextPosition(Board board) {
        return columnRepository.findByBoardOrderByPositionAsc(board)
                .stream()
                .mapToInt(Column::getPosition)
                .max()
                .orElse(0) + 1;
    }
}
