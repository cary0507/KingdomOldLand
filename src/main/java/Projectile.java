public class Projectile extends Entity {
    ItemData data;
    // Vector components
    public double accX;
    public double accY;
    public double velX;
    public double velY;
    public int damage;
    public boolean isOutOfBound;

    /**
     * Initializes the projectile with its position, hitbox dimensions, movement parameters, and vector components.
     *
     * @param x            the initial x-coordinate of the projectile
     * @param y            the initial y-coordinate of the projectile
     * @param rawHitboxWidth  the width in tiles of the projectile's hitbox
     * @param rawHitboxHeight the height in tiles of the projectile's hitbox
     * @param maxSpeed     the maximum speed the projectile can reach
     */
    public Projectile(int x, int y, int rawHitboxWidth, int rawHitboxHeight, double maxSpeed, GamePanel gamePanel) {
        super(x, y, rawHitboxWidth, rawHitboxHeight, maxSpeed, gamePanel);
    }

    /**
     * Sets the ItemData
     * */
    public void setData(ItemData data) {
        this.data = data;
    }

    /**
     * Set the values for the motion of the projectile
     * */
    public void setMotionValues(double velX, double velY, double accX, double accY, int damage, boolean isOutOfBound) {
        this.accX = accX;
        this.accY = accY;
        this.velX = velX;
        this.velY = velY;
        this.damage = damage;
        this.isOutOfBound = isOutOfBound;
    }

    /**
     * Calculate the acceleration vector components based on the facing direction and acceleration magnitude.
     * */
    public void updateAccVectors(double facingDir, double acceleration) {
        this.accX = Math.cos(facingDir) * acceleration;
        this.accY = Math.sin(facingDir) * acceleration;
    }

    /**
     * Calculate the velocity vector components based on the current velocity, acceleration, and facing direction.
     * */
    public void updateVelVectors() {
        double newVelX = this.velX + this.accX;
        double newVelY = this.velY + this.accY;
        double facingDir = Math.atan2(velY, velX);  // Gets facing direction from the current velocity vector
        double velMagnitude = Math.sqrt(newVelX * newVelX + newVelY * newVelY);
        if (velMagnitude > this.maxSpeed) {  // Check for speed limit
            double maxVelX = Math.cos(facingDir) * this.maxSpeed;
            double maxVelY = Math.sin(facingDir) * this.maxSpeed;
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

    public PickedItem getPicked(ItemData data) {
        PickedItem itemForm = new PickedItem(data);
        return itemForm;
    }
}
