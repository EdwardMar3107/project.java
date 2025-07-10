package Animals;

public class Cat extends Animal {

    public Cat(String name) {
        super(name);
    }
    @Override
    public void makeNoise() {
        System.out.println(name + " мяукает");
    }

    @Override
    public void eat() {
        System.out.println(name + " кушает кошачий корм");
    }
    @Override
    public String getDescription() {
        return name + " - это кот!";
    }
}
