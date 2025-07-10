package Animals;

public abstract class Animal {

    protected String name;

    public Animal (String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void makeNoise();
    public abstract void eat();
    public abstract String getDescription();



}
