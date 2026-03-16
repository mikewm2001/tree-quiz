package com.example.treequiz.repository;

import com.example.treequiz.entity.TreePersonality;
import com.example.treequiz.entity.enums.TreeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TreePersonalityRepository extends JpaRepository<TreePersonality, Long> {

    Optional<TreePersonality> findByCode(TreeType code);

    boolean existsByCode(TreeType code);
}
