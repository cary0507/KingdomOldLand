public class Mountable extends Entity {
    public final int MAX_STAMINA;
    public int stamina;
    public double curSpeed;
    // Offset values when facing left
    private int passengerXOffsetL;
    private int passengerYOffsetL;
    // Offset values when not facing left (Facing right)
    private int passengerXOffsetR;
    private int passengerYOffsetR;
    // Sets the passenger
    public Entity passenger;

    /**
     * Initializes the mountable entity with its position, hitbox dimensions, movement parameters, and stamina.
     *
     * @param x the initial x-coordinate of the mountable entity
     * @param y the initial y-coordinate of the mountable entity
     * @param maxSpeed the maximum speed the mountable entity can reach
     * @param maxStamina the maximum stamina of the mountable entity, determines the speed of the mount
     * */
    public Mountable(int x, int y, double maxSpeed, int maxStamina, GamePanel gamePanel) {
        super(x, y, maxSpeed, gamePanel);
        this.MAX_STAMINA = maxStamina;
        this.stamina = maxStamina;
        curSpeed = maxSpeed;
    }

    /**
     * Set the offset values for the passenger when the mount is facing left and right. These offsets determine where
     * the passenger will be positioned relative to the mount's origin (x, y)
     *
     * @param xOffsetL the horizontal offset for the passenger when the mount is facing left
     * @param yOffsetL the vertical offset for the passenger when the mount is facing left
     * @param xOffsetR the horizontal offset for the passenger when the mount is facing right
     * @param yOffsetR the vertical offset for the passenger when the mount is facing right
     * */
    public void setPassengerOffset(int xOffsetL, int yOffsetL, int xOffsetR, int yOffsetR) {
        passengerXOffsetL = xOffsetL * GamePanel.SCALE_PIXEL;
        passengerYOffsetL = yOffsetL * GamePanel.SCALE_PIXEL;
        passengerXOffsetR = xOffsetR * GamePanel.SCALE_PIXEL;
        passengerYOffsetR = yOffsetR * GamePanel.SCALE_PIXEL;
    }

    /**
     * Anchors the passenger to the mount, positioning them relative to the mount's origin based on its facing direction
     *
     */
    public void anchorsPassenger() {
        if (passenger == null) {
            return;
        }
        if (isFacingLeft) {
            passenger.x = x + passengerXOffsetL;
            passenger.y = y - passengerYOffsetL;
        } else {
            passenger.x = x + passengerXOffsetR;
            passenger.y = y - passengerYOffsetR;
        }
    }

    @Override
    public void update() {
        super.update();
        if (passenger != null) {
            anchorsPassenger();
        }
    }
}
