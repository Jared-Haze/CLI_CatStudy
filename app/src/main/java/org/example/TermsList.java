package org.example;

import java.util.HashSet;
import java.util.Scanner;

public class TermsList extends StudyCat {
    public String itemType;
    public String catType = "[terms list]";

    TermsList(int id, String studyCatName, String question, String itemType) {
        super(id, studyCatName, question);
        this.itemType = itemType;
    }

    /*
    public String getSCString() {
        String studyCatString = "- " + studyCatName + " |" + catType;
        return studyCatString;
    }
    */

    public static void termsListStudyCatCycle(StudyCat currentStudyCat, Scanner scanner) {
        //makes model object for answers using chosen study cat id
        HashSet<StudyCatAnswer> answers = new HashSet<>(); 
        for (StudyCatAnswer answer : DAL.getListAnswers(currentStudyCat.id)) {
            answers.add(answer);
        }

        //make model object for answers String value (convert list of answers to list of just each answer's string)
        HashSet<String> answersReference = new HashSet<>();
        for (StudyCatAnswer answer : answers) {
            String reference = answer.answer;
            answersReference.add(reference);
        }

        //makes list for answered terms
        HashSet<String> answeredList = new HashSet<>();

        //cycle through input and references 
        while (!answersReference.isEmpty()) {
            System.out.println(currentStudyCat.Prompt);
            String inputAnswer = scanner.nextLine().strip();
            if (answersReference.contains(inputAnswer)) {
                answersReference.remove(inputAnswer);
                answeredList.add(inputAnswer);
                System.out.println("correct!" + "\n");
            } else if (answeredList.contains(inputAnswer)) {
                System.out.println("you already got that one." + "\n");
            } else {
                System.out.println("wrong answer," + "\n");
            }
            if (answersReference.isEmpty()) {continue;}
            System.out.println("next guess...");
        }
        System.out.println("seems like you gottem all. Good job!");
    }
}
