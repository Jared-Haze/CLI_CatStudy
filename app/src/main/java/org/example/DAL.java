package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.SequencedMap;

import javax.naming.spi.DirStateFactory.Result;

public class DAL {
    
    /*
    Better Understanding of DAL Methods 

    Home screen/Database "Hero" display : Read Query - returns rs of table(s) needed for the first database data 
    display.
    - First you have to define what that Hero display is/will show (; list of available study Cats for the user to 
    choose from). 
    - Second you make a database query method to produce a result set(s) of needed tables.
    - Then you map the ResultSet(s) row to an entity(;ORM)/model object(;MVC) to actually show, use, & manipulate in 
    code. You're creating a study Cat "entity" from each database row. 
        - You'll also want to store those entities into a datastructure to represent the entire ResultSet (table) 
        in code (; adding study cat entities into a HashSet of study cats {or, more accurately, right now it's set up 
        to be in an ArrayDeque of TermsListsPropmts}).
    - finally, have that method return the datastructure representing the queried table, and use it however is needed
    in the code (; displaying the names of each study cat option from the database table of study cat options in your
    database hero display).
    */ 


    public static ArrayDeque<TermsList> showLists() {
        ArrayDeque<TermsList> termsLists = new ArrayDeque();

        try(Connection conn = JDBC.getConnection()) {
            String sql = "SELECT * FROM termsListsPrompts ORDER BY id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            TermsList termsList;
            while (rs.next()) {
                termsList = new TermsList(rs.getInt("id"), rs.getString("list_name"), rs.getString("question"), rs.getString("item_type"));
                termsLists.add(termsList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return termsLists;
    }

    public static ArrayDeque<SyntaxFlashcardsDeck> showDecks() {
        ArrayDeque<SyntaxFlashcardsDeck> decksList = new ArrayDeque<>();

        try(Connection conn = JDBC.getConnection()) {
            String sql = "SELECT * FROM syntaxFlashcardsDecks ORDER BY id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            SyntaxFlashcardsDeck deck;
            while (rs.next()) {
                deck = new SyntaxFlashcardsDeck(rs.getInt("id"), rs.getString("deck_name"));
                decksList.add(deck);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return decksList;
    }

    public static HashSet<TLAnswer> getListAnswers (int list_id) {
        HashSet<TLAnswer> listAnswers = new HashSet<>();

        try(Connection conn = JDBC.getConnection()) {
            String sql = "SELECT * FROM termslistanswers WHERE question_id = ? ORDER BY id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, list_id);

            ResultSet rs = ps.executeQuery();
            TLAnswer tlAnswer;
            while(rs.next()) {
                tlAnswer = new TLAnswer(rs.getInt("id"), rs.getString("answer"));
                listAnswers.add(tlAnswer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAnswers;
    }

    public static SequencedMap<String, String> getDeckAnswers(int deck_id) {
        SequencedMap<String, String> flashcards = new LinkedHashMap<>();

        try(Connection conn = JDBC.getConnection()) {
            String sql = "SELECT * FROM syntaxFlashcards WHERE deck_id = ? ORDER BY id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, deck_id);

            ResultSet rs = ps.executeQuery();
            SyntaxFlashcard flashcard;
            while(rs.next()) {
                flashcard = new SyntaxFlashcard(rs.getInt("id"), rs.getString("question"), rs.getString("answer"));
                flashcards.put(flashcard.question, flashcard.answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flashcards;
    }

    public static void newTermsList(Scanner scanner) {

        try(Connection conn = JDBC.getConnection()) {
            //input termslistprompt
            String sql = "INSERT INTO termslistsprompts (list_name, question, item_type) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            //prompts and inputs
            System.out.print("Enter the list name of the terms list: ");
            String list_name = scanner.nextLine();
            System.out.print("Enter the question/prompt for the terms list: ");
            String question = scanner.nextLine();
            System.out.print("Enter the item type of the list (; CLI flag, etc.): ");
            String item_type = scanner.nextLine();

            ps.setString(1, list_name);
            ps.setString(2, question);
            ps.setString(3, item_type);

            ps.executeUpdate();

            //get new termslistprompt id #
            String getId = "SELECT id from termslistsprompts WHERE list_name = ?;";
            PreparedStatement psId = conn.prepareStatement(getId);
            psId.setString(1, list_name);
            ResultSet newTermsListId = psId.executeQuery();
            int newId = -1;
            if (newTermsListId.next()) {
                newId = newTermsListId.getInt("id");
            } else {
                //shouldn't ever really happen though.
                throw new SQLException("No matching terms list found.");
            }
            //input answers
            String sql2 = "INSERT INTO termslistanswers (answer, question_id) VALUES (?, ?);";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            System.out.print("Input the # of answer references: ");
            int referencesAmount = scanner.nextInt();
            scanner.nextLine();

            //loop inputing answer references
            for (int i = 0; i < referencesAmount; i++) {
                //prompts and inputs (cycle)
                System.out.print("Enter item/answer: ");
                String tlanswer = scanner.nextLine();
                ps2.setString(1, tlanswer);
                ps2.setInt(2, newId);
                ps2.executeUpdate();
            }
            System.out.println("you've successfully added a new terms list study cat!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void newSyntaxFlashcards(Scanner scanner) {

        try(Connection conn = JDBC.getConnection()) {
            String sql = "INSERT INTO syntaxflashcardsdecks (deck_name) VALUES (?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.print("Enter the name of this syntax flashcards deck: ");
            String deck_name = scanner.nextLine();

            ps.setString(1, deck_name);

            ps.executeUpdate();

            //get new syntax flashcards id #
            String getId = "SELECT id from syntaxflashcardsdecks WHERE deck_name = ?;";
            PreparedStatement psId = conn.prepareStatement(getId);
            psId.setString(1, deck_name);
            ResultSet newDeckId = psId.executeQuery();
            int newId = -1;
            if (newDeckId.next()) {
                newId = newDeckId.getInt("id");
            } else {
                //shouldn't ever really happen though.
                throw new SQLException("No matching syntax flashcards deck found.");
            }

            //input answers
            String sql2 = "INSERT INTO syntaxflashcards (question, answer, deck_id) VALUES (?, ?, ?);";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            System.out.print("Input the # of flashcards: ");
            int flashcardsAmount = scanner.nextInt();
            scanner.nextLine();

            //loop inputing flashcards
            for (int i = 0; i < flashcardsAmount; i++) {
                //prompts and inputs (cycle)
                System.out.println("Enter question:");
                String sfquestion = scanner.nextLine();
                System.out.println("Enter answer (must be exact):");
                String sfanswer = scanner.nextLine();
                ps2.setString(1, sfquestion);
                ps2.setString(2, sfanswer);
                ps2.setInt(3, newId);
                ps2.executeUpdate();
            }
            System.out.println("you've successfully added a new terms list study cat!");

        }   catch (SQLException e) {e.printStackTrace();}
    }

    public static void deleteStudyCat(StudyCat currentStudyCat) {

        try(Connection conn = JDBC.getConnection()) {
            String cat_table;
            String answers_table;
            String foreign_key;
            if (currentStudyCat instanceof TermsList) {
                cat_table = "termslistsprompts";
                answers_table = "termslistanswers";
                foreign_key = "question_id";
            } else if (currentStudyCat instanceof SyntaxFlashcardsDeck) {
                cat_table = "syntaxflashcardsdecks";
                answers_table = "syntaxflashcards";
                foreign_key = "deck_id";
            } else {
                //shouldn't ever really happen though.
                throw new SQLException("current study cat is not a study cat type.");
            }

            String deleteCat = "DELETE FROM " + cat_table + " WHERE id = ?;";
            String deleteAnswers = "DELETE FROM " + answers_table + " WHERE " + foreign_key +  " = ?;";

            PreparedStatement ps = conn.prepareStatement(deleteCat);
            PreparedStatement ps2 = conn.prepareStatement(deleteAnswers);

            ps.setInt(1, currentStudyCat.id);
            ps2.setInt(1, currentStudyCat.id);

            
            ps2.executeUpdate();
            ps.executeUpdate();
            

            System.out.println("you've successfully deleted Study Cat: " + currentStudyCat.studyCatName);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tlAddTerm(StudyCat currentStudyCat, Scanner scanner) {
        try(Connection conn = JDBC.getConnection()) {
            int currentID = currentStudyCat.id;
            String sql = "INSERT INTO termslistanswers (answer, question_id) Values (?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(2, currentID);

            System.out.println("Enter new term:");
            String newTerm = scanner.nextLine();
            ps.setString(1, newTerm);
            ps.executeUpdate();
            System.out.println("New term successfully added to " + currentStudyCat.studyCatName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tlRemoveTerm(StudyCat currentStudyCat, Scanner scanner) {
        try(Connection conn = JDBC.getConnection()) {
            int currentID = currentStudyCat.id;
            String sql = "DELETE FROM termslistanswers WHERE answer = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            HashSet<TLAnswer> listAnswers = getListAnswers(currentID);

            System.out.println("These are the current list terms...");

            HashSet<String> answerStrings = new HashSet<>();
            for (TLAnswer answer : listAnswers) {
                System.out.println("> " + answer.answer);
                answerStrings.add(answer.answer);
            }

            System.out.println("Enter the term to delete:");
            String delTerm = scanner.nextLine().strip();
            if (!answerStrings.contains(delTerm)) {
                System.out.println("That term doesn't exist in the list...\ncancelling term removal.");
                return;
            }
            ps.setString(1, delTerm);
            ps.executeUpdate();
            System.out.println("Term successfully removed from " + currentStudyCat.studyCatName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void tlUpdatePrompt(StudyCat currentStudyCat, Scanner scanner) {
        try(Connection conn = JDBC.getConnection()) {
            int currentID = currentStudyCat.id;
            TermsList tl = (TermsList) currentStudyCat;
            String sql = "UPDATE termslistsprompts SET question = ? WHERE id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            System.out.println("~current prompt/question -> " + tl.prompt);
            System.out.println("Enter new UPDATED prompt/question for terms list [" + currentStudyCat.studyCatName + "]: ");
            
            String prompt = scanner.nextLine();
            ps.setString(1, prompt);
            ps.setInt(2, currentID);
            ps.executeUpdate();
            System.out.println("prompt successfully change to:\n" + prompt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
