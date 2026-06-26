package org.example;

import java.util.Scanner;

public class MethodTestFile {
    public static void main(String[] args) {
        System.out.println("test lol");
        Scanner scanner = new Scanner(System.in);
        DAL.newSyntaxFlashcards(scanner);
    }
}
