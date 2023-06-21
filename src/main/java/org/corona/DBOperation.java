package org.corona;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBOperation {

    static void storeDataInDatabase(String dbPath, String dbName, Elements tableRows) {
        // Insert only if the records is not exists in the table
        String insertQuery = "INSERT INTO "+dbName+" (Region, Country, TotalCases, TotalTests, ActiveCases) " +
        "SELECT ?, ?, ?, ?, ?"+
       " WHERE NOT EXISTS ("+
                "SELECT *" +
                "FROM "+dbName+ " "+
                "WHERE Region = ? AND Country = ?"+
                ")";


        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                for (Element row : tableRows) {

                    Elements columns = row.select("td");

                    String region = columns.get(15).text();
                    String country = columns.get(1).text();
                    String totalCases = columns.get(2).text();
                    String totalTests = columns.get(10).text();
                    String activeCases = columns.get(6).text();

//                    String region = row.select("td:nth-child(1)").text();
//                    String country = row.select("td:nth-child(2)").text();
//                    String totalCases = row.select("td:nth-child(3)").text();
//                    String totalTests = row.select("td:nth-child(11)").text();
//                    String activeCases = row.select("td:nth-child(6)").text();

                    statement.setString(1, region);
                    statement.setString(2, country);
                    statement.setString(3, totalCases);
                    statement.setString(4, totalTests);
                    statement.setString(5, activeCases);
                    statement.setString(6, region);
                    statement.setString(7, country);

                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
