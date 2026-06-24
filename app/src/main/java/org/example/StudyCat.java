package org.example;

import java.util.Scanner;

public abstract class StudyCat {
    int id;
    String studyCatName;
    String catType;

    StudyCat (int id, String studyCatName, String catType) {
        this.id = id;
        this.studyCatName = studyCatName;
        this.catType = catType;
    }

}
