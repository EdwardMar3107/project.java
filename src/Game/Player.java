package Game;

public class Player {

    private String name;
    private int level;
    private int health;

    public Player (String name, int level, int health) {
        setName(name);
        setLevel(level);
        setHealth(health);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        if (level >= 1) {
            this.level = level;
        } else {
            System.out.println("Ошибка! Уровень не может быть меньше 1!");
        }
    }

    public void setHealth(int health) {
        if (health < 0) {
            System.out.println("Ошибка! Здоровье не может быть меньше 0!");
        } else if (health > 1000) {
            System.out.println("Ошибка! Здоровье не может превышать 1000!");
        } else {
            this.health = health;
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public void printInfo() {
        System.out.println("Игрок: " + name + " | Уровень: " + level + " | Здоровье: " + health);
    }

    public void takeDamage (int damage) {
        if (damage < 0) {
            System.out.println("Ошибка! Урон не может быть меньше 0!");
            return;
        }
        health -= damage;
        if (health < 0) health = 0;
    }

    public void heal (int amount) {
        if (amount < 0) {
            System.out.println("Ошибка ! Восстановление не может быть отрицательным!");
            return;
        }
        health += amount;
        if (health > 1000) {
            health = 1000;
        }
    }

}
