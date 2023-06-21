package org.corona;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnection {

    static void createDatabase(String dbPath, String dbName) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS "+dbName+ "(" +
                    "Id INTEGER Primary Key AUTOINCREMENT,"+
                    "Region TEXT, " +
                    "Country TEXT, " +
                    "TotalCases TEXT, " +
                    "TotalTests TEXT, " +
                    "ActiveCases TEXT)";
            try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
