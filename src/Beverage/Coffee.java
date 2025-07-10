package Beverage;

public class Coffee extends Beverage {

    public Coffee (String name) {
        super(name);
    }

    @Override
    public double getCost() {
        return 200;
    }
}
