package org.example;

import java.util.ArrayDeque;
import java.util.Scanner;

public class MethodTestFile {
    public static void main(String[] args) {
        System.out.println("test lol");
        Scanner scanner = new Scanner(System.in);
        
        //TermsList tests
        ArrayDeque<TermsList> termsLists = DAL.showLists();

        TermsList x = null;

        for (TermsList term : termsLists) {
            x = term;
        }

        //DAL.tlUpdatePrompt(x, scanner);

//----------------------------------------------------------------------------------------------------

        //Syntax Flashcards tests
        ArrayDeque<SyntaxFlashcardsDeck> sfDecks = DAL.showDecks();

        SyntaxFlashcardsDeck z = null;

        for(SyntaxFlashcardsDeck deck : sfDecks) {
            z = deck;
        }

        DAL.sfRemoveCard(z, scanner);




    }
}
