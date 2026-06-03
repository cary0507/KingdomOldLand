public class Enemy extends Entity {
    public int hp;
    public int damage;
    public int dmgCooldown;  // Time in milliseconds between attacks
    public int curCooldown;

    public Enemy(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, String imagePath, int damage, int dmgCooldown) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, imagePath);
        this.damage = damage;
        this.dmgCooldown = dmgCooldown;
        this.curCooldown = 0;
    }
}
