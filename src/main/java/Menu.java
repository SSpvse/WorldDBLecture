import Country.*;
import db.WorldService;

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
        fileReader = new myFileReader(worldService);
    }

    public void runMenu() throws SQLException {
        String stringInput;
        int intInput;

        Scanner scanner = new Scanner(System.in);
        int input;
            do {
            System.out.println("1. Add country\n2. Get all countries\n3. Get cities by country name\n" +
                                "4: Delete country by name\n5. Update population of Country by name\n" +
                                "6. Enter Country name to edid its cities\n7. Add City\n0. Exit\n");
            input = scanner.nextInt();
            scanner.nextLine();
            switch (input){
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

                    System.out.println("Enter the new population count:\n");
                    int newPopulation = scanner.nextInt();
                    // there is "\n" in the scanner-buffer so you need to eat it up with nextLine, so the next scan will work
                    scanner.nextLine();
                    worldService.updatePopulationOfCountry(newPopulation, id);
                    break;
                case 6:
                    System.out.println("Enter Country for city list:");
                    stringInput = scanner.nextLine();
                    // populate our global list
                    cityList.clear();
                    cityList = worldService.getCitiesByCountryName(stringInput);

                    if (cityList.isEmpty()) {
                        System.out.println("no cityList ");
                    }
                    else {
                        // to save and showcase the index of object being iterated through
                        int index;
                        //print out all cities that are under that country name
                        for (City city : cityList){
                            index = cityList.indexOf(city);
                            System.out.println("-----------------------------------");
                            System.out.println("Nr: "+index);
                            System.out.println(city.toString());
                            System.out.println("-----------------------------------");
                        }

                        System.out.println("What city do you want to edit? (enter Nr:)");
                        int userInputIndex = scanner.nextInt();
                        scanner.nextLine();
                        City city = cityList.get(userInputIndex);
                        System.out.println(city.toString());

                    }
                    break;
                case 7:
                    System.out.println("Enter City´s country:\n");
                    String stringInputCountryName = scanner.nextLine();

                    int n = worldService.getCountryIdByName(stringInputCountryName);
                    // n will be -1 if the getCountry method doesnt return any countries , so we check for that under in if/else statments
                    System.out.println("here is the n int :" + n);
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
                        worldService.addCity( stringInputCountryName, countryList, worldService, scanner);
                    }

                    break;
                case 0:
                    System.out.println("Closing Program!");
                return;
            }
        }   while (input >= 0 && input < 7);

    }
}
