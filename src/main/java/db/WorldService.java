package db;

import Country.*;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class WorldService {

    private final MysqlDataSource worldDS;


        // CONSTRUCTOR
        //              - that instantly configures the settings for connection to the database, we take that data from PROPS. to get the the data under host/port/db_name/uname/pwd ==>
        public WorldService() throws SQLException {
            worldDS = new MysqlDataSource();
            worldDS.setServerName(PropertiesProvider.PROPS.getProperty("host"));
            worldDS.setPortNumber(Integer.parseInt(PropertiesProvider.PROPS.getProperty("port")));
            worldDS.setDatabaseName(PropertiesProvider.PROPS.getProperty("db_name"));
            worldDS.setUser(PropertiesProvider.PROPS.getProperty("uname"));
            worldDS.setPassword(PropertiesProvider.PROPS.getProperty("pwd"));
        }

        public MysqlDataSource getWorldDS() {
            return worldDS;
        }

        // ADD COUNTRY

        public void addCountry(String name, int pop) throws SQLException {
            try(Connection con = worldDS.getConnection()){

                con.setAutoCommit(false);
                String sql = "INSERT INTO country(countryName, population) VALUES(?,?)";

                PreparedStatement prepStatement = con.prepareStatement(sql);
                prepStatement.setString(1, name);
                prepStatement.setInt(2, pop);
                int rowsAffected = prepStatement.executeUpdate();

                if (rowsAffected > 0) {
                    con.commit();
                }
                else{
                    // this will empty the connection buffer and delete the data if there is no return value (or 0 ) from prep.executeUpdate();
                    con.rollback();
                }

            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }

        // ADD CITY
        public void addCity(String country_Name, ArrayList<Country> countryList, WorldService worldService, Scanner scan) {

            Scanner scanner = scan;

            try(Connection con = worldDS.getConnection()){
                con.setAutoCommit(false);
                String query1 = "INSERT INTO city(name, population, countryId) VALUES(?,?,?)";

                // countryID will be whatever the id is in the database of that countr_Name. If nothing returns from method, countryId = -1;
                int countryId = getCountryIdByName(country_Name);
                if (countryId < 0) {
                    System.out.println("No country found with that name\n here are your options:");
                    countryList.clear();
                    countryList = worldService.getAllCountries();
                    for (Country country : countryList) {
                        System.out.println(country.getName());
                    }
                } else {
                    PreparedStatement preparedStatement = con.prepareStatement(query1);
                    System.out.println("Enter your new city´s name:\n");
                    String cityName = scanner.nextLine();
                    System.out.println("Enter city´s population:");
                    int cityPop = scanner.nextInt();
                    scanner.nextLine();
                    preparedStatement.setString(1, cityName);
                    preparedStatement.setInt(2, cityPop);
                    preparedStatement.setInt(3, countryId );

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        con.commit();
                    }
                    else{
                        // this will empty the connection buffer and delete the data if there is no return value (or 0 ) from prep.executeUpdate();
                        con.rollback();
                    }
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }

        }

        public Country getCountryByName(Connection connection, String country_Name) throws SQLException {

            Country country = null;
            connection.setAutoCommit(false);

            String query = "SELECT * FROM country WHERE countryName=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country_Name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String countryName = resultSet.getString("countryName");
                int pop = resultSet.getInt("population");
                country = new Country(id, countryName, pop);
            }

            return country;
        }

        // GET ALL COUNTRIES
        public ArrayList<Country> getAllCountries() throws SQLException {
            ArrayList<Country> tempList = new ArrayList<>();
            try (Connection conn = worldDS.getConnection()) {
                String query = "SELECT * FROM country";
                PreparedStatement prep = conn.prepareStatement(query);
                ResultSet resultSet = prep.executeQuery();
                while (resultSet.next()) {
                    // country.id   means that it is in country table and column id
                    int id = resultSet.getInt("country.id");
                    String name = resultSet.getString("countryName");
                    int pop = resultSet.getInt("population");
                    Country country = new Country();
                    country.setName(name);
                    country.setId(id);
                    country.setPopulation(pop);
                    tempList.add(country);
                }
            }
            return tempList;
        }


        // GET CITIES BY COUNTRY NAME
        public ArrayList<City> getCitiesByCountryName(String countryName){

            int id = getCountryIdByName(countryName);
            ArrayList<City> cityList;

            if (id < 0) {
                throw new RuntimeException("Could not find country with name" + countryName);
            }else {

                cityList = getCitiesByCountryId(id);

            }


            return cityList;



        }

        // returns Id of Country by name  (this is called first)

        // GET COUNTRY ID BY NAME --
        public int getCountryIdByName(String countryName) {

            // this is for having id returned -1 , wich will fail the checks if no id is returned from this method (getCountryIdByName)
            int id = -1;

            try (Connection con = worldDS.getConnection())
            {
                String query = "SELECT id FROM country WHERE countryName=?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, countryName);
                ResultSet resultSet = preparedStatement.executeQuery();
                // getInt from column "Id"
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return id;
        }


        // GET CITIES BY COUNTRY ID --
        public ArrayList<City> getCitiesByCountryId(int countryId) {

            ArrayList<City> cityList = new ArrayList<>();

            try (Connection con = worldDS.getConnection())
            {
                String query = "SELECT countryName, city.name, city.population, city.id FROM city  JOIN country ON countryId = country.id WHERE countryId=?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1, countryId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    City city = new City();
                    city.setId(resultSet.getInt("id"));
                    city.setName(resultSet.getString("name"));
                    city.setPopulation(resultSet.getInt("population"));
                    city.setCountryName(resultSet.getString("countryName"));

                    cityList.add(city);
                    System.out.println("added city to list (city:)"+city.getName());
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return cityList;
        }


        // DELETE CITY BY COUNTRY ID

        public void deleteCityByCountryId(int id, Connection con) {

            try {
                con.setAutoCommit(false);

                String query = "DELETE FROM city WHERE countryId=?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setInt(1,id);

                int resutl = preparedStatement.executeUpdate();
                if (resutl != 1) {
                    System.out.println("failed to delete the city");
                } else {
                    con.commit();
                    System.out.println("deleted city with countryId: " + id);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        // - DELETE COUNTRY BY NAME
        public void deleteCountryByName(String countryName) {
            try (Connection connection = worldDS.getConnection()){
                connection.setAutoCommit(false);
                // get country id so we can use it to delete cities by countryId
                int id = getCountryIdByName(countryName);
                deleteCityByCountryId(id, connection);

                String query = "DELETE FROM country WHERE countryName=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, countryName);

                int result = preparedStatement.executeUpdate();
                if (result != 1) {
                    System.out.println("failed to delete the country");
                } else {
                    connection.commit();
                    System.out.println("deleted country by country name : " + countryName);
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // UPDATES --

        public void updatePopulationOfCountry(int pop, int country_ID) {

            try(Connection connection = worldDS.getConnection()){
                connection.setAutoCommit(false);

                    String query = "UPDATE country " +
                            "SET population=? " +
                            "WHERE id=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1,pop);
                    preparedStatement.setInt(2,country_ID);
                    preparedStatement.executeUpdate();

                    connection.commit();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }



    }
