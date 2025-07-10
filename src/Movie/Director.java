package Movie;

import java.util.Objects;

public class Director {
    String name;
    int birthYear;

    public Director(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Director)) return false;
        Director other = (Director) obj;
        return getName().equals(other.getName()) && getBirthYear() == other.getBirthYear();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthYear());
    }
}
