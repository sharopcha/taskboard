package com.taskboard.taskboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "columns")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardColumn {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @Column(nullable = false)
    private int position;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}