package Animals;

public class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeNoise() {
        System.out.println(name + " гавкает");
    }

    @Override
    public void eat() {
        System.out.println(name + " кушает собачий корм");
    }
    @Override
    public String getDescription() {
        return name + " - это собака!";
    }
}
