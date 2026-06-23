package org.example;

public class SyntaxFlashcard extends StudyCatAnswer {
    String question;
    int deck_id;

    SyntaxFlashcard(int id, String question, String answer, int deck_id) {
        super(id, answer);
        this.question = question;
        this.deck_id = deck_id;
    }
}
