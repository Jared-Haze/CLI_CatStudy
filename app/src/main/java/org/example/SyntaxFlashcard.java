package org.example;

public class SyntaxFlashcard extends StudyCatAnswer {
    String question;

    SyntaxFlashcard(int id, String question, String answer) {
        super(id, answer);
        this.question = question;
    }
}
