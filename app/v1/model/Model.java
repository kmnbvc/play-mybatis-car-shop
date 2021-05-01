package v1.model;

import java.time.Year;
import java.util.Objects;

public class Model {

    private String name;
    private Year introduced;
    private Year closed;

    public Model() {
    }

    public Model(String name, Year introduced, Year closed) {
        this.name = name;
        this.introduced = introduced;
        this.closed = closed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Year getIntroduced() {
        return introduced;
    }

    public void setIntroduced(Year introduced) {
        this.introduced = introduced;
    }

    public Year getClosed() {
        return closed;
    }

    public void setClosed(Year closed) {
        this.closed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(name, model.name) && Objects.equals(introduced, model.introduced) && Objects.equals(closed, model.closed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, introduced, closed);
    }
}
