package main;

import java.util.*;

public class QuestionProcessorService {

    private final Map<String, List<String>> qaStore = new HashMap<>();
    private static final String DEFAULT_ANSWER = "the answer to life, universe and everything is 42";
    private static final String QUESTION_FORMAT = "Invalid format. Please use: <question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"";
    private static final String SUCCESSFUL_QUESTION_ADDITION = "Question and answers added successfully.";
    private static final String OVER_LIMIT_WARNING = "Question is too long. Max length is 255 characters.";
    private static final String MISSING_ANSWER_WARNING = "At least one answer is required.";
    private static final String MISSING_QUESTION_WARNING = "A question is required.";
    private static final int MAX_LENGTH = 255;

    public void askQuestion(String question) {

        if(!question.isEmpty()){

            String cleanQuestion = question.trim();

            char lastChar = cleanQuestion.charAt(cleanQuestion.length() - 1);

            if(lastChar == '?'){
                String keyQuestion = cleanQuestion.substring(0, cleanQuestion.length() - 1);
                if (keyQuestion.length() > MAX_LENGTH) {
                    System.out.println(OVER_LIMIT_WARNING);
                    return;
                }
                List<String> answers = qaStore.get(keyQuestion);
                printAnswers(answers);
            }else{
                if (cleanQuestion.length() > MAX_LENGTH) {
                    System.out.println(OVER_LIMIT_WARNING);
                    return;
                }
                List<String> answers = qaStore.get(cleanQuestion);
                printAnswers(answers);
            }

        }else{
            System.out.println(MISSING_QUESTION_WARNING);
        }

    }

    private void printAnswers(List<String> answers) {
        if (answers == null) {
            System.out.println(DEFAULT_ANSWER);
        } else {
            for (String answer : answers) {
                System.out.println("- " + answer);
            }
        }
    }

    public void addQuestion(String input) {

        String[] parts = input.split("\\?", 2);
        if (parts.length != 2) {
            System.out.println(QUESTION_FORMAT);
            return;
        }

        String question = parts[0].trim();
        String answersInput = parts[1].trim();


        if (question.length() > MAX_LENGTH) {
            System.out.println(OVER_LIMIT_WARNING);
            return;
        }

        List<String> answers = new ArrayList<>();

        String[] answerParts = answersInput.split("\"");
        for (String answer : answerParts) {
            answer = answer.trim();
            if (!answer.isEmpty()) {
                if (answer.length() > MAX_LENGTH) {
                    System.out.println(OVER_LIMIT_WARNING);
                    return;
                }
                answers.add(answer);
            }
        }

        if (answers.isEmpty()) {
            System.out.println(MISSING_ANSWER_WARNING);
            return;
        }

        qaStore.put(question, answers);
        System.out.println(SUCCESSFUL_QUESTION_ADDITION);
    }
}
