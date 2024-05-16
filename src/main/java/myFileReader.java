import db.WorldService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class myFileReader {


    // String filePath;
    File file = new File("/Users/stefanspasenic/Downloads/WorldLectureDB/src/main/resources/countryListFile.txt");
   // File file;
    Scanner reader;


    public myFileReader(WorldService worldService) throws FileNotFoundException, SQLException {
        reader = new Scanner(file);
        while (reader.hasNext()) {
            String countryName = reader.nextLine();
            String countryPopulation = reader.nextLine();
            int countryPopulationInt = Integer.parseInt(countryPopulation);
            System.out.println("CountryName: " + countryName + "\nCountryPopString" + countryPopulation + "\nCountryPopInt: " + countryPopulationInt);

            worldService.addCountry(countryName, countryPopulationInt);
        }
    }

    // this is just if you want to send a specific File to file reader
    public myFileReader(File f) throws FileNotFoundException {
        file = f;
        reader = new Scanner(f);

        while (reader.hasNext()) {
            String countryName = reader.nextLine();
            String countryPopulation = reader.nextLine();
            int countryPopulationInt = Integer.parseInt(countryPopulation);
            System.out.println("CountryName: " + countryName + "\nCountryPopString" + countryPopulation + "\nCountryPopInt: " + countryPopulationInt);

        }
    }

}
