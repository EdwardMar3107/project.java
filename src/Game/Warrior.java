package Game;

public class Warrior extends Player {

    private int strength;
    private Bow bow;

    public Warrior (String name, int level, int health, int strength, Bow bow) {
        super(name, level, health);
        this.strength = strength;
        this.bow = bow;

    }

    public Bow getBow() {
        return bow;
    }

    @Override
    public void printInfo() {
        System.out.println("Игрок: " + getName() + " | Уровень: " + getLevel() +
                " | Здоровье: " + getHealth() + " | Сила: " + strength);
    }
    }



