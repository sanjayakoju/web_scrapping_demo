package org.corona;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

public class ExportToCSV {

    static void exportDataToCSV(String dbPath, String dbName, String regionParameter) {
        String selectQuery = "SELECT DISTINCT Region, Country, TotalCases, TotalTests, ActiveCases FROM "+dbName;
        if (!regionParameter.isEmpty()) {
            selectQuery += " WHERE Region = ?";
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
                if (!regionParameter.isEmpty()) {
                    statement.setString(1, regionParameter);
                }

                try (ResultSet resultSet = statement.executeQuery()) {
                    String csvFilePath = "export_" + regionParameter + "_" +
                            java.time.LocalDate.now().toString() + ".csv";
                    File file = new File(csvFilePath);

                    if(file.exists()) {
                        file.delete();
                    }

                    FileWriter writer = new FileWriter(csvFilePath);

                    writer.append("Region,Country,TotalCases,TotalTests,ActiveCases\n");

                    String headerFormat = "%-10s %-10s %-10s %-10s %-10s";
                    String dataFormat = "%-10s %-10s %-10s %-10s %-10s";

                    String header = String.format(headerFormat, "Region", "Country", "TotalCases", "TotalTests", "ActiveCases");
                    System.out.println(header);

                    while (resultSet.next()) {
                        String region = resultSet.getString("Region");
                        String country = resultSet.getString("Country");
                        String totalCases = resultSet.getString("TotalCases");
                        String totalTests = resultSet.getString("TotalTests");
                        String activeCases = resultSet.getString("ActiveCases");

                        writer.append(region).append(",")
                                .append(country).append(",")
                                .append(totalCases).append(",")
                                .append(totalTests).append(",")
                                .append(activeCases).append("\n");

                        String formattedData = String.format(dataFormat, region, country, totalCases, totalTests, activeCases);
                        System.out.println(formattedData);
                    }

                    writer.flush();
                    writer.close();

                    System.out.println("\nData exported to: " + csvFilePath);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
