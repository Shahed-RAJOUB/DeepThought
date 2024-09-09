package test;

import main.QuestionProcessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("when (askQuestion) with empty question expect missing question warning")
    void testAskQuestion_EmptyQuestion() {
        service.askQuestion("");
        assertEquals("A question is required.", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (askQuestion) with too long question expect oversize warning")
    void testAskQuestion_QuestionTooLong() {
        String longQuestion = "a".repeat(256) + "?";
        service.askQuestion(longQuestion);
        assertEquals("Question is too long. Max length is 255 characters.", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (askQuestion) with a question that does not exist expect the answer to life")
    void testAskQuestion_NoMatchingAnswer() {
        service.askQuestion("What is the meaning of life?");
        assertEquals("the answer to life, universe and everything is 42", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (askQuestion) with a existing question with question mark expect its answer")
    void testAskQuestion_WithMatchingAnswer() {
        service.addQuestion("What is the capital of France? \"Paris\"");
        outContent.reset();
        service.askQuestion("What is the capital of France?");
        assertEquals("- Paris", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (askQuestion) with a existing question without question mark expect its answer")
    void testAskQuestion_WithoutQuestionMark() {
        service.addQuestion("What is the capital of Germany? \"Berlin\"");
        outContent.reset();
        service.askQuestion("What is the capital of Germany");
        assertEquals("- Berlin", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (addQuestion) of invalid format expect invalid format warning")
    void testAddQuestion_InvalidFormat() {
        service.addQuestion("Invalid question format");
        assertEquals("Invalid format. Please use: <question>? \"<answer1>\" \"<answer2>\" \"<answerX>\"", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (addQuestion) of too long question expect oversize warning")
    void testAddQuestion_QuestionTooLong() {
        String longQuestion = "a".repeat(256) + "? \"Answer\"";
        service.addQuestion(longQuestion);
        assertEquals("Question is too long. Max length is 255 characters.", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (addQuestion) of too long answers expect oversize warning")
    void testAddQuestion_AnswerTooLong() {
        String questionWithLongAnswer = "Short question? \"" + "a".repeat(256) + "\"";
        service.addQuestion(questionWithLongAnswer);
        assertEquals("Question is too long. Max length is 255 characters.", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (addQuestion) of without answers expect Missing answer warning")
    void testAddQuestion_NoAnswer() {
        service.addQuestion("What is the capital of Italy? ");
        assertEquals("At least one answer is required.", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (addQuestion) of valid format expect question successfully added")
    void testAddQuestion_SuccessfulAddition() {
        service.addQuestion("What is the capital of Spain? \"Madrid\"");
        assertEquals("Question and answers added successfully.", outContent.toString().trim());
    }

    @Test
    @DisplayName("when (addQuestion) of valid format of multiple answers expect question successfully added")
    void testAddQuestion_MultipleAnswers() {
        service.addQuestion("What are the primary colors? \"Red\" \"Blue\" \"Yellow\"");
        assertEquals("Question and answers added successfully.", outContent.toString().trim());
        outContent.reset();
        service.askQuestion("What are the primary colors?");
        String expected = "- Red" + System.lineSeparator() + "- Blue" + System.lineSeparator() + "- Yellow";
        assertEquals(expected, outContent.toString().trim());
    }
}