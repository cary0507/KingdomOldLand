public class Human extends Entity {
    public GameData.ID id;
    public Structure habitat;

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
                 String imagePath) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, imagePath);
        this.id = GameData.ID.FUGITIVE_ID;
        this.habitat = habitat;
    }


}
