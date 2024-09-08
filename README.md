# DeepThought Question-Answer Program

## Overview

DeepThought is a simple Java program that allows users to ask questions and receive answers. It also provides functionality to add new questions and answers to its knowledge base. The program is named after the supercomputer in Douglas Adams' "The Hitchhiker's Guide to the Galaxy" that came up with the answer to the ultimate question of life, the universe, and everything.

## Features

- Ask questions and receive answers
- Add new questions and answers to the knowledge base
- Default answer for unknown questions
- Input validation and error handling

## Components

1. `DeepThought.java`: The main class that handles user interaction.
2. `QuestionProcessorService.java`: A service class that processes questions and manages the knowledge base.

## How to Run

### Prerequisites

- Java Development Kit (JDK) 8 or higher installed on your system
- A Java IDE (like IntelliJ IDEA, Eclipse) or a text editor and command line interface

### Steps

1. Clone or download the repository to your local machine.

2. Open a terminal or command prompt and navigate to the directory containing the Java files.

3. Compile the Java files:
   ```
   javac DeepThought.java QuestionProcessorService.java
   ```

4. Run the program:
   ```
   java DeepThought
   ```

5. Follow the on-screen prompts to interact with the program:
    - Enter `1` to ask a question
    - Enter `2` to add a new question and its answer(s)

### Usage Examples

#### Asking a Question
```
Enter 1 to ask a question, 2 to add a question:
1
Enter your question:
What is the meaning of life?
the answer to life, universe and everything is 42
```

#### Adding a Question
```
Enter 1 to ask a question, 2 to add a question:
2
Enter your question and answers in the format:
<question>? "<answer1>" "<answer2>" "<answerX>"
What is the capital of France? "Paris"
Question and answers added successfully.
```

## Running Tests

To run the tests for this program, you'll need JUnit 5 in your classpath. You can run the tests using your IDE's test runner or via the command line if you have JUnit set up correctly.

1. `QuestionProcessorServiceTest.java`: Unit tests for the QuestionProcessorService class.
2. `DeepThoughtIntegrationTest.java`: Integration tests for the DeepThought class.

## Notes

- The program runs in an infinite loop. To exit, you'll need to terminate the program manually (e.g., by pressing Ctrl+C in the terminal).
- Questions are case-sensitive and must end with a question mark when adding them to the knowledge base.
- The maximum length for questions and answers is 255 characters.

## Future Improvements

- Add a feature to save and load the knowledge base from a file
- Implement a more sophisticated matching algorithm for questions
- Add an option to exit the program gracefully
- Implement a web interface for easier interaction

Feel free to contribute to this project by submitting pull requests or reporting issues!