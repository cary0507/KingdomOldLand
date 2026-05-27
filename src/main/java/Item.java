public class Item extends Entity {
    private boolean hasPicked;  // Whether the item is being picked up or on the ground

    public Item(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, double acceleration) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, acceleration);
        this.hasPicked = false;
    }

    public boolean hasPicked() {
        return this.hasPicked;
    }

    public void releaseItem() {
        this.hasPicked = false;
    }
}
