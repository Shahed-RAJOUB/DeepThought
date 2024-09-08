package test;

import main.QuestionProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class QuestionProcessorServiceTest {

    private QuestionProcessorService service;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        service = new QuestionProcessorService();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testAskQuestion_EmptyQuestion() {
        service.askQuestion("");
        assertEquals("A question is required.", outContent.toString().trim());
    }

    @Test
    void testAskQuestion_QuestionTooLong() {
        String longQuestion = "a".repeat(256) + "?";
        service.askQuestion(longQuestion);
        assertEquals("Question is too long. Max length is 255 characters.", outContent.toString().trim());
    }

    @Test
    void testAskQuestion_NoMatchingAnswer() {
        service.askQuestion("What is the meaning of life?");
        assertEquals("the answer to life, universe and everything is 42", outContent.toString().trim());
    }

    @Test
    void testAskQuestion_WithMatchingAnswer() {
        service.addQuestion("What is the capital of France? \"Paris\"");
        outContent.reset();
        service.askQuestion("What is the capital of France?");
        assertEquals("- Paris", outContent.toString().trim());
    }

    @Test
    void testAskQuestion_WithoutQuestionMark() {
        service.addQuestion("What is the capital of Germany? \"Berlin\"");
        outContent.reset();
        service.askQuestion("What is the capital of Germany");
        assertEquals("- Berlin", outContent.toString().trim());
    }

    @Test
    void testAddQuestion_InvalidFormat() {
        service.addQuestion("Invalid question format");
        assertEquals("Invalid format. Please use: <question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"", outContent.toString().trim());
    }

    @Test
    void testAddQuestion_QuestionTooLong() {
        String longQuestion = "a".repeat(256) + "? \"Answer\"";
        service.addQuestion(longQuestion);
        assertEquals("Question is too long. Max length is 255 characters.", outContent.toString().trim());
    }

    @Test
    void testAddQuestion_AnswerTooLong() {
        String questionWithLongAnswer = "Short question? \"" + "a".repeat(256) + "\"";
        service.addQuestion(questionWithLongAnswer);
        assertEquals("Question is too long. Max length is 255 characters.", outContent.toString().trim());
    }

    @Test
    void testAddQuestion_NoAnswer() {
        service.addQuestion("What is the capital of Italy? ");
        assertEquals("At least one answer is required.", outContent.toString().trim());
    }

    @Test
    void testAddQuestion_SuccessfulAddition() {
        service.addQuestion("What is the capital of Spain? \"Madrid\"");
        assertEquals("Question and answers added successfully.", outContent.toString().trim());
    }

    @Test
    void testAddQuestion_MultipleAnswers() {
        service.addQuestion("What are the primary colors? \"Red\" \"Blue\" \"Yellow\"");
        assertEquals("Question and answers added successfully.", outContent.toString().trim());
        outContent.reset();
        service.askQuestion("What are the primary colors?");
        String expected = "- Red" + System.lineSeparator() + "- Blue" + System.lineSeparator() + "- Yellow";
        assertEquals(expected, outContent.toString().trim());
    }
}