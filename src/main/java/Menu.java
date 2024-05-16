import Country.*;
import com.google.protobuf.compiler.PluginProtos;
import db.WorldService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {



    WorldService worldService = new WorldService();

    myFileReader fileReader;

    // array lists for temporary lists that we use throughout menu
    ArrayList<Country> countryList = new ArrayList<Country>();
    ArrayList<City> cityList = new ArrayList<City>();


    public Menu() throws SQLException, FileNotFoundException {
        // fileReader activates when you instantiate Menu, so when you run program this will automatically import all countries from the countryListFile.txt under resources
        // fileReader = new myFileReader(worldService);
    }

    public void runMenu() throws SQLException, FileNotFoundException {
        // these are if you need to have placeholder for your scanner.nextString() / scanner.nextInt()
        String stringInput;
        int intInput;
        // only instantiate one scanner and use it throughout the menu
        Scanner scanner = new Scanner(System.in);
        // this will be our input placeholder for choice we make in main menu 1-8 in this case,
        //      if input is 0, then do-while loop will break and program will shut down.
        int input;

        do {
            System.out.println("1. Add country\n2. Get all countries\n3. Get cities by country name\n" +
                    "4: Delete country by name\n5. Update population of Country by name\n" +
                    "6. Enter Country name to edid its cities\n7. Add City\n" +
                    "8. Show All cities\n9. Import your .txt file to DB\n0. Exit\n");
            input = scanner.nextInt();
            scanner.nextLine();
            switch (input) {
            case 1:
                System.out.println("Write country name");
                stringInput = scanner.nextLine();
                System.out.println("Write the population");
                int popinput = scanner.nextInt();
                scanner.nextLine();
                worldService.addCountry(stringInput, popinput);
                break;
            case 2:
                countryList.clear();
                countryList = worldService.getAllCountries();
                System.out.println(countryList.size());
                for (Country country : countryList) {
                    System.out.println(country.toString());
                }
                break;
            case 3:
                System.out.println("Enter country name:\n");
                String nameInput = scanner.nextLine();
                cityList.clear();
                cityList = worldService.getCitiesByCountryName(nameInput);
                for (City city : cityList) {
                    System.out.println("v----------------------------v");
                    System.out.println(city.toString());
                    System.out.println("^----------------------------^");
                }
                break;
            case 4:
                System.out.println("Enter countries name to delete:\n");
                stringInput = scanner.nextLine();
                worldService.deleteCountryByName(stringInput);
                break;
            case 5:
                //  -- this can go into one method just for example? --
                // update population of country by name
                System.out.println("Enter country name to change population:\n");
                stringInput = scanner.nextLine();
                int id = worldService.getCountryIdByName(stringInput);
                if (id > 0) {
                    System.out.println("Enter the new population count:\n");
                    int newPopulation = scanner.nextInt();
                    // there is "\n" in the scanner-buffer so you need to eat it up with nextLine, so the next scan will work
                    scanner.nextLine();
                    worldService.updatePopulationOfCountry(newPopulation, id);
                } else {
                    System.out.println("No country found by that name!");
                }
                break;
            case 6:
                System.out.println("Enter Country for city list:");
                stringInput = scanner.nextLine();
                // populate our global list
                cityList.clear();
                cityList = worldService.getCitiesByCountryName(stringInput);

                if (cityList.isEmpty()) {
                    System.out.println("no cityList ");
                } else {
                    // to save and showcase the index of object being iterated through
                    int index;
                    //print out all cities that are under that country name
                    for (City city : cityList) {
                        index = cityList.indexOf(city);
                        System.out.println("-----------------------------------");
                        System.out.println("Index: " + index);
                        System.out.println(city.toString());
                        System.out.println("-----------------------------------");
                    }

                    System.out.println("What city do you want to edit? (enter Index nr:)");
                    int userInputIndex = scanner.nextInt();
                    scanner.nextLine();
                    City city = cityList.get(userInputIndex);
                    System.out.println(city.toString());

                }
                break;
            case 7:
                // add city, first check if the country exists
                System.out.println("Enter City´s country:\n");
                String stringInputCountryName = scanner.nextLine();

                int n = worldService.getCountryIdByName(stringInputCountryName);
                // n will be -1 if the getCountry method doesnt return any countries , so we check for that under in if/else statments
                System.out.println("here is the int n = " + n);
                if (n < 0) {
                    System.out.println("No country found with that name\n here are your options:");
                    countryList.clear();
                    countryList = worldService.getAllCountries();
                    for (Country country : countryList) {
                        System.out.println(country.getName());
                    }
                } else {
                   /* System.out.println("Enter your new city´s name:\n");
                    stringInput = scanner.nextLine();
                    System.out.println("Enter city´s population:");
                    intInput = scanner.nextInt();
                    scanner.nextLine();
                    */
                    //  addCity( cityName, population , countryName )
                    // worldService.addCity(stringInput, intInput, stringInputCountryName);     VERSION 1

                    // here we want the add City method to take more responsibility , so we send the countryList so we can print the entire country list if we write non existing countryName
                    //          then we send the worldSerivce object to addCity so it can perform ==> worldService.getAllCountries(); inside addCity method.
                    worldService.addCity(stringInputCountryName, countryList, worldService, scanner);
                }

                break;
            case 8:
                cityList.clear();
                cityList = worldService.getAllCities();
                System.out.println(cityList.size());
                for (City city: cityList) {
                    System.out.println(city.toString());
                }


                break;
            case 9:

                fileReader = new myFileReader(worldService);
                // this is another option to use the static FileRader2 class, and use its method to import the .txt file data into your database.

                String stringFilePath = "/Users/stefanspasenic/Downloads/WorldLectureDB/src/main/resources/countryListFile.txt";
                FileReader2.readFileAndAddCountries(worldService, stringFilePath);

                break;
            case 10:
                System.out.println("Enter the number from the menu!");
                break;
            case 0:
                System.out.println("Closing Program!");
                return;
            }

        }   while (input != 0);

        scanner.close();
    }
}
