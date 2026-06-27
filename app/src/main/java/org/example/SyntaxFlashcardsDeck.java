package org.example;

import java.util.Map;
import java.util.Scanner;
import java.util.SequencedMap;

public class SyntaxFlashcardsDeck extends StudyCat{
    public String catType = "[syntax flashcards deck]";
    
    SyntaxFlashcardsDeck(int id, String deck_name) {
        super(id, deck_name, "[syntax flashcards deck]");
    }



    
    public static void syntaxFlashcardsStudyCatCycle(int currentSFDeck_id, Scanner scanner) {
        SequencedMap<String, String> flashcards = DAL.getDeckAnswers(currentSFDeck_id);
        
        while (!flashcards.isEmpty()) {
            Map.Entry<String, String> card = flashcards.pollFirstEntry();

            System.out.println(card.getKey());
            String input = scanner.nextLine().strip();

            if (input.equals(card.getValue())) {
                System.out.println("✅correct!✅" + cardsLeft(flashcards));
                if (flashcards.isEmpty()) {break;}
                StudyCatMethods.askContinue(scanner);
            } else {
                flashcards.putLast(card.getKey(), card.getValue());
                System.out.println("❌wrong answer❌..." + cardsLeft(flashcards) + "\nthe correct answer was: " + card.getValue());
                StudyCatMethods.askContinue(scanner);
            }
        }
        System.out.println("😎congrats! you completed this deck!!!");
    }

    public static String cardsLeft(SequencedMap<String, String> flashcards) {
        int size = flashcards.size();
        String s = (size == 1) ? "" : "s";
        String itemsLeft = " " + size + " flashcard" + s + " left.";
        return itemsLeft;
    }


    
}
