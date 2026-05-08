package com.example.treequiz.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.treequiz.entity.enums.TreeType;
import static com.example.treequiz.entity.enums.TreeType.ASPEN;
import static com.example.treequiz.entity.enums.TreeType.BIRCH;
import static com.example.treequiz.entity.enums.TreeType.BONSAI;
import static com.example.treequiz.entity.enums.TreeType.CEDAR;
import static com.example.treequiz.entity.enums.TreeType.CHERRY_BLOSSOM;
import static com.example.treequiz.entity.enums.TreeType.MAPLE;
import static com.example.treequiz.entity.enums.TreeType.OAK;
import static com.example.treequiz.entity.enums.TreeType.PINE;
import static com.example.treequiz.entity.enums.TreeType.REDWOOD;
import static com.example.treequiz.entity.enums.TreeType.WILLOW;

/**
 * Maps each quiz answer (by question order index + option label) to the tree types it scores.
 * The first tree in each list is the "direct hit" tree used in tie-breaking.
 * Modify this class to adjust quiz scoring without touching any other logic.
 */
@Component
public class ScoringConfig {

    private static final Map<Integer, Map<String, List<TreeType>>> SCORING_MAP = buildScoringMap();

    /**
     * Returns the list of tree types scored by a given answer.
     * The first element is the primary (direct-hit) tree.
     */
    public List<TreeType> getTreesFor(int questionOrder, String optionLabel) {
        Map<String, List<TreeType>> questionMap = SCORING_MAP.get(questionOrder);
        if (questionMap == null) return List.of();
        return questionMap.getOrDefault(optionLabel.toUpperCase(), List.of());
    }

