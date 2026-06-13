public class Enemy extends Entity {
    public int hp;
    public int damage;
    public int dmgCooldown;  // Time in frames between attacks
    public int curCooldown;

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     *
     * @param x the initial x-coordinate of the entity
     * @param y the initial y-coordinate of the entity
     * @param maxSpeed the maximum speed the entity can reach
     * @param gamePanel the main screen
     *
     */
    public Enemy(int x, int y, double maxSpeed, GamePanel gamePanel) {
        super(x, y, maxSpeed, gamePanel);
        setImagesFromPaths(GameData.ENEMY_IMG_L, GameData.ENEMY_IMG_R);
    }

    /**
     * Uses a separate method to set the base stats of the enemy, allowing for more flexible enemy creation
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

    /**
     * Tracks the player
     * */
    public void update(Entity target) {
        if (curCooldown > 0) {
            curCooldown--;
        }
        if (x <= GameData.getCenterX(target)) {
            isFacingLeft = false;
            x += (int) MAX_SPEED;
        } else {
            isFacingLeft = true;
            x -= (int) MAX_SPEED;
        }
    }
}
