public class Projectile extends Entity {
    // Vector components
    public double accX;
    public double accY;
    public double velX;
    public double velY;
    public int damage;

    /**
     * Initializes the projectile with its position, hitbox dimensions, movement parameters, and vector components.
     *
     * @param x the initial x-coordinate of the projectile
     * @param y the initial y-coordinate of the projectile
     * @param hitboxWidth the width of the projectile's hitbox
     * @param hitboxHeight the height of the projectile's hitbox
     * @param maxSpeed the maximum speed the projectile can reach
     * @param accX the initial horizontal acceleration component of the projectile
     * @param accY the initial vertical acceleration component of the projectile
     * @param velX the initial horizontal velocity component of the projectile
     * @param velY the initial vertical velocity component of the projectile
     * */
    public Projectile(int x, int y, int hitboxWidth, int hitboxHeight, double maxSpeed, double accX, double accY,
                      double velX, double velY, String imagePath) {
        super(x, y, hitboxWidth, hitboxHeight, maxSpeed, imagePath);
        this.accX = accX;
        this.accY = accY;
        this.velX = velX;
        this.velY = velY;
        this.damage = 0;
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
        if (velMagnitude > this.maxSpeed) {
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
}
