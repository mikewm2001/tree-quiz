# TreeQuiz

A tree-themed personality quiz web application built with Spring Boot and Thymeleaf.

Answer 30 personality questions and discover which of 10 tree types best reflects who you are — along with your best and most challenging compatibility matches.

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
| `GET /quiz`     | 30-question personality quiz            |
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

Each of the 30 questions has 4 answer options. Each answer awards points to 2–3 tree types. The first tree in each mapping receives a **direct hit** used in tie-breaking.

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

On first startup, `DataInitializerService` automatically seeds all 10 tree personalities, compatibility records, and 30 quiz questions. Subsequent restarts skip seeding (idempotent check via `count() == 0`).

---

## CI/CD

Every push to `main` triggers a GitHub Actions workflow that builds the application and deploys it to AWS Elastic Beanstalk.

### Workflow summary

| Step | Action |
|------|--------|
| Build | `./mvnw clean package -DskipTests` |
| Auth | OIDC — no stored AWS credentials |
| Upload | JAR uploaded to S3 under `deployments/<sha>/` |
| Release | New EB application version created, labeled with the Git SHA |
| Deploy | `Treepersonalityquiz-env-3` updated to the new version |
| Wait | Workflow blocks until EB reports the environment update is complete |

Workflow file: [.github/workflows/deploy.yml](.github/workflows/deploy.yml)

### AWS-side setup (one-time)

1. **OIDC identity provider** — add GitHub's OIDC provider to IAM:
   - Provider URL: `https://token.actions.githubusercontent.com`
   - Audience: `sts.amazonaws.com`

2. **IAM role** — `GitHubActionsElasticBeanstalkDeployRole` (account `730335405205`) needs the following:
   - Trust policy allowing `token.actions.githubusercontent.com` with condition `repo:mikewm2001/tree-quiz:ref:refs/heads/main`
   - Permissions: current setup uses `AdministratorAccess-AWSElasticBeanstalk` and `AmazonS3FullAccess`. These can be tightened later to least-privilege permissions for Elastic Beanstalk deployment and S3 access to the Beanstalk deploy bucket.

3. **S3 bucket** — use the existing Elastic Beanstalk service bucket in `us-east-1`: `elasticbeanstalk-us-east-1-730335405205`. The bucket name is referenced in the workflow as the `EB_DEPLOY_BUCKET` repository variable.

### GitHub repository settings

Under **Settings → Secrets and variables → Actions → Variables**, add:

| Variable | Value |
|----------|-------|
| `EB_DEPLOY_BUCKET` | `elasticbeanstalk-us-east-1-730335405205` |

No repository secrets are needed — authentication is entirely OIDC-based.

### Manual deploy fallback

Requires the AWS CLI to be configured locally with permissions to upload to S3 and update Elastic Beanstalk.

```bash
VERSION_LABEL=manual-$(date +%Y%m%d%H%M%S)
BUCKET=elasticbeanstalk-us-east-1-730335405205

./mvnw clean package -DskipTests

aws s3 cp target/treequiz-0.0.1-SNAPSHOT.jar \
  s3://$BUCKET/deployments/manual/treequiz-0.0.1-SNAPSHOT.jar

aws elasticbeanstalk create-application-version \
  --application-name treepersonalityquiz \
  --version-label $VERSION_LABEL \
  --source-bundle S3Bucket=$BUCKET,S3Key=deployments/manual/treequiz-0.0.1-SNAPSHOT.jar

aws elasticbeanstalk update-environment \
  --application-name treepersonalityquiz \
  --environment-name Treepersonalityquiz-env-3 \
  --version-label $VERSION_LABEL
```

### Rollback

In the AWS Console, go to **Elastic Beanstalk → treepersonalityquiz → Application versions**, select any previous version, and choose **Deploy**. Or via CLI:

```bash
aws elasticbeanstalk update-environment \
  --application-name treepersonalityquiz \
  --environment-name Treepersonalityquiz-env-3 \
  --version-label <previous-git-sha>
```

### Testing the workflow safely

1. Push a trivial change (e.g., a README edit) to `main` and watch the **Actions** tab.
2. Confirm the workflow reaches the "Wait" step without error.
3. Check the EB environment health in the AWS Console after the workflow completes.
4. If deployment fails, check the EB events/logs and redeploy a previous application version from the Elastic Beanstalk console.

---

## Modifying Quiz Content

**Questions / answers** — edit the `seedQuestions()` method in `DataInitializerService.java`.

**Answer → tree mappings** — edit `ScoringConfig.java`. Each question maps to a `Map<optionLabel, List<TreeType>>`. The first tree in each list is the primary tree (counts as a direct hit in tie-breaking).

**Tree descriptions / traits / colors** — edit the `seedTreePersonalities()` method in `DataInitializerService.java`.

**Compatibility** — edit `seedTreeCompatibilities()` in `DataInitializerService.java`.

> After changing seed data, clear the relevant database tables so the seeder runs again on next startup.
