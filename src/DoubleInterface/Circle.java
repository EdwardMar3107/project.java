package DoubleInterface;

public class Circle implements Drawable, Scalable {

    private String name;
    private double radius;

    public Circle(String name, double radius) {
        this.name = name;
        this.radius = radius;
    }

    public String getName() {
        return name;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public void draw() {
        System.out.println("Рисует " + getName() + " с радиусом " + getRadius());
    }

    @Override
    public void scale(double factor) {
        radius *= factor;
    }
}
