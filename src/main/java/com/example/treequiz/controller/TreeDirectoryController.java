package com.example.treequiz.controller;

import com.example.treequiz.service.TreePersonalityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trees")
@RequiredArgsConstructor
public class TreeDirectoryController {

    private final TreePersonalityService treePersonalityService;

    @GetMapping
    public String showDirectory(Model model) {
        model.addAttribute("trees", treePersonalityService.findAll());
        return "trees";
    }
}
