package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

import javax.naming.spi.DirStateFactory.Result;

public class DAL {
    
    public static ArrayDeque<TermsListsPrompt> showLists() {
        ArrayDeque<TermsListsPrompt> termsLists = new ArrayDeque();

        try(Connection conn = JDBC.getConnection()) {
            String x = "SELECT * FROM termsListsPrompts ORDER BY id;";
            PreparedStatement ps = conn.prepareStatement(x);

            ResultSet rs = ps.executeQuery();
            TermsListsPrompt tlp;
            while (rs.next()) {
                tlp = new TermsListsPrompt(rs.getInt("id"), rs.getString("list_name"), rs.getString("question"), rs.getString("item_type"));
                termsLists.add(tlp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return termsLists;
    }
}
