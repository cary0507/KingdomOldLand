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
     * @param acceleration the acceleration magnitude of the entity
     *
     */
    public Human(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, double acceleration,
                 Structure habitat) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, acceleration);
        this.id = GameData.ID.FUGITIVE_ID;
        this.habitat = habitat;
    }


}
