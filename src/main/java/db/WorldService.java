package db;

import Country.*;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WorldService {

    private final MysqlDataSource worldDS;


    public WorldService(){
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

        public void addCountry(String name, int pop) throws SQLException {
            try(Connection con = worldDS.getConnection()){
                con.setAutoCommit(false);
                String sql = "INSERT INTO country(countryName, population) VALUES(?,?)";
                PreparedStatement prep = con.prepareStatement(sql);
                prep.setString(1, name);
                prep.setInt(2, pop);
                int rowsAffected = prep.executeUpdate();

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

        // this method is called from the menu

    public ArrayList<City> getCitiesByCountryName(String countryName){

        int id = getCountryIdByName(countryName);
        ArrayList<City> cityList = new ArrayList<>();

        if (id < 0) {
            throw new RuntimeException("Could not find country with name" + countryName);
        }else {

            cityList = getCitiesByCountryId(id);

        }


        return cityList;



    }

    // returns Id of Country by name  (this is called first)
    public int getCountryIdByName(String countryName) {

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
    // returns city list by countryID  ( this is returned after the method above gives back country ID
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
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cityList;
    }





}
