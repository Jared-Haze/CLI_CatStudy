package org.example;

public class TermsListsPrompt {
    public int id;
    public String listName;
    public String question;
    public String itemType;
    public String catType = "[terms list]";

    TermsListsPrompt(int id, String listName, String question, String itemType) {
        this.id = id;
        this.listName = listName;
        this.question = question;
        this.itemType = itemType;
    }

    public void getTermsList() {
        System.out.println("- " + listName + " |" + catType);
    }
}
