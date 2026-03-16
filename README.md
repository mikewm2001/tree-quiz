# TreeQuiz

A tree-themed personality quiz web application built with Spring Boot and Thymeleaf.

Answer 20 personality questions and discover which of 10 tree types best reflects who you are — along with your best and most challenging compatibility matches.

---

## Tech Stack

| Layer       | Technology                        |
|-------------|-----------------------------------|
| Backend     | Spring Boot 4, Spring Web MVC     |
| Persistence | Spring Data JPA, PostgreSQL       |
| Templates   | Thymeleaf (server-side rendering) |
| Build       | Maven                             |

---

## Pages

| Route           | Description                             |
|-----------------|-----------------------------------------|
| `GET /`         | Home page with hero section             |
| `GET /quiz`     | 20-question personality quiz            |
| `POST /quiz/submit` | Submit answers, redirect to result  |
| `GET /result?tree=OAK` | Personality result page        |
| `GET /trees`    | Directory of all 10 tree personalities  |

---

## Tree Personalities

| Tree           | Title                   |
|----------------|-------------------------|
| Oak            | The Steady Guardian     |
| Maple          | The Warm Creative       |
| Willow         | The Gentle Dreamer      |
| Pine           | The Quiet Survivor      |
| Birch          | The Fresh Start         |
| Redwood        | The Quiet Giant         |
| Cherry Blossom | The Joyful Romantic     |
| Cedar          | The Calm Protector      |
| Aspen          | The Lively Connector    |
| Bonsai         | The Intentional Thinker |

---

## Scoring

Each of the 20 questions has 4 answer options. Each answer awards points to 2–3 tree types. The first tree in each mapping receives a **direct hit** used in tie-breaking.

**Tie-break order** (applied when scores are equal):
1. Highest total points
2. Highest number of direct answer hits (first-choice tree per answer)
3. Fixed priority: `REDWOOD > OAK > CEDAR > BONSAI > PINE > MAPLE > WILLOW > BIRCH > CHERRY_BLOSSOM > ASPEN`

Scoring rules live entirely in `ScoringConfig.java` and can be edited without touching any other class.

---

## Project Structure

```
src/main/java/com/example/treequiz/
├── controller/
│   ├── HomeController.java
│   ├── QuizController.java
│   ├── QuizSubmissionForm.java
│   ├── ResultController.java
│   └── TreeDirectoryController.java
├── entity/
│   ├── enums/
│   │   ├── CompatibilityType.java
│   │   └── TreeType.java
│   ├── AnswerOption.java
│   ├── Question.java
│   ├── QuizResult.java
│   ├── TreeCompatibility.java
│   └── TreePersonality.java
├── repository/
│   ├── AnswerOptionRepository.java
│   ├── QuestionRepository.java
│   ├── QuizResultRepository.java
│   ├── TreeCompatibilityRepository.java
│   └── TreePersonalityRepository.java
├── service/
│   ├── DataInitializerService.java   ← seeds DB on first startup
│   ├── QuizService.java              ← scoring + tie-break logic
│   ├── ScoringConfig.java            ← answer → tree mappings
│   └── TreePersonalityService.java
└── TreequizApplication.java

src/main/resources/
├── static/css/style.css
├── templates/
│   ├── fragments/header.html
│   ├── home.html
│   ├── quiz.html
│   ├── result.html
│   └── trees.html
├── application.properties
└── application-local.properties      ← DB credentials (git-ignored)
```

---

## Running Locally

### Prerequisites

- Java 17+
- PostgreSQL running on `localhost:5432`
- A database named `treequiz`

### Setup

1. Create the database:

```sql
CREATE DATABASE treequiz;
```

2. Set your credentials in `src/main/resources/application-local.properties`:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Run with Maven:

```bash
./mvnw spring-boot:run
```

The app starts on [http://localhost:8080](http://localhost:8080).

On first startup, `DataInitializerService` automatically seeds all 10 tree personalities, compatibility records, and 20 quiz questions. Subsequent restarts skip seeding (idempotent check via `count() == 0`).

---

## Modifying Quiz Content

**Questions / answers** — edit the `seedQuestions()` method in `DataInitializerService.java`.

**Answer → tree mappings** — edit `ScoringConfig.java`. Each question maps to a `Map<optionLabel, List<TreeType>>`. The first tree in each list is the primary tree (counts as a direct hit in tie-breaking).

**Tree descriptions / traits / colors** — edit the `seedTreePersonalities()` method in `DataInitializerService.java`.

**Compatibility** — edit `seedTreeCompatibilities()` in `DataInitializerService.java`.

> After changing seed data, clear the relevant database tables so the seeder runs again on next startup.
