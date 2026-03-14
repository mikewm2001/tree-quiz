package com.example.treequiz.service;

import com.example.treequiz.entity.TreeCompatibility;
import com.example.treequiz.entity.TreePersonality;
import com.example.treequiz.entity.enums.CompatibilityType;
import com.example.treequiz.entity.enums.TreeType;
import com.example.treequiz.repository.TreeCompatibilityRepository;
import com.example.treequiz.repository.TreePersonalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreePersonalityService {

    private final TreePersonalityRepository treePersonalityRepository;
    private final TreeCompatibilityRepository treeCompatibilityRepository;

    public List<TreePersonality> findAll() {
        return treePersonalityRepository.findAll();
    }

    public Optional<TreePersonality> findByCode(TreeType code) {
        return treePersonalityRepository.findByCode(code);
    }

    public List<TreePersonality> getBestMatches(TreeType code) {
        return treeCompatibilityRepository
            .findBySourceTreeAndType(code, CompatibilityType.BEST)
            .stream()
            .map(TreeCompatibility::getTargetTree)
            .map(treePersonalityRepository::findByCode)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }

    public List<TreePersonality> getWorstMatches(TreeType code) {
        return treeCompatibilityRepository
            .findBySourceTreeAndType(code, CompatibilityType.WORST)
            .stream()
            .map(TreeCompatibility::getTargetTree)
            .map(treePersonalityRepository::findByCode)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
    }
}
