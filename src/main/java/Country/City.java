package Country;

public class City {

    int id;
    String countryName;
    String name;
    int population;


    public City(){
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", Country name=" + countryName +
                '}';
    }

    public City(String name, int population) {
        this.name = name;
        this.population = population;
    }

    public City(int id, String name, int population) {
        this.id = id;
        this.name = name;
        this.population = population;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPopulation() {
        return population;
    }
    public void setPopulation(int population){
        this.population = population;
    }
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
