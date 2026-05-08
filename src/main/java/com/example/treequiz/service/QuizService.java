package com.example.treequiz.service;

import com.example.treequiz.entity.Question;
import com.example.treequiz.entity.enums.TreeType;
import com.example.treequiz.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.treequiz.entity.enums.TreeType.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    /**
     * Tie-break priority order (highest priority first).
     * Applied only when total points and direct hits are both tied.
     */
    private static final List<TreeType> TIE_BREAK_PRIORITY = List.of(
        REDWOOD, OAK, MAPLE, CEDAR, ASPEN, BONSAI, CHERRY_BLOSSOM, PINE, WILLOW, BIRCH
    );

    private final QuestionRepository questionRepository;
    private final ScoringConfig scoringConfig;

    public List<Question> getAllQuestions() {
        return questionRepository.findAllByOrderByOrderIndexAsc();
    }

    /**
     * Calculates the resulting tree type for a completed quiz.
     *
     * @param answers map of questionOrderIndex -> selected option label (e.g. "A")
     * @return the winning TreeType after applying scoring and tie-break rules
     */
    public TreeType calculateResult(Map<Integer, String> answers) {
        Map<TreeType, Integer> totalPoints = new EnumMap<>(TreeType.class);
        Map<TreeType, Integer> directHits  = new EnumMap<>(TreeType.class);

        for (TreeType t : TreeType.values()) {
            totalPoints.put(t, 0);
            directHits.put(t, 0);
        }

        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            List<TreeType> trees = scoringConfig.getTreesFor(entry.getKey(), entry.getValue());
            for (int i = 0; i < trees.size(); i++) {
                TreeType tree = trees.get(i);
                totalPoints.merge(tree, 1, Integer::sum);
                if (i == 0) {
                    directHits.merge(tree, 1, Integer::sum);
                }
            }
        }

        // 1. Find all trees with the highest total points
        int maxPoints = totalPoints.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        List<TreeType> tied = totalPoints.entrySet().stream()
            .filter(e -> e.getValue() == maxPoints)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());

        if (tied.size() == 1) return tied.get(0);

        // 2. Tie-break: highest number of direct answer hits
        int maxDirect = tied.stream().mapToInt(directHits::get).max().orElse(0);
        tied = tied.stream()
            .filter(t -> directHits.get(t) == maxDirect)
            .collect(Collectors.toList());

        if (tied.size() == 1) return tied.get(0);

        // 3. Tie-break: fixed priority order
        for (TreeType t : TIE_BREAK_PRIORITY) {
            if (tied.contains(t)) return t;
        }

        return tied.get(0);
    }
}
