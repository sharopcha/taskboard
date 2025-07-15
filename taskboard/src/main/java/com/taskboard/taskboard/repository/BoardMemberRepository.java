package com.taskboard.taskboard.repository;

import com.taskboard.taskboard.entity.Board;
import com.taskboard.taskboard.entity.BoardMember;
import com.taskboard.taskboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, UUID> {

    // Get all members for a specific board
    List<BoardMember> findByBoard(Board board);

    // Check if a user is already a member of the board
    Optional<BoardMember> findByBoardAndUser(Board board, User user);

    // Remove all members when a board is deleted (optional if Cascade is set)
    void deleteByBoard(Board board);
}
