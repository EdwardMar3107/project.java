package Animals;

public class Bear extends Animal {

    public Bear(String name) {
        super(name);
    }
    @Override
    public void makeNoise() {
        System.out.println(name + " рычит");
    }

    @Override
    public void eat() {
        System.out.println(name + " кушает мёд и ягоды");
    }
    @Override
    public String getDescription() {
        return name + " - это медведь!";
    }
}
