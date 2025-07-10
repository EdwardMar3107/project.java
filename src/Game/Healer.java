package Game;

public class Healer extends Player {

    protected int potion;

    public Healer(String name, int level, int health, int Potion) {
        super(name,level,health);
        this.potion = potion;
    }

    public void setPotion(int potion) {
        this.potion = potion;
    }

    public int getPotion() {
        return potion;
    }

    public void heal(Player ally) {
        if (potion > 0) {
            ally.heal(30);
            potion--;
            System.out.println(getName() + " лечит " + ally.getName() + " на 30 здоровья. Осталось зелья: " + potion);
        } else {
            System.out.println("Зелье закончилось");
        }

    }
}
