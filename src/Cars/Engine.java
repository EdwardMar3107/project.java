package Cars;

public class Engine {

    private String type;
    private int horsepower;

    public Engine(String type, int horsepower) {
        this.type = type;
        this.horsepower = horsepower;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHorsepower(int horsepower) {
        this.horsepower = horsepower;
    }

    public String getType() {
        return type;
    }

    public int getHorsepower() {
        return horsepower;
    }


}

