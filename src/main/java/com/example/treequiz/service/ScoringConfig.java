package com.example.treequiz.service;

import com.example.treequiz.entity.enums.TreeType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.treequiz.entity.enums.TreeType.*;

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
            "A", List.of(BONSAI, OAK, CEDAR),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, ASPEN),
            "C", List.of(PINE, WILLOW, REDWOOD),
            "D", List.of(ASPEN, BIRCH, MAPLE)
        ));

        // Q4: How do you handle conflict?
        map.put(4, Map.of(
            "A", List.of(CEDAR, REDWOOD, OAK),
            "B", List.of(MAPLE, CHERRY_BLOSSOM, WILLOW),
            "C", List.of(PINE, BONSAI, WILLOW),
            "D", List.of(OAK, BIRCH, ASPEN)
        ));

        // Q5: A close friend is going through a hard time. You:
        map.put(5, Map.of(
            "A", List.of(WILLOW, CEDAR, CHERRY_BLOSSOM),
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
            "D", List.of(MAPLE, CEDAR, ASPEN)
        ));

        // Q8: Your ideal creative outlet is:
        map.put(8, Map.of(
            "A", List.of(WILLOW, CHERRY_BLOSSOM, BONSAI),
            "B", List.of(OAK, BONSAI, CEDAR),
            "C", List.of(MAPLE, CHERRY_BLOSSOM, BIRCH),
            "D", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM)
        ));

        // Q9: When making plans with others, you usually:
        map.put(9, Map.of(
            "A", List.of(OAK, BONSAI, CEDAR),
            "B", List.of(ASPEN, BIRCH, MAPLE),
            "C", List.of(REDWOOD, PINE, CEDAR),
            "D", List.of(WILLOW, CEDAR, MAPLE)
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
            "D", List.of(BIRCH, MAPLE, ASPEN)
        ));

        // Q13: When someone needs help, you:
        map.put(13, Map.of(
            "A", List.of(CEDAR, OAK, WILLOW),
            "B", List.of(BONSAI, REDWOOD, OAK),
            "C", List.of(MAPLE, CHERRY_BLOSSOM, ASPEN),
            "D", List.of(PINE, WILLOW, CEDAR)
        ));

        // Q14: You feel most alive when:
        map.put(14, Map.of(
            "A", List.of(BONSAI, REDWOOD, PINE),
            "B", List.of(ASPEN, MAPLE, CHERRY_BLOSSOM),
            "C", List.of(CHERRY_BLOSSOM, WILLOW, MAPLE),
            "D", List.of(OAK, CEDAR, REDWOOD)
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
            "C", List.of(REDWOOD, WILLOW, BONSAI),
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
            "B", List.of(CHERRY_BLOSSOM, WILLOW, CEDAR),
            "C", List.of(BIRCH, ASPEN, MAPLE),
            "D", List.of(PINE, BONSAI, WILLOW)
        ));

        // Q19: In a group project, you naturally become:
        map.put(19, Map.of(
            "A", List.of(OAK, BONSAI, CEDAR),
            "B", List.of(MAPLE, BIRCH, CHERRY_BLOSSOM),
            "C", List.of(ASPEN, CEDAR, MAPLE),
            "D", List.of(REDWOOD, PINE, BONSAI)
        ));

        // Q20: If you could be described by one image in nature, it would be:
        map.put(20, Map.of(
            "A", List.of(REDWOOD, OAK, PINE),
            "B", List.of(CHERRY_BLOSSOM, MAPLE, BIRCH),
            "C", List.of(WILLOW, BONSAI, CEDAR),
            "D", List.of(ASPEN, BIRCH, MAPLE)
        ));

        return Collections.unmodifiableMap(map);
    }
}
