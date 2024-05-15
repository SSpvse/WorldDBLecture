import Country.*;
import db.WorldService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {


    WorldService ws = new WorldService();

    ArrayList<Country> countryList = new ArrayList<Country>();
    ArrayList<City> cityList = new ArrayList<City>();
    public Menu() {

    }

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int input;
            do {
            System.out.println("1. Add country\n2. Get all countries\n3. Get cities by country name\n4. Exit");
            input = scanner.nextInt();
            scanner.nextLine();
            switch (input){
                case 1:
                    System.out.println("Write country name");
                    String cuntinput = scanner.nextLine();
                    System.out.println("Write the population");
                    int popinput = scanner.nextInt();
                    scanner.nextLine();
                    ws.addCountry(cuntinput, popinput);
                    break;
                case 2:
                    countryList.clear();
                    countryList = ws.getAllCountries();
                    for (Country country : countryList) {
                        System.out.println(country.toString());
                    }
                    break;
                case 3:
                    System.out.println("Enter country name:\n");
                    String nameInput = scanner.nextLine();
                    cityList.clear();
                    cityList = ws.getCitiesByCountryName(nameInput);
                    for (City city : cityList) {
                        System.out.println("----------------------------");
                        System.out.println(city.toString());
                        System.out.println("----------------------------");

                    }
                    break;
                case 4:
                    // input = 5;
                    return;
            }
        }   while (input > 0 && input < 5);

    }
}
