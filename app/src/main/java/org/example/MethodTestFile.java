package org.example;

import java.util.ArrayDeque;
import java.util.Scanner;

public class MethodTestFile {
    public static void main(String[] args) {
        System.out.println("test lol");
        Scanner scanner = new Scanner(System.in);
        

        ArrayDeque<TermsList> termsList = DAL.showLists();

        TermsList x = null;

        for (TermsList term : termsList) {
            x = term;
        }

        DAL.tlUpdatePrompt(x, scanner);


    }
}
