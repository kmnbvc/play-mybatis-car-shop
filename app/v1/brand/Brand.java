package v1.brand;

import java.util.Objects;

public class Brand {

    private String name;
    private String country;

    public Brand() {
    }

    public Brand(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(name, brand.name) && Objects.equals(country, brand.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
