package org.example;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public interface StudyCatMethods {


    public static void askContinue(Scanner scanner) {
        System.out.print("continue(yes/no)?: ");
        String input = scanner.nextLine().strip();
        if (Set.of("yes", "y", "").contains(input)) {
            System.out.println("Great!\n");
        } else if (input.equalsIgnoreCase("no")) {
            System.out.println("you'll have to come back later...\nEnding program.");
            scanner.close();
            System.exit(0);
        } else {
            System.out.println("confusing input, continuing study...\n");
        }
    }

    public static void askContinue(Scanner scanner, HashSet<String> answeredList, HashSet<String> answersReference) {
        System.out.println("continue: (yes/no) | \nsee answered items: (W) | \nsee unanswered items(ends practice early): (L)");
        String input = scanner.nextLine().strip();
        switch (input) {
            case "yes", "y", "" -> System.out.println("Great!\n");

            case "no" -> {
                System.out.println("you'll have to come back later...\nEnding program.");
                scanner.close();
                System.exit(0);
            }

            case "w", "W" -> {
                System.out.println("showing answerd items: ");
                System.out.println(answeredList);
                System.out.println("that's what you have so far, let's see you add some more...\n");
            }

            case "l", "L" -> {
                System.out.println("seems like you sturggle with these terms: \n" + answersReference);
                System.out.println("better keep practicing\nEnding program.");
                scanner.close();
                System.exit(0);
            }

            default -> System.out.println("confusing input, continuing study...\n");
        }
    }

}
