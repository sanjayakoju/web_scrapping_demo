package org.corona;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class CovidDataScraper {

    public static void main(String[] args) {
        String url = "https://www.worldometers.info/coronavirus/";

        // Step 1: Extract data from the website
        try {
            Document document = Jsoup.connect(url).get();
            Elements tableRows = document.select("#main_table_countries_today tbody tr");

            // Step 2: Create and store data in SQLite database
            String dbPath = "Data.db";
            String currentDate = java.time.LocalDate.now().toString();
            currentDate = currentDate.replace('-', '_');
            String dbName = "Data_" + currentDate;
            DBConnection.createDatabase(dbPath, dbName);
            DBOperation.storeDataInDatabase(dbPath, dbName, tableRows);

            System.out.println("Enter the region");
            Scanner scan = new Scanner(System.in);
            String region = scan.nextLine();
            Optional<String> regionOptional = Optional.ofNullable(region);

            // Step 3: Export data to CSV file
            String regionParameter = regionOptional.orElse("Europe"); // Enter the region parameter here
            ExportToCSV.exportDataToCSV(dbPath, dbName, regionParameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

