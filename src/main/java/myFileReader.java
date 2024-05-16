import db.WorldService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class myFileReader {


    // String filePath;


    // Here the filepath is hardcoded inside the file, but you can also have placeholder for String (the filepath) as parameter, so you can send a filepath as argument to myFileReader and it will
    // do same thing when instantiated... the only important thing is that the File file gets the path, and then
    // Scanner reader = new Scanner (file) , this feeds the Scanner with the file and then you can use methods upon the read text from the file.txt

    File file = new File("/Users/stefanspasenic/Downloads/WorldLectureDB/src/main/resources/countryListFile.txt");

    // this you can use if you want to fill in the placeholder for File through constructors parameter:
    //  ==> public myFileReader(WorldService worldService, File f){
    //                 this.file = f;
    //                 reader = new Scanner(file);    }   ((( the method is bellow as expl aswell)))



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
