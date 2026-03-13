package com.example.treequiz.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answer_option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "option_label", nullable = false, length = 1)
    private String optionLabel;

    @Column(name = "option_text", nullable = false, length = 500)
    private String optionText;
}
