public class Human extends Entity {
    public GameData.ID id;
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
     * @param hitboxWidth  the width of the entity's hitbox
     * @param hitboxHeight the height of the entity's hitbox
     * @param maxSpeed     the maximum speed the entity can reach
     */
    public Human(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, Structure habitat,
                 GamePanel gamePanel) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, gamePanel);
        this.id = GameData.ID.FUGITIVE_ID;
        this.habitat = habitat;
    }


}
