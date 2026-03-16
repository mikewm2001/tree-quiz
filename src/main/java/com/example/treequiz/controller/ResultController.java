package com.example.treequiz.controller;

import com.example.treequiz.entity.TreePersonality;
import com.example.treequiz.entity.enums.TreeType;
import com.example.treequiz.service.TreePersonalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {

    private final TreePersonalityService treePersonalityService;

    @GetMapping
    public String showResult(@RequestParam("tree") String treeCode, Model model) {
        TreeType treeType;
        try {
            treeType = TreeType.valueOf(treeCode);
        } catch (IllegalArgumentException e) {
            return "redirect:/";
        }

        Optional<TreePersonality> personality = treePersonalityService.findByCode(treeType);
        if (personality.isEmpty()) {
            return "redirect:/";
        }

        model.addAttribute("personality", personality.get());
        model.addAttribute("bestMatches", treePersonalityService.getBestMatches(treeType));
        model.addAttribute("worstMatches", treePersonalityService.getWorstMatches(treeType));
        return "result";
    }
}
