package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.HashSet;

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

    public static HashSet<TLAnswer> getListAnswers (int list_id) {
        HashSet<TLAnswer> listAnswers = new HashSet<>();

        try(Connection conn = JDBC.getConnection()) {
            String sql = "SELECT * FROM termslistanswers WHERE question_id = ? ORDER BY id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, list_id);

            ResultSet rs = ps.executeQuery();
            TLAnswer tlAnswer;
            while(rs.next()) {
                tlAnswer = new TLAnswer(rs.getString("answer"));
                listAnswers.add(tlAnswer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listAnswers;
    }
}
