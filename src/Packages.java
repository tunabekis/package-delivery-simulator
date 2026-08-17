/**
 * A parcel waiting for or in transit to a city.
 */
public class Packages implements Transportable {

    private String name;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Packages{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
