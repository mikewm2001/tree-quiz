package com.example.treequiz.controller;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form-backing object for the quiz submission.
 * Spring MVC binds answers[1]=A, answers[2]=B, ... into the map.
 */
public class QuizSubmissionForm {

    private Map<Integer, String> answers = new LinkedHashMap<>();

    public Map<Integer, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Integer, String> answers) {
        this.answers = answers;
    }
}
