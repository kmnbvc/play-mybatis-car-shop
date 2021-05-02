package v1.model;

import java.time.Year;
import java.util.Objects;

public class Model {

    private String name;
    private Year yearManufactureBegan;
    private Year yearManufactureEnded;

    public Model() {
    }

    public Model(String name, Year yearManufactureBegan, Year yearManufactureEnded) {
        this.name = name;
        this.yearManufactureBegan = yearManufactureBegan;
        this.yearManufactureEnded = yearManufactureEnded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Year getYearManufactureBegan() {
        return yearManufactureBegan;
    }

    public void setYearManufactureBegan(Year yearManufactureBegan) {
        this.yearManufactureBegan = yearManufactureBegan;
    }

    public Year getYearManufactureEnded() {
        return yearManufactureEnded;
    }

    public void setYearManufactureEnded(Year yearManufactureEnded) {
        this.yearManufactureEnded = yearManufactureEnded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(name, model.name) && Objects.equals(yearManufactureBegan, model.yearManufactureBegan) && Objects.equals(yearManufactureEnded, model.yearManufactureEnded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, yearManufactureBegan, yearManufactureEnded);
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", yearManufactureBegan=" + yearManufactureBegan +
                ", yearManufactureEnded=" + yearManufactureEnded +
                '}';
    }
}
