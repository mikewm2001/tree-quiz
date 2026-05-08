package com.example.treequiz.service;

import com.example.treequiz.entity.AnswerOption;
import com.example.treequiz.entity.Question;
import com.example.treequiz.entity.TreeCompatibility;
import com.example.treequiz.entity.TreePersonality;
import com.example.treequiz.entity.enums.CompatibilityType;
import com.example.treequiz.entity.enums.TreeType;
import com.example.treequiz.repository.QuestionRepository;
import com.example.treequiz.repository.TreeCompatibilityRepository;
import com.example.treequiz.repository.TreePersonalityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Order(1)
@RequiredArgsConstructor
public class DataInitializerService implements ApplicationRunner {

    private final TreePersonalityRepository treePersonalityRepository;
    private final TreeCompatibilityRepository treeCompatibilityRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (treePersonalityRepository.count() == 0) {
            log.info("Seeding tree personality data...");
            seedTreePersonalities();
            log.info("Tree personality data seeded.");
        }

        long deleted = treeCompatibilityRepository.count();
        treeCompatibilityRepository.deleteAllInBatch();
        seedTreeCompatibilities();
        long created = treeCompatibilityRepository.count();
        log.info("Compatibility mappings refreshed: {} deleted, {} recreated.", deleted, created);

        if (questionRepository.count() == 0) {
            log.info("Seeding quiz questions...");
            seedQuestions();
            log.info("Quiz questions seeded.");
        }
        if (questionRepository.count() < 30) {
            log.info("Seeding extended quiz questions (21–30)...");
            seedExtendedQuestions();
            log.info("Extended quiz questions seeded.");
        }
    }

    // ------------------------------------------------------------------
    // Tree personalities
    // ------------------------------------------------------------------

    private void seedTreePersonalities() {
        treePersonalityRepository.saveAll(List.of(

            TreePersonality.builder()
                .code(TreeType.OAK)
                .displayName("Oak")
                .title("The Steady Guardian")
                .description("You are the foundation that others rely on. Steadfast and strong, you face challenges with quiet determination and offer shelter to those around you. Your loyalty runs deep, and your word is your bond. People turn to you in hard times because you never waver.")
                .traits(List.of("dependable", "resilient", "loyal", "disciplined", "protective"))
                .accentColor("#5C4033")
                .build(),

            TreePersonality.builder()
                .code(TreeType.MAPLE)
                .displayName("Maple")
                .title("The Warm Creative")
                .description("You bring warmth and colour to the world. Gifted with creativity and a vibrant spirit, you thrive in connection and find beauty in everyday moments. Your adaptability lets you flourish in any season, and your optimism is infectious to everyone around you.")
                .traits(List.of("creative", "expressive", "sociable", "adaptable", "optimistic"))
                .accentColor("#C8520A")
                .build(),

            TreePersonality.builder()
                .code(TreeType.WILLOW)
                .displayName("Willow")
                .title("The Gentle Dreamer")
                .description("You move through life with grace and sensitivity. Deeply empathetic, you bend without breaking and listen with your whole heart. Your rich inner world feeds a powerful imagination, and people feel genuinely understood in your presence.")
                .traits(List.of("empathetic", "reflective", "imaginative", "intuitive", "sensitive"))
                .accentColor("#7A9E7E")
                .build(),

            TreePersonality.builder()
                .code(TreeType.PINE)
                .displayName("Pine")
                .title("The Quiet Survivor")
                .description("You stand tall through every storm, needing little but giving much. Quiet and self-sufficient, you find strength in stillness and carry wisdom earned through endurance. You don't seek the spotlight — your roots run too deep for that.")
                .traits(List.of("independent", "calm", "enduring", "humble", "patient"))
                .accentColor("#2D5016")
                .build(),

            TreePersonality.builder()
                .code(TreeType.BIRCH)
                .displayName("Birch")
                .title("The Fresh Start")
                .description("You carry the energy of new beginnings. Bright, curious, and resilient, you adapt easily and inspire others with your fresh perspective. Your enthusiasm for life is contagious and your openness to change is one of your greatest gifts.")
                .traits(List.of("curious", "flexible", "hopeful", "intelligent", "youthful"))
                .accentColor("#8FA882")
                .build(),

            TreePersonality.builder()
                .code(TreeType.REDWOOD)
                .displayName("Redwood")
                .title("The Quiet Giant")
                .description("You are rare and extraordinary. Grounded in deep roots yet reaching toward the sky, you see the long view and lead by quiet example. Your presence alone brings calm to those around you, and your vision extends far beyond the immediate horizon.")
                .traits(List.of("wise", "composed", "visionary", "grounded", "patient"))
                .accentColor("#7B3F2E")
                .build(),

            TreePersonality.builder()
                .code(TreeType.CHERRY_BLOSSOM)
                .displayName("Cherry Blossom")
                .title("The Joyful Romantic")
                .description("You are a celebration of life. Joyful and heartfelt, you embrace beauty in its most fleeting forms and love with your whole being. Your warmth draws people to you like petals to spring, and your grace leaves a lasting impression on everyone you meet.")
                .traits(List.of("joyful", "expressive", "romantic", "heartfelt", "graceful"))
                .accentColor("#C97BA0")
                .build(),

            TreePersonality.builder()
                .code(TreeType.CEDAR)
                .displayName("Cedar")
                .title("The Calm Protector")
                .description("You are a quiet anchor in a shifting world. Nurturing and steady, you create safe spaces for others and balance your own needs with genuine care. Your calm is not indifference — it's a gift you extend freely to everyone around you.")
                .traits(List.of("nurturing", "stable", "calm", "balanced", "protective"))
                .accentColor("#5C7A4A")
                .build(),

            TreePersonality.builder()
                .code(TreeType.ASPEN)
                .displayName("Aspen")
                .title("The Lively Connector")
                .description("You are alive with energy and connection. Never quite still, you thrive in community and light up any room you enter. Your playful spirit and open heart make you a magnet for joy, and your spontaneity turns ordinary moments into adventures.")
                .traits(List.of("outgoing", "energetic", "playful", "communicative", "spontaneous"))
                .accentColor("#A8C47A")
                .build(),

            TreePersonality.builder()
                .code(TreeType.BONSAI)
                .displayName("Bonsai")
                .title("The Intentional Thinker")
                .description("You are a study in intentional living. Patient and precise, you shape your world with care and find meaning in the details others overlook. Your artistry and discipline combine to create something truly unique — a life lived with deep awareness.")
                .traits(List.of("precise", "thoughtful", "disciplined", "observant", "artistic"))
                .accentColor("#3D5C2E")
                .build()
        ));
    }

    // ------------------------------------------------------------------
    // Tree compatibilities
    // ------------------------------------------------------------------

    private void seedTreeCompatibilities() {
        treeCompatibilityRepository.saveAll(List.of(
            // OAK — best: CEDAR, REDWOOD, BONSAI | worst: ASPEN, CHERRY_BLOSSOM
            compat(TreeType.OAK, TreeType.CEDAR,          CompatibilityType.BEST),
            compat(TreeType.OAK, TreeType.REDWOOD,        CompatibilityType.BEST),
            compat(TreeType.OAK, TreeType.BONSAI,         CompatibilityType.BEST),
            compat(TreeType.OAK, TreeType.ASPEN,          CompatibilityType.WORST),
            compat(TreeType.OAK, TreeType.CHERRY_BLOSSOM, CompatibilityType.WORST),
            // MAPLE — best: ASPEN, CHERRY_BLOSSOM, BIRCH | worst: BONSAI, CEDAR
            compat(TreeType.MAPLE, TreeType.ASPEN,          CompatibilityType.BEST),
            compat(TreeType.MAPLE, TreeType.CHERRY_BLOSSOM, CompatibilityType.BEST),
            compat(TreeType.MAPLE, TreeType.BIRCH,          CompatibilityType.BEST),
            compat(TreeType.MAPLE, TreeType.BONSAI,         CompatibilityType.WORST),
            compat(TreeType.MAPLE, TreeType.CEDAR,          CompatibilityType.WORST),
            // WILLOW — best: CHERRY_BLOSSOM, CEDAR, BONSAI | worst: REDWOOD, BIRCH
            compat(TreeType.WILLOW, TreeType.CHERRY_BLOSSOM, CompatibilityType.BEST),
            compat(TreeType.WILLOW, TreeType.CEDAR,          CompatibilityType.BEST),
            compat(TreeType.WILLOW, TreeType.BONSAI,         CompatibilityType.BEST),
            compat(TreeType.WILLOW, TreeType.REDWOOD,        CompatibilityType.WORST),
            compat(TreeType.WILLOW, TreeType.BIRCH,          CompatibilityType.WORST),
            // PINE — best: BONSAI, REDWOOD, BIRCH | worst: CHERRY_BLOSSOM, ASPEN
            compat(TreeType.PINE, TreeType.BONSAI,         CompatibilityType.BEST),
            compat(TreeType.PINE, TreeType.REDWOOD,        CompatibilityType.BEST),
            compat(TreeType.PINE, TreeType.BIRCH,          CompatibilityType.BEST),
            compat(TreeType.PINE, TreeType.CHERRY_BLOSSOM, CompatibilityType.WORST),
            compat(TreeType.PINE, TreeType.ASPEN,          CompatibilityType.WORST),
            // BIRCH — best: MAPLE, ASPEN, PINE | worst: REDWOOD, WILLOW
            compat(TreeType.BIRCH, TreeType.MAPLE,   CompatibilityType.BEST),
            compat(TreeType.BIRCH, TreeType.ASPEN,   CompatibilityType.BEST),
            compat(TreeType.BIRCH, TreeType.PINE,    CompatibilityType.BEST),
            compat(TreeType.BIRCH, TreeType.REDWOOD, CompatibilityType.WORST),
            compat(TreeType.BIRCH, TreeType.WILLOW,  CompatibilityType.WORST),
            // REDWOOD — best: OAK, CEDAR, PINE | worst: BIRCH, WILLOW
            compat(TreeType.REDWOOD, TreeType.OAK,    CompatibilityType.BEST),
            compat(TreeType.REDWOOD, TreeType.CEDAR,  CompatibilityType.BEST),
            compat(TreeType.REDWOOD, TreeType.PINE,   CompatibilityType.BEST),
            compat(TreeType.REDWOOD, TreeType.BIRCH,  CompatibilityType.WORST),
            compat(TreeType.REDWOOD, TreeType.WILLOW, CompatibilityType.WORST),
            // CHERRY_BLOSSOM — best: WILLOW, MAPLE, ASPEN | worst: OAK, PINE
            compat(TreeType.CHERRY_BLOSSOM, TreeType.WILLOW, CompatibilityType.BEST),
            compat(TreeType.CHERRY_BLOSSOM, TreeType.MAPLE,  CompatibilityType.BEST),
            compat(TreeType.CHERRY_BLOSSOM, TreeType.ASPEN,  CompatibilityType.BEST),
            compat(TreeType.CHERRY_BLOSSOM, TreeType.OAK,    CompatibilityType.WORST),
            compat(TreeType.CHERRY_BLOSSOM, TreeType.PINE,   CompatibilityType.WORST),
            // CEDAR — best: OAK, WILLOW, REDWOOD | worst: MAPLE, BONSAI
            compat(TreeType.CEDAR, TreeType.OAK,    CompatibilityType.BEST),
            compat(TreeType.CEDAR, TreeType.WILLOW,  CompatibilityType.BEST),
            compat(TreeType.CEDAR, TreeType.REDWOOD, CompatibilityType.BEST),
            compat(TreeType.CEDAR, TreeType.MAPLE,   CompatibilityType.WORST),
            compat(TreeType.CEDAR, TreeType.BONSAI,  CompatibilityType.WORST),
            // ASPEN — best: MAPLE, CHERRY_BLOSSOM, BIRCH | worst: OAK, PINE
            compat(TreeType.ASPEN, TreeType.MAPLE,          CompatibilityType.BEST),
            compat(TreeType.ASPEN, TreeType.CHERRY_BLOSSOM, CompatibilityType.BEST),
            compat(TreeType.ASPEN, TreeType.BIRCH,          CompatibilityType.BEST),
            compat(TreeType.ASPEN, TreeType.OAK,            CompatibilityType.WORST),
            compat(TreeType.ASPEN, TreeType.PINE,           CompatibilityType.WORST),
            // BONSAI — best: PINE, WILLOW, OAK | worst: MAPLE, CEDAR
            compat(TreeType.BONSAI, TreeType.PINE,   CompatibilityType.BEST),
            compat(TreeType.BONSAI, TreeType.WILLOW, CompatibilityType.BEST),
            compat(TreeType.BONSAI, TreeType.OAK,    CompatibilityType.BEST),
            compat(TreeType.BONSAI, TreeType.MAPLE,  CompatibilityType.WORST),
            compat(TreeType.BONSAI, TreeType.CEDAR,  CompatibilityType.WORST)
        ));
    }

    private TreeCompatibility compat(TreeType source, TreeType target, CompatibilityType type) {
        return TreeCompatibility.builder()
            .sourceTree(source)
            .targetTree(target)
            .type(type)
            .build();
    }

    // ------------------------------------------------------------------
    // Quiz questions and answer options
    // ------------------------------------------------------------------

    private void seedQuestions() {
        questionRepository.saveAll(List.of(

            question(1,
                "On a free weekend, what sounds most appealing?",
                "A", "Planning something productive or working on a personal project",
                "B", "Exploring somewhere new or visiting an inspiring place",
                "C", "Resting quietly at home with a book or favourite hobby",
                "D", "Spending time with friends and enjoying good company"
            ),

            question(2,
                "When facing a difficult decision, you tend to:",
                "A", "Analyse all the options carefully before taking any action",
                "B", "Follow your gut instinct and trust how you feel",
                "C", "Seek advice from people you trust before deciding",
                "D", "Act boldly and adjust your course as you go"
            ),

            question(3,
                "Which environment feels most like home to you?",
                "A", "A cosy, well-organised study or workspace",
                "B", "A vibrant garden full of colour and life",
                "C", "A peaceful forest clearing or open hillside",
                "D", "A lively neighbourhood café buzzing with people"
            ),

            question(4,
                "How do you handle conflict?",
                "A", "Stay calm and try to mediate between both sides",
                "B", "Express your feelings openly and honestly",
                "C", "Withdraw and take time alone to reflect",
                "D", "Confront it directly and work through it head-on"
            ),

            question(5,
                "A close friend is going through a difficult time. You:",
                "A", "Listen without judgement and offer quiet comfort",
                "B", "Give practical advice and concrete solutions",
                "C", "Plan a fun distraction or outing to cheer them up",
                "D", "Check in regularly and stay steadily by their side"
            ),

            question(6,
                "What word best describes your social style?",
                "A", "Reserved but deeply loyal",
                "B", "Warm, expressive, and open",
                "C", "Thoughtful, selective, and genuine",
                "D", "Open, energetic, and outgoing"
            ),

            question(7,
                "When you learn something new, you prefer to:",
                "A", "Take structured notes and review them carefully",
                "B", "Experiment hands-on and learn through doing",
                "C", "Reflect on how it connects to what you already know",
                "D", "Discuss it with others to deepen your understanding"
            ),

            question(8,
                "Your ideal creative outlet is:",
                "A", "Writing poetry, journaling, or personal storytelling",
                "B", "Crafting, building, or designing something tangible",
                "C", "Painting, music, or another form of visual or sonic art",
                "D", "Performing, presenting, or entertaining others"
            ),

            question(9,
                "When making plans with others, you usually:",
                "A", "Organise everything in advance and share a clear plan",
                "B", "Go with the flow and stay flexible as things unfold",
                "C", "Suggest one meaningful idea and commit fully to it",
                "D", "Adapt based on what works best for everyone involved"
            ),

            question(10,
                "What motivates you most?",
                "A", "Achieving mastery and depth in something you care about",
                "B", "Creating joy and memorable moments for yourself and others",
                "C", "Protecting and supporting the people you love",
                "D", "Discovering new ideas, places, and ways of seeing the world"
            ),

            question(11,
                "How do you typically recharge after a tiring week?",
                "A", "Alone in a quiet, calm space with no demands on you",
                "B", "In nature — walking, hiking, or simply being outside",
                "C", "Around friends, music, and laughter",
                "D", "Reading, thinking, or working on something creative"
            ),

            question(12,
                "Which best describes your relationship with change?",
                "A", "You resist it — stability and continuity matter deeply to you",
                "B", "You embrace it with excitement and curiosity",
                "C", "You accept it slowly and process it in your own time",
                "D", "You adapt quickly and tend to find opportunity in it"
            ),

            question(13,
                "When someone close to you needs help, you:",
                "A", "Drop everything and show up for them immediately",
                "B", "Find the most effective and practical way to assist",
                "C", "Encourage them and help lift their spirits",
                "D", "Offer a quiet, steady presence without overwhelming them"
            ),

            question(14,
                "You feel most alive when:",
                "A", "You're in deep focus on work that feels truly meaningful",
                "B", "You're surrounded by laughter, warmth, and good people",
                "C", "You're experiencing something beautiful or moving",
                "D", "You're solving a problem that genuinely matters"
            ),

            question(15,
                "How do you approach your personal goals?",
                "A", "Set clear milestones and track your progress step by step",
                "B", "Stay open — you trust the path will reveal itself",
                "C", "Dream big and pursue it with full energy and passion",
                "D", "Work quietly and consistently, without needing recognition"
            ),

            question(16,
                "Your friends would most likely describe you as:",
                "A", "Reliable, steady, and always there when needed",
                "B", "Spontaneous, fun, and full of energy",
                "C", "Wise, calming, and grounding to be around",
                "D", "Curious, enthusiastic, and full of ideas"
            ),

            question(17,
                "What kind of stories do you love most?",
                "A", "Quiet, character-driven journeys with emotional depth",
                "B", "Epic adventures built on trust, sacrifice, and lasting bonds",
                "C", "Romantic and emotional tales that celebrate connection",
                "D", "Fast-paced plots full of surprising twists and turns"
            ),

            question(18,
                "When you look back on your life, you most want to have:",
                "A", "Built something lasting, meaningful, and enduring",
                "B", "Loved deeply and been truly known by others",
                "C", "Lived with curiosity, variety, and openness",
                "D", "Found peace, inner clarity, and a quiet sense of purpose"
            ),

            question(19,
                "In a group project, you naturally become:",
                "A", "The planner who keeps everyone on track",
                "B", "The creative who brings fresh energy and ideas",
                "C", "The connector who keeps the group together",
                "D", "The quiet thinker whose ideas shape the outcome"
            ),

            question(20,
                "If you could be described by one image from nature, it would be:",
                "A", "A towering ancient tree standing alone in a quiet forest",
                "B", "Branches covered in spring blossoms after a long winter",
                "C", "A willow trailing its branches beside a still lake at dusk",
                "D", "A whole grove of trees swaying together in the wind"
            )
        ));
    }

    private Question question(int order,
                               String prompt,
                               String labelA, String textA,
                               String labelB, String textB,
                               String labelC, String textC,
                               String labelD, String textD) {
        Question q = Question.builder()
            .prompt(prompt)
            .orderIndex(order)
            .build();

        q.setAnswerOptions(List.of(
            option(q, labelA, textA),
            option(q, labelB, textB),
            option(q, labelC, textC),
            option(q, labelD, textD)
        ));

        return q;
    }

    private void seedExtendedQuestions() {
        questionRepository.saveAll(List.of(

            question(21,
                "When you join a new group, what do you naturally do?",
                "A", "Quietly observe and understand the dynamics first",
                "B", "Help organise things so everyone feels grounded",
                "C", "Bring energy and get people talking",
                "D", "Look for ways to make the experience more meaningful"
            ),

            question(22,
                "Which personal strength feels most natural to you?",
                "A", "Staying steady when others feel uncertain",
                "B", "Seeing beauty and possibility in ordinary things",
                "C", "Understanding subtle emotions in yourself and others",
                "D", "Refining details until something feels just right"
            ),

            question(23,
                "When plans suddenly change, you usually...",
                "A", "Adapt quickly and make the best of the new situation",
                "B", "Stay calm and rebuild the plan from scratch",
                "C", "Need a little time to process the emotional shift",
                "D", "Step back and choose the most intentional next move"
            ),

            question(24,
                "What kind of work feels most satisfying?",
                "A", "Work that creates something lasting and meaningful",
                "B", "Work that lets you express your creativity freely",
                "C", "Work that directly helps or comforts other people",
                "D", "Work that demands precision and thoughtful design"
            ),

            question(25,
                "What frustrates you most?",
                "A", "People who are unreliable or inconsistent",
                "B", "Feeling boxed in or creatively limited",
                "C", "Being misunderstood or not heard emotionally",
                "D", "Careless decisions made without proper reflection"
            ),

            question(26,
                "Which role do you naturally play during difficult times?",
                "A", "The anchor who keeps everyone steady",
                "B", "The encourager who lifts the mood",
                "C", "The listener who helps people feel seen",
                "D", "The quiet problem-solver who thinks deeply"
            ),

            question(27,
                "How would you rather spend a quiet evening?",
                "A", "Planning goals or working on something useful",
                "B", "Creating, cooking, decorating, or making something beautiful",
                "C", "Journalling, reflecting, or listening to calming music",
                "D", "Reading, learning, or practising a skill"
            ),

            question(28,
                "What kind of compliment feels most accurate?",
                "A", "\"You are reliable when it matters.\"",
                "B", "\"You bring warmth and life into a room.\"",
                "C", "\"You understand people deeply.\"",
                "D", "\"You think with rare care and intention.\""
            ),

            question(29,
                "When making an important life choice, you prioritise...",
                "A", "Security and long-term stability",
                "B", "Excitement, growth, and new experiences",
                "C", "Emotional alignment and personal meaning",
                "D", "Clarity, purpose, and careful reasoning"
            ),

            question(30,
                "Which image feels most like your own growth?",
                "A", "Strong roots spreading deeper each year",
                "B", "Bright leaves changing boldly with every season",
                "C", "Branches bending gently but never breaking",
                "D", "A carefully shaped form refined slowly over time"
            )
        ));
    }

    private AnswerOption option(Question question, String label, String text) {
        return AnswerOption.builder()
            .question(question)
            .optionLabel(label)
            .optionText(text)
            .build();
    }
}
