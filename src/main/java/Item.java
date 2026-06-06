public class Item extends Projectile {
    public boolean hasPicked;  // Whether the item is being picked up or on the ground
    public boolean isOutOfBound;  // Determines if the Item will fall into the river and disappear
    public final GameData.ID id;

    /**
     * Create a new Item instance.
     *
     * @param x horizontal position of the item's origin
     * @param y vertical position of the item's origin
     * @param hitboxWidth width of the item's collision hitbox
     * @param hitboxHeight height of the item's collision hitbox
     * @param maxSpeed maximum movement speed for the item
     * @param id identifier representing the item's type
     */
    public Item(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, GameData.ID id,
                GamePanel gamePanel) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, 0, GameData.GRAVITY, 0, 0, gamePanel);
        hasPicked = false;
        isOutOfBound = false;
        this.id = id;
    }

    /**
     * Creates a duplicate of this Item instance, copying all properties including position, hitbox dimensions,
     * movement parameters, and the picked-up state.
     * */
    public Item duplicate() {
        Item cloned = new Item(
                this.x, this.y, this.hitboxWidth, this.hitboxHeight, this.maxSpeed,
                this.id, this.gamePanel
        );
        cloned.isOutOfBound = this.isOutOfBound;
        cloned.hasPicked = this.hasPicked;
        return cloned;
    }
}
