package v1.position;

import v1.brand.Brand;
import v1.model.Model;

import java.time.Year;
import java.util.Objects;
import java.util.UUID;

public class Position {

    private UUID id;
    private Brand brand;
    private Model model;
    private Year yearOfManufacture;
    private int run;
    private int price;

    public Position() {
    }

    public Position(UUID id, Brand brand, Model model, Year yearOfManufacture, int run, int price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.yearOfManufacture = yearOfManufacture;
        this.run = run;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Year getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(Year yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return run == position.run && price == position.price && Objects.equals(id, position.id) && Objects.equals(brand, position.brand) && Objects.equals(model, position.model) && Objects.equals(yearOfManufacture, position.yearOfManufacture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, yearOfManufacture, run, price);
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", brand=" + brand +
                ", model=" + model +
                ", yearOfManufacture=" + yearOfManufacture +
                ", run=" + run +
                ", price=" + price +
                '}';
    }
}
