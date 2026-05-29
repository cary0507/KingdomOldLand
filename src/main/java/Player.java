public class Player extends Entity {
    // Anchors the crown the player's head
    private final int HEAD_OFFSET_X = 5;
    private final int HEAD_OFFSET_Y = -5;
    private Mountable mount;
    private Item crown;

    /**
     * Initializes the player with crown anchored to the head
     * @param mount the player's mount
     * @param playerHeight the player's height (for anchoring the crown)
     * @param playerWidth the player's width (for anchoring the crown)
     * */
    public Player(Mountable mount, int playerHeight, int playerWidth, String imagePath) {
        super(0, 0, playerWidth, playerHeight, 0, 0);
        this.mount = mount;
        // Anchor the player to the center of the mount, and the crown to the player's head
        this.x = (int) mount.x + mount.getHitboxWidth() / 2 - playerWidth / 2;
        this.y = (int) mount.x + mount.getHitboxHeight() / 2 + playerHeight / 2;
        int crownX = x + HEAD_OFFSET_X;
        int crownY = y + HEAD_OFFSET_Y;
        this.crown = new Item(crownX, crownY, 10, 10, 5, 0, "crown");
        this.crown.hasPicked = true;  // Without this line the player will lose the crown immediately after game starts
    }

    /**
     * Fix the player's position to follow the mount, and the crown's position to follow the player's head.
     * */
    public void anchorsMount(Mountable targetMount) {
        // Update the player's position to follow the mount
        x = (int) mount.x + targetMount.getHitboxWidth() / 2 - getHitboxWidth() / 2;
        y = (int) mount.x + targetMount.getHitboxHeight() / 2 + getHitboxHeight() / 2;
        // Update the crown's position to follow the player's head
        int crownX = x + HEAD_OFFSET_X;
        int crownY = y + HEAD_OFFSET_Y;
        crown.x = crownX;
        crown.y = crownY;
    }

    public void swapMount(Mountable newMount) {
        mount.isMounted = false;  // Dismount the current mount
        mount = newMount;         // Switch to the new mount
        mount.isMounted = true;   // Mount the new mount
        anchorsMount(newMount);   // Update the player's position to follow the new mount
    }
}