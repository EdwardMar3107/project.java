package Game;

public class Mage extends Player {

    private int mana;
    private final Staff staff;

    public Mage (String name, int level, int health, int mana, Staff staff) {
        super(name, level, health);
        this.staff = staff;
        this.mana = mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public Staff getStaff() {
        return staff;
    }


    @Override
    public void printInfo() {
        System.out.println("Игрок: " + getName() + " | Уровень: " + getLevel() +
                " | Здоровье: " + getHealth() + " | Мана: " + mana);
    }
}
