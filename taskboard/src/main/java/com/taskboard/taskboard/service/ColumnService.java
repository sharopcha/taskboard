package com.taskboard.taskboard.service;

import com.taskboard.taskboard.dto.ColumnRequestDTO;
import com.taskboard.taskboard.dto.ColumnResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ColumnService {

    ColumnResponseDTO createColumn(UUID boardId, ColumnRequestDTO request, String requesterUsername);

    List<ColumnResponseDTO> getColumnsForBoard(UUID boardId, String requesterUsername);

    ColumnResponseDTO updateColumn(UUID columnId, ColumnRequestDTO request, String requesterUsername);

    void deleteColumn(UUID columnId, String requesterUsername);
}
