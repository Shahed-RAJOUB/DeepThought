package test;

import main.DeepThought;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DeepThoughtIntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("when the task is asking a question that does not exist expect the answer to life")
    void testAskQuestion() {
        String input = "1\nWhat is the meaning of life?\n3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Thread thread = new Thread(() -> DeepThought.main(new String[]{}));
        thread.start();

        try {
            Thread.sleep(100); // Give some time for the program to process
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();

        String output = outContent.toString();
        assertTrue(output.contains("Enter 1 to ask a question, 2 to add a question:"));
        assertTrue(output.contains("Enter your question:"));
        assertTrue(output.contains("the answer to life, universe and everything is 42"));
    }

    @Test
    @DisplayName("when the task is adding a question expected to be added and answered")
    void testAddQuestion() {
        String input = "2\nWhat is the capital of France? \"Paris\"\n1\nWhat is the capital of France?\n3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Thread thread = new Thread(() -> DeepThought.main(new String[]{}));
        thread.start();

        try {
            Thread.sleep(100); // Give some time for the program to process
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();

        String output = outContent.toString();
        assertTrue(output.contains("Enter 1 to ask a question, 2 to add a question:"));
        assertTrue(output.contains("Enter your question and answers in the format:"));
        assertTrue(output.contains("<question>? \"<answer1>\" \"<answer2>\" \"<answerX>\""));
        assertTrue(output.contains("Question and answers added successfully."));
        assertTrue(output.contains("Enter your question:"));
        assertTrue(output.contains("- Paris"));
    }

    @Test
    @DisplayName("when the choice of task is invalid expect to write a warning and ask again to choose")
    void testInvalidChoice() {
        String input = "5\n3\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Thread thread = new Thread(() -> DeepThought.main(new String[]{}));
        thread.start();

        try {
            Thread.sleep(100); // Give some time for the program to process
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();

        String output = outContent.toString();
        assertTrue(output.contains("Enter 1 to ask a question, 2 to add a question:"));
        assertTrue(output.contains("Invalid choice. Please try again."));
    }
}