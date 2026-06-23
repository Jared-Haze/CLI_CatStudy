package org.example;

import java.util.HashSet;
import java.util.Scanner;

public class TermsList extends StudyCat {
    public String itemType;
    public String prompt;
    public String catType = "[terms list]";

    TermsList(int id, String studyCatName, String prompt, String itemType) {
        super(id, studyCatName);
        this.itemType = itemType;
        this.prompt = prompt;
    }


    public static void termsListStudyCatCycle(TermsList currentTermsList, Scanner scanner) {
        
        //makes model object for answers using chosen study cat id
        HashSet<TLAnswer> answers = new HashSet<>(); 
        for (TLAnswer answer : DAL.getListAnswers(currentTermsList.id)) {
            answers.add(answer);
        }

        //make model object for answers String value (convert list of answers to list of just each answer's string)
        HashSet<String> answersReference = new HashSet<>();
        for (TLAnswer answer : answers) {
            String reference = answer.answer;
            answersReference.add(reference);
        }

        //makes list for answered terms
        HashSet<String> answeredList = new HashSet<>();

        //cycle through input and references 
        while (!answersReference.isEmpty()) {
            System.out.println(currentTermsList.prompt);
            String inputAnswer = scanner.nextLine().strip();
            
            if (answersReference.contains(inputAnswer)) {
                answersReference.remove(inputAnswer);
                answeredList.add(inputAnswer);
                
                System.out.println("correct!" + itemsLeft(answersReference, currentTermsList.itemType) + "\n");
            } else if (answeredList.contains(inputAnswer)) {
                System.out.println("you already got that one." + itemsLeft(answersReference, currentTermsList.itemType) + "\n");
            } else {
                System.out.println("wrong answer," + itemsLeft(answersReference, currentTermsList.itemType) + "\n");
            }
            if (answersReference.isEmpty()) {continue;}
            System.out.println("next guess...");
        }
        System.out.println("seems like you gottem all. Good job!");
    }

    public static String itemsLeft(HashSet<String> answersReference, String itemType) {
        int size = answersReference.size();
        String s = (size == 1) ? "" : "s";
        String itemsLeft = " " + size + " " + itemType + s + " left.";
        return itemsLeft;
    }
}
