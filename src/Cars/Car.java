package Cars;

public class Car {

    private Engine engine;
    private String name;
    private Wheel wheel;

    public Car(String name, Engine engine, Wheel wheel) {
        this.name = name;
        this.engine = engine;
        this.wheel = wheel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public Engine getEngine() {
        return engine;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public String getInfo() {
        return "Машина: " + getName() + " | мощность двигателя: " + getEngine().getHorsepower() + " | размер колес: " + getWheel().getSize() + " | тип двигателя: " + getEngine().getType() + " | сезон колес: " + getWheel().getWheelType();
    }
}
