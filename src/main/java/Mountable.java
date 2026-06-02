public class Mountable extends Entity {
    public boolean isMounted;  // Whether the mount is being ridden or on the ground
    public final int MAX_STAMINA;
    public int stamina;
    public boolean isFrightened;  // Whether the mount is currently frightened and cannot move

    public Mountable(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, int maxStamina,
                     String imagePath) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, imagePath);
        this.isMounted = false;
        this.MAX_STAMINA = maxStamina;
        this.stamina = maxStamina;
        this.isFrightened = false;
    }
}
