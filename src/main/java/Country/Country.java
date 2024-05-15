package Country;

public class Country {
    int id;
    String name;
    int population;

    public Country(){
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                '}';
    }

    public Country(String name, int population) {
        this.name = name;
        this.population = population;
    }

    public Country(int id, String name, int population) {
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
}
