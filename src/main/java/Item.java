public class Item extends Entity {
    public boolean hasPicked;  // Whether the item is being picked up or on the ground
    private String name;

    public Item(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, double acceleration, String name) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, acceleration);
        this.hasPicked = false;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
