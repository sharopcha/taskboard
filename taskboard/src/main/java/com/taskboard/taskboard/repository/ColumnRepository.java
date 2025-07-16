package com.taskboard.taskboard.repository;

import com.taskboard.taskboard.entity.Board;
import com.taskboard.taskboard.entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<Column, UUID> {

    // Get all columns for a specific board, ordered by position
    List<Column> findByBoardOrderByPositionAsc(Board board);
}