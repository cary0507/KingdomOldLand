public class Mountable extends Entity {
    public boolean isMounted;  // Whether the mount is being ridden or on the ground

    public Mountable(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, double acceleration) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, acceleration);
        this.isMounted = false;
    }
}
