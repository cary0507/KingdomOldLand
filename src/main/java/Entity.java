import java.io.Serializable;

public class Entity implements Serializable {
    // Scalar magnitudes
    public double acceleration;
    public double speed = 0;
    public final double maxSpeed;  // This should be a read only variable
    public double facingDir;
    // Vector components
    private double accX;
    private double accY;
    private double velX;
    private double velY;
    // The pixels on the screen has to be a whole number, hence the int type.
    public int x;
    public int y;
    public final int hitboxWidth;
    public final int hitboxHeight;
    // Stores image file paths
    private String[] animePaths;  // How it looks when it is moving
    private String iconPath;  // How it looks when at stationary

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * @param x the initial x-coordinate of the entity
     * @param y the initial y-coordinate of the entity
     * @param hitboxWidth the width of the entity's hitbox
     * @param hitboxHeight the height of the entity's hitbox
     * @param maxSpeed the maximum speed the entity can reach
     * @param acceleration the acceleration magnitude of the entity
     * */
    public Entity(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, double acceleration) {
        this.x = x;
        this.y = y;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    /**
     * Calculate the acceleration vector components based on the facing direction and acceleration magnitude.
     * */
    public void updateAccVectors() {
        this.accX = Math.cos(this.facingDir) * this.acceleration;
        this.accY = Math.sin(this.facingDir) * this.acceleration;
    }

    /**
     * Calculate the velocity vector components based on the current velocity, acceleration, and facing direction.
     * */
    public void updateVelVectors() {
        double newVelX = this.velX + this.accX;
        double newVelY = this.velY + this.accY;
        double velMagnitude = Math.sqrt(newVelX * newVelX + newVelY * newVelY);
        if (velMagnitude > this.maxSpeed) {
            double maxVelX = Math.cos(this.facingDir) * this.maxSpeed;
            double maxVelY = Math.sin(this.facingDir) * this.maxSpeed;
            this.velX = maxVelX;
            this.velY = maxVelY;
        } else {
            this.velX = newVelX;
            this.velY = newVelY;
        }
    }

    /**
     * Update the position of the entity based on its velocity vector components.
     * */
    public void getDisplacement() {
        this.x += (int) this.velX;
        this.y += (int) this.velY;
    }
}
