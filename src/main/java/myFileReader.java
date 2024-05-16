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
        // reader.hasNext() will just check if there is next line,it wont move the reader to that line... so its just checking to see if there is next.

        while (reader.hasNext()) {
            // if there is nextLine in reader, it will execute 2x .nextLine() making it read from the file 2 lines and then place those two lines into an object that is country.
              // then you can place those countries by using imported worldService to use addCountry method, for populating your DB.
            String countryName = reader.nextLine();
            String countryPopulation = reader.nextLine();
            int countryPopulationInt = Integer.parseInt(countryPopulation);
            System.out.println("CountryName: " + countryName + "\nCountryPopString" + countryPopulation + "\nCountryPopInt: " + countryPopulationInt);

            worldService.addCountry(countryName, countryPopulationInt);
        }
    }

    // this is just if you want to send a specific File to file reader
                // but this is just example ... what can be done
    public myFileReader(File f,WorldService worldService) throws FileNotFoundException, SQLException {
        file = f;
        reader = new Scanner(f);

        while (reader.hasNext()) {
            String countryName = reader.nextLine();
            String countryPopulation = reader.nextLine();
            int countryPopulationInt = Integer.parseInt(countryPopulation);
            System.out.println("CountryName: " + countryName + "\nCountryPopString" + countryPopulation + "\nCountryPopInt: " + countryPopulationInt);
            worldService.addCountry(countryName, countryPopulationInt);
        }
    }

}