    private static Map<Integer, Map<String, List<TreeType>>> buildScoringMap() {
        Map<Integer, Map<String, List<TreeType>>> map = new HashMap<>();

        // Q1: On a free weekend, what sounds most appealing?
        map.put(1, Map.of(
            "A", List.of(OAK, REDWOOD, CEDAR),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, BIRCH),
            "C", List.of(PINE, BONSAI, WILLOW),
            "D", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM)
        ));

        // Q2: When facing a difficult decision, you tend to:
        map.put(2, Map.of(
            "A", List.of(BONSAI, REDWOOD, OAK),
            "B", List.of(WILLOW, CHERRY_BLOSSOM, ASPEN),
            "C", List.of(CEDAR, OAK, MAPLE),
            "D", List.of(BIRCH, ASPEN, MAPLE)
        ));

        // Q3: Which environment feels most like home to you?
        map.put(3, Map.of(
            "A", List.of(BONSAI, CEDAR, REDWOOD),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, ASPEN),
            "C", List.of(PINE, WILLOW, REDWOOD),
            "D", List.of(ASPEN, BIRCH, MAPLE)
        ));

        // Q4: How do you handle conflict?
        map.put(4, Map.of(
            "A", List.of(CEDAR, REDWOOD, OAK),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, WILLOW),
            "C", List.of(PINE, BONSAI, WILLOW),
            "D", List.of(OAK, CEDAR, REDWOOD)
        ));

        // Q5: A close friend is going through a hard time. You:
        map.put(5, Map.of(
            "A", List.of(WILLOW, CEDAR, PINE),
            "B", List.of(OAK, BONSAI, REDWOOD),
            "C", List.of(MAPLE, ASPEN, BIRCH),
            "D", List.of(CEDAR, OAK, WILLOW)
        ));

        // Q6: What word best describes your social style?
        map.put(6, Map.of(
            "A", List.of(PINE, OAK, REDWOOD),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, ASPEN),
            "C", List.of(BONSAI, WILLOW, CEDAR),
            "D", List.of(ASPEN, BIRCH, MAPLE)
        ));

        // Q7: When you learn something new, you prefer to:
        map.put(7, Map.of(
            "A", List.of(BONSAI, OAK, REDWOOD),
            "B", List.of(BIRCH, ASPEN, MAPLE),
            "C", List.of(WILLOW, REDWOOD, BONSAI),
            "D", List.of(MAPLE, BIRCH, ASPEN)
        ));

        // Q8: Your ideal creative outlet is:
        map.put(8, Map.of(
            "A", List.of(WILLOW, CHERRY_BLOSSOM, BONSAI),
            "B", List.of(BONSAI, MAPLE, OAK),
            "C", List.of(MAPLE, CHERRY_BLOSSOM, BIRCH),
            "D", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM)
        ));

        // Q9: When making plans with others, you usually:
        map.put(9, Map.of(
            "A", List.of(OAK, BONSAI, CEDAR),
            "B", List.of(ASPEN, BIRCH, MAPLE),
            "C", List.of(REDWOOD, PINE, CEDAR),
            "D", List.of(CEDAR, WILLOW, MAPLE)
        ));

        // Q10: What motivates you most?
        map.put(10, Map.of(
            "A", List.of(BONSAI, REDWOOD, PINE),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, ASPEN),
            "C", List.of(OAK, CEDAR, WILLOW),
            "D", List.of(BIRCH, ASPEN, MAPLE)
        ));

        // Q11: How do you typically recharge?
        map.put(11, Map.of(
            "A", List.of(PINE, BONSAI, WILLOW),
            "B", List.of(REDWOOD, CEDAR, OAK),
            "C", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM),
            "D", List.of(WILLOW, BONSAI, BIRCH)
        ));

        // Q12: Which describes your relationship with change?
        map.put(12, Map.of(
            "A", List.of(OAK, CEDAR, PINE),
            "B", List.of(BIRCH, ASPEN, MAPLE),
            "C", List.of(BONSAI, WILLOW, REDWOOD),
            "D", List.of(MAPLE, CEDAR, ASPEN)
        ));

        // Q13: When someone needs help, you:
        map.put(13, Map.of(
            "A", List.of(CEDAR, OAK, WILLOW),
            "B", List.of(OAK, CEDAR, BONSAI),
            "C", List.of(MAPLE, CHERRY_BLOSSOM, ASPEN),
            "D", List.of(PINE, WILLOW, CEDAR)
        ));

        // Q14: You feel most alive when:
        map.put(14, Map.of(
            "A", List.of(BONSAI, REDWOOD, PINE),
            "B", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM),
            "C", List.of(CHERRY_BLOSSOM, WILLOW, MAPLE),
            "D", List.of(REDWOOD, OAK, BONSAI)
        ));

        // Q15: How do you approach personal goals?
        map.put(15, Map.of(
            "A", List.of(OAK, BONSAI, REDWOOD),
            "B", List.of(WILLOW, BIRCH, ASPEN),
            "C", List.of(MAPLE, CHERRY_BLOSSOM, BIRCH),
            "D", List.of(PINE, CEDAR, REDWOOD)
        ));

        // Q16: Your friends would describe you as:
        map.put(16, Map.of(
            "A", List.of(OAK, CEDAR, PINE),
            "B", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM),
            "C", List.of(REDWOOD, CEDAR, WILLOW),
            "D", List.of(BIRCH, MAPLE, ASPEN)
        ));

        // Q17: What kind of stories do you love most?
        map.put(17, Map.of(
            "A", List.of(WILLOW, BONSAI, PINE),
            "B", List.of(OAK, REDWOOD, CEDAR),
            "C", List.of(CHERRY_BLOSSOM, WILLOW, MAPLE),
            "D", List.of(ASPEN, BIRCH, MAPLE)
        ));

        // Q18: When you look back on your life, you most want to have:
        map.put(18, Map.of(
            "A", List.of(OAK, REDWOOD, CEDAR),
            "B", List.of(CHERRY_BLOSSOM, WILLOW, ASPEN),
            "C", List.of(BIRCH, ASPEN, MAPLE),
            "D", List.of(PINE, BONSAI, WILLOW)
        ));

        // Q19: In a group project, you naturally become:
        map.put(19, Map.of(
            "A", List.of(OAK, BONSAI, CEDAR),
            "B", List.of(MAPLE, BIRCH, CHERRY_BLOSSOM),
            "C", List.of(CEDAR, ASPEN, MAPLE),
            "D", List.of(REDWOOD, PINE, BONSAI)
        ));

        // Q20: If you could be described by one image in nature, it would be:
        map.put(20, Map.of(
            "A", List.of(REDWOOD, OAK, PINE),
            "B", List.of(CHERRY_BLOSSOM, MAPLE, BIRCH),
            "C", List.of(WILLOW, PINE, CEDAR),
            "D", List.of(ASPEN, BIRCH, MAPLE)
        ));

        // Q21: When you join a new group, what do you naturally do?
        map.put(21, Map.of(
            "A", List.of(BONSAI, REDWOOD, PINE),
            "B", List.of(CEDAR, OAK, PINE),
            "C", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM),
            "D", List.of(CHERRY_BLOSSOM, WILLOW, BIRCH)
        ));

        // Q22: Which personal strength feels most natural to you?
        map.put(22, Map.of(
            "A", List.of(CEDAR, REDWOOD, OAK),
            "B", List.of(CHERRY_BLOSSOM, WILLOW, BIRCH),
            "C", List.of(WILLOW, CEDAR, BONSAI),
            "D", List.of(BONSAI, PINE, REDWOOD)
        ));

        // Q23: When plans suddenly change, you usually...
        map.put(23, Map.of(
            "A", List.of(BIRCH, ASPEN, MAPLE),
            "B", List.of(CEDAR, PINE, OAK),
            "C", List.of(WILLOW, PINE, BONSAI),
            "D", List.of(BONSAI, REDWOOD, PINE)
        ));

        // Q24: What kind of work feels most satisfying?
        map.put(24, Map.of(
            "A", List.of(REDWOOD, OAK, CEDAR),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, WILLOW),
            "C", List.of(CEDAR, WILLOW, OAK),
            "D", List.of(BONSAI, PINE, REDWOOD)
        ));

        // Q25: What frustrates you most?
        map.put(25, Map.of(
            "A", List.of(OAK, CEDAR, REDWOOD),
            "B", List.of(MAPLE, BIRCH, ASPEN),
            "C", List.of(WILLOW, CHERRY_BLOSSOM, CEDAR),
            "D", List.of(BONSAI, PINE, OAK)
        ));

        // Q26: Which role do you play during difficult times?
        map.put(26, Map.of(
            "A", List.of(CEDAR, OAK, PINE),
            "B", List.of(CHERRY_BLOSSOM, MAPLE, ASPEN),
            "C", List.of(WILLOW, CEDAR, CHERRY_BLOSSOM),
            "D", List.of(BONSAI, PINE, REDWOOD)
        ));

        // Q27: How would you rather spend a quiet evening?
        map.put(27, Map.of(
            "A", List.of(OAK, BONSAI, CEDAR),
            "B", List.of(CHERRY_BLOSSOM, MAPLE, WILLOW),
            "C", List.of(WILLOW, PINE, BONSAI),
            "D", List.of(BIRCH, BONSAI, PINE)
        ));

        // Q28: What kind of compliment feels most accurate?
        map.put(28, Map.of(
            "A", List.of(OAK, CEDAR, PINE),
            "B", List.of(MAPLE, ASPEN, CHERRY_BLOSSOM),
            "C", List.of(WILLOW, REDWOOD, BONSAI),
            "D", List.of(BONSAI, REDWOOD, PINE)
        ));

        // Q29: When making an important life choice, you prioritize...
        map.put(29, Map.of(
            "A", List.of(OAK, CEDAR, PINE),
            "B", List.of(BIRCH, ASPEN, MAPLE),
            "C", List.of(WILLOW, CHERRY_BLOSSOM, ASPEN),
            "D", List.of(REDWOOD, BONSAI, PINE)
        ));

        // Q30: Which image feels most like your growth?
        map.put(30, Map.of(
            "A", List.of(REDWOOD, OAK, CEDAR),
            "B", List.of(MAPLE, BIRCH, ASPEN),
            "C", List.of(WILLOW, PINE, BIRCH),
            "D", List.of(BONSAI, PINE, REDWOOD)
        ));

        return Collections.unmodifiableMap(map);
    }
}
