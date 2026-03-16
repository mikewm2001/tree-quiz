package com.example.treequiz.repository;

import com.example.treequiz.entity.TreeCompatibility;
import com.example.treequiz.entity.enums.CompatibilityType;
import com.example.treequiz.entity.enums.TreeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeCompatibilityRepository extends JpaRepository<TreeCompatibility, Long> {

    List<TreeCompatibility> findBySourceTreeAndType(TreeType sourceTree, CompatibilityType type);

    List<TreeCompatibility> findBySourceTree(TreeType sourceTree);
}
