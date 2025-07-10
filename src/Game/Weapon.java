package Game;

public abstract class Weapon {

    protected int damage;
    protected int ammo;


    public Weapon(int damage, int ammo) {
        this.damage = damage;
        this.ammo = ammo;

    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    

    public int getAmmo() {
        return ammo;
    }

    public int getDamage() {
        return damage;
    }
    
    public void shot (Player enemy, Player self) throws EmptyAmmoException {
        if (ammo < 0) {
            throw new EmptyAmmoException("Патроны закончились");
        }
        System.out.println("Игрок: " + self.getName() + " Наносит урона на: " + damage + " единиц " + enemy.getName());
        enemy.takeDamage(damage);
        ammo--;
    }

}
