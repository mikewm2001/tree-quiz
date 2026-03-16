package com.example.treequiz.entity;

import com.example.treequiz.entity.enums.TreeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TreeType resultingTree;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
