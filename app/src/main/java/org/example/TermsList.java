package org.example;

public class TermsList extends StudyCat {
    public String question;
    public String itemType;
    public String catType = "[terms list]";

    TermsList(int id, String studyCatName, String question, String itemType) {
        super(id, studyCatName);
        this.question = question;
        this.itemType = itemType;
    }

    /*
    public String getSCString() {
        String studyCatString = "- " + studyCatName + " |" + catType;
        return studyCatString;
    }
    */
}
