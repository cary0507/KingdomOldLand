public class Enemy extends Entity {
    public GameData.ID id;
    public int damage;
    public long dmgCooldown;  // Time in milliseconds between attacks

    public Enemy(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, String imagePath, int damage,
                 GameData.ID id, long dmgCooldown) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, imagePath);
        this.id = id;
        this.damage = damage;
    }
}
