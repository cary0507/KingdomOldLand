public class Projectile extends Entity {
    ItemData data;
    // Vector components
    public double accX;
    public double accY;
    public double velX;
    public double velY;
    public int damage;
    public boolean isOutOfBound;  // When set to true, the projectile will fall through the water and disappear

    /**
     * Initializes the projectile with its position, hitbox dimensions, movement parameters, and vector components.
     *
     * @param x            the initial x-coordinate of the projectile
     * @param y            the initial y-coordinate of the projectile
     * @param maxSpeed     the maximum speed the projectile can reach
     */
    public Projectile(int x, int y, double maxSpeed, GamePanel gamePanel, ItemData data) {
        super(x, y, maxSpeed, gamePanel);
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
     * Calculate the velocity vector components based on the facing direction and acceleration magnitude.
     * */
    public void setVelFromDir(double facingDir, double velocity) {
        this.velX = Math.cos(facingDir) * velocity;
        this.velY = Math.sin(facingDir) * velocity;
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
            // Break into components
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

    @Override
    public void update() {
        super.update();
        if (this.y <= GamePanel.HORIZON - this.hitboxHeight) {
            updateVelVectors();
            getDisplacement();
        } else {
            this.y = GamePanel.HORIZON - this.hitboxHeight;
        }
    }
}
