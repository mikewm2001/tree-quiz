package com.example.treequiz.repository;

import com.example.treequiz.entity.QuizResult;
import com.example.treequiz.entity.enums.TreeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    List<QuizResult> findByResultingTree(TreeType resultingTree);
}
