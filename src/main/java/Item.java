/**
 * Represents an in-game item entity.
 *
 * <p>An Item is a type of {@link Projectile} that can exist on the ground or be
 * picked up by a player or another object (Some buildings can hold items). Each Item has a persistent identifier
 * ({@link #id}) used to distinguish item types and a flag ({@link #hasPicked})
 * that indicates whether the item is currently being carried/picked up.
 *
 * <p>Typical usage:
 * <pre>
 * Item coin = new Item(10, 20, 8, 8, 0.0, 0.0, GameData.ID.COIN);
 * if (!coin.hasPicked) { /* render on ground * / }
 * </pre>
 */
public class Item extends Projectile {
    public boolean hasPicked;  // Whether the item is being picked up or on the ground
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
                String imagePath) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, 0, 9.81, 0, 0, imagePath);
        this.hasPicked = false;
        this.id = id;
    }

    /**
     * Create a shallow copy/duplicate of this Item.
     *
     * <p>The returned {@code Item} shares the same primitive property values and
     * the same {@link #id}. Mutable object fields (if any are added later)
     * would still reference the same objects (shallow copy semantics).
     *
     * @return a new {@code Item} whose properties match this instance
     */
    public Item duplicate() {
        Item cloned = new Item(
                this.x, this.y, this.hitboxWidth, this.hitboxHeight, this.maxSpeed,
                this.id, this.imagePath
        );
        cloned.hasPicked = this.hasPicked;
        return cloned;
    }
}
