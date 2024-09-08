package main;

import java.util.*;

public class DeepThought {
    public static void main(String[] args) {

        QuestionProcessorService questionProcessorService = new QuestionProcessorService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter 1 to ask a question, 2 to add a question:");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Enter your question:");
                    String question = scanner.nextLine();
                    questionProcessorService.askQuestion(question);
                    break;
                case "2":
                    System.out.println("Enter your question and answers in the format:");
                    System.out.println("<question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"");
                    String input = scanner.nextLine();
                    questionProcessorService.addQuestion(input);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}