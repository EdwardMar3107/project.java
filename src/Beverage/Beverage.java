package Beverage;

public abstract class Beverage {
    private String name;

    public Beverage(String name) {
        setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double getCost();

    public String getInfo() {
        return "Напиток: " + name + ". Цена: " + getCost();
    }

}
