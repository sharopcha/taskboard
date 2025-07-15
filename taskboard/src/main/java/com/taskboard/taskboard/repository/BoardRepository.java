package com.taskboard.taskboard.repository;

import com.taskboard.taskboard.entity.Board;
import com.taskboard.taskboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {

    // Find boards owned by a specific user
    List<Board> findByOwner(User owner);
}