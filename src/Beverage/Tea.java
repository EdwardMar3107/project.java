package Beverage;

public class Tea extends Beverage {

    public Tea(String name) {
        super(name);
    }

    @Override
    public double getCost() {
        final int price = 150;
        return price;
    }
}
