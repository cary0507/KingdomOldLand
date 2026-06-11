public class Human extends Entity {
    public GameData.JobID id;
    public Structure habitat;
    public final int MAX_HP = 2;
    public int hp;
    public PickedItem equipping;

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * Humans are spawned as unemployed fugitives
     *
     * @param x            the initial x-coordinate of the entity
     * @param y            the initial y-coordinate of the entity
     * @param maxSpeed     the maximum speed the entity can reach
     */
    public Human(int x, int y, double maxSpeed, Structure habitat,
                 GamePanel gamePanel) {
        super(x, y, maxSpeed, gamePanel);
        this.id = GameData.JobID.FUGITIVE;
        this.habitat = habitat;
        hp = 1;
    }
}
