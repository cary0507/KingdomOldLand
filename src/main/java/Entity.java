import java.io.Serializable;

public class Entity implements Serializable {
    public final double maxSpeed;
    // The pixels on the screen has to be a whole number, hence the int type.
    public int x;
    public int y;
    public final int hitboxWidth;
    public final int hitboxHeight;
    // Stores image file paths
    public String imagePath;  // How it looks when at stationary

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * @param x the initial x-coordinate of the entity
     * @param y the initial y-coordinate of the entity
     * @param hitboxWidth the width of the entity's hitbox
     * @param hitboxHeight the height of the entity's hitbox
     * @param maxSpeed the maximum speed the entity can reach
     * */
    public Entity(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, String imagePath) {
        this.x = x;
        this.y = y;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.maxSpeed = maxSpeed;
        this.imagePath = imagePath;
    }
}
