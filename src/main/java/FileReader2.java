import db.WorldService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class FileReader2 {




    // Define a static method to read the file and add countries to the database
    public static void readFileAndAddCountries(WorldService worldService, String filePath) throws FileNotFoundException, SQLException {
        File file = new File(filePath);
        Scanner reader = new Scanner(file);

        while (reader.hasNext()) {
            String countryName = reader.nextLine();
            String countryPopulation = reader.nextLine();
            int countryPopulationInt = Integer.parseInt(countryPopulation);
            System.out.println("CountryName: " + countryName + "\nCountryPopString" + countryPopulation + "\nCountryPopInt: " + countryPopulationInt);

            worldService.addCountry(countryName, countryPopulationInt);
        }
    }

    public static void readFileAndAddCities(WorldService worldService, String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner reader = new Scanner(file);

        // and here you need to do the logic behind reading the file, if there are N number of lines for each city
        // then you need to have that many .nextLines() in one while iteration, where it gathers all of the lines in
        // respective Strings , those strings need to be parseInt() into int later on...

        // IF the file is split by ----- , then you need to create logic to read ------ and then go to next line that will be the 1st line of the new object
    }


}
