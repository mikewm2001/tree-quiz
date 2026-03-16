package com.example.treequiz.controller;

import com.example.treequiz.entity.Question;
import com.example.treequiz.entity.enums.TreeType;
import com.example.treequiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public String showQuiz(Model model) {
        List<Question> questions = quizService.getAllQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("form", new QuizSubmissionForm());
        return "quiz";
    }

    @PostMapping("/submit")
    public String submitQuiz(@ModelAttribute("form") QuizSubmissionForm form, Model model) {
        List<Question> questions = quizService.getAllQuestions();

        // Validate all questions answered
        boolean incomplete = questions.stream()
            .anyMatch(q -> !form.getAnswers().containsKey(q.getOrderIndex())
                       || form.getAnswers().get(q.getOrderIndex()).isBlank());

        if (incomplete) {
            model.addAttribute("questions", questions);
            model.addAttribute("form", form);
            model.addAttribute("error", "Please answer all questions before submitting.");
            return "quiz";
        }

        TreeType result = quizService.calculateResult(form.getAnswers());
        return "redirect:/result?tree=" + result.name();
    }
}
