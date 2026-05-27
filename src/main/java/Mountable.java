public class Mountable extends Entity {
    private boolean isMounted;  // Whether the mount is being ridden or on the ground

    public Mountable(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, double acceleration) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, acceleration);
        this.isMounted = false;
    }

    public boolean isMounted() {
        return this.isMounted;
    }

    public void releaseMount() {
        this.isMounted = false;
    }
}
