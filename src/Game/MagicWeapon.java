package Game;

public abstract class MagicWeapon {

    protected int magicDamage;


    public MagicWeapon (int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public void setMagicDamage(int magicDamage) {
        this.magicDamage = magicDamage;
    }


    public int getMagicDamage() {
        return magicDamage;
    }

    public void magicShot (Player enemy, Mage self) throws EmptyManaException {
        if (self.getMana() < 0) {
            throw new EmptyManaException("Мана закончилась");
        }
        System.out.println("Игрок: " + self.getName() + " Наносит урона на: " + magicDamage + " единиц " + enemy.getName());
        enemy.takeDamage(magicDamage);
        self.setMana(self.getMana() - 1);
    }
}





