public class Enemy extends Entity {
    public int hp;
    public int damage;
    public int dmgCooldown;  // Time in milliseconds between attacks
    public int curCooldown;

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     *
     * @param x            the initial x-coordinate of the entity
     * @param y            the initial y-coordinate of the entity
     * @param rawHitboxWidth  the width in tiles of the entity's hitbox
     * @param rawHitboxHeight the height in tiles of the entity's hitbox
     * @param maxSpeed     the maximum speed the entity can reach
     * @param gamePanel
     *
     */
    public Enemy(int x, int y, int rawHitboxWidth, int rawHitboxHeight, double maxSpeed, GamePanel gamePanel) {
        super(x, y, rawHitboxWidth, rawHitboxHeight, maxSpeed, gamePanel);
    }

    /**
     * Uses a seperate method to set the base stats of the enemy, allowing for more flexible enemy creation
     *
     * @param hp the health points of the enemy
     * @param damage the damage the enemy can inflict on the player
     * @param dmgCooldown the cooldown time in milliseconds between the enemy's attacks
     * */
    public void setBaseStats(int hp, int damage, int dmgCooldown) {
        this.hp = hp;
        this.damage = damage;
        this.dmgCooldown = dmgCooldown;
        this.curCooldown = 0;
    }
}
