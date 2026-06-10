public class Human extends Entity {
    public GameData.JobID id;
    public Structure habitat;
    public int numCoins;
    public int maxCoins;
    public PickedItem equipping;

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * Humans are spawned as unemployed fugitives
     *
     * @param x            the initial x-coordinate of the entity
     * @param y            the initial y-coordinate of the entity
     * @param rawHitboxWidth  the width in tiles of the entity's hitbox
     * @param rawHitboxHeight the height in tiles of the entity's hitbox
     * @param maxSpeed     the maximum speed the entity can reach
     */
    public Human(int x, int y, int rawHitboxWidth, int rawHitboxHeight, double maxSpeed, Structure habitat,
                 GamePanel gamePanel) {
        super(x, y, rawHitboxWidth, rawHitboxHeight, maxSpeed, gamePanel);
        this.id = GameData.JobID.FUGITIVE;
        this.habitat = habitat;
    }


}
