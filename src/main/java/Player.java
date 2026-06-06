import java.awt.*;

public class Player extends Entity {
    public final MoneyBag moneyBag;
    public Mountable mount;
    public Item crown;
    // Offset when facing left
    public int crownXOffsetL;
    public int crownYOffsetL;
    // Offset when facing right
    public int crownXOffsetR;
    public int crownYOffsetR;
    KeyHandler keyInput;

    /**
     * Initializes the player with crown anchored to the head and riding a default horse
     * @param keyHandler the key handler to control the player
     * @param gamePanel the game panel to render the player
     */
    public Player(KeyHandler keyHandler, GamePanel gamePanel, Mountable mount) {
        super(0, 0, 17, 23, 0, gamePanel);
        this.keyInput = keyHandler;
        this.gamePanel = gamePanel;
        this.mount = mount;
        crown = new Item(0, 0, 6, 2, 5, GameData.ID.CROWN_ID,
                gamePanel);
        mount.isMounted = true;
        mount.anchorsPassenger(this);  // Anchor the player to the mount's position
        moneyBag = new MoneyBag(15, x, y);
    }

    /**
     * Sets the offset values for the crown
     *
     * @param xOffsetL the horizontal offset for the crown when facing left
     * @param yOffsetL the vertical offset for the crown when facing left
     * @param xOffsetR the horizontal offset for the crown when facing right
     * @param yOffsetR the vertical offset for the crown when facing right
     * */
    public void setCrownOffset(int xOffsetL, int yOffsetL, int xOffsetR, int yOffsetR) {
        crownXOffsetL = xOffsetL;
        crownYOffsetL = yOffsetL;
        crownXOffsetR = xOffsetR;
        crownYOffsetR = yOffsetR;
    }

    /**
     * Anchors the crown to the player's head based on facing direction
     * */
    public void anchorsCrown() {
        if (isFacingLeft) {
            crown.x = x + crownXOffsetL;
            crown.y = y + crownYOffsetL;
        } else {
            crown.x = x + crownXOffsetR;
            crown.y = y + crownYOffsetR;
        }
    }

    /**
     * Swap the current mount with a new mount, updating the player's position to follow the new mount and ensuring the
     * mounting state is correctly updated for both the old and new mounts.
     *
     * @param newMount the new mount to switch to
     * */
    public void swapMount(Mountable newMount) {
        mount.isMounted = false;            // Dismount the current mount
        mount = newMount;                   // Switch to the new mount
        mount.isMounted = true;             // Mount the new mount
        newMount.anchorsPassenger(this);    // Update the player's position to follow the new mount
    }

    /**
     * When the player loses the crown, it creates a duplicate of the crown item to drop on the ground, resets the
     * player's reference to the crown, and returns the dropped crown item for further processing
     *
     * @return the Item instance representing the lost crown that can be added to the game world as a dropped item
     * */
    public Item loseCrown() {
        crown.hasPicked = false;
        Item lostCrown = crown.duplicate();  // Create a copy of the crown to drop on the ground
        crown = null;
        return lostCrown;
    }

    /**
     * Update the player's state based on key inputs while mounted, allowing the player to toss coins and move with the
     * mount and anchors the crown to the player's head.
     * */
    @Override
    public void update() {
        if (mount == null) {
            return;
        }
        // Update player's actions based on key inputs while mounted
        if (keyInput.downPressed) {
            Item tossedCoin = moneyBag.tossCoin();
            GamePanel.gameData.allItems.add(tossedCoin);
        }
        if (keyInput.leftPressed) {
            isFacingLeft = true;
            mount.isFacingLeft = true;
            mount.x -= (int) mount.maxSpeed;
            mount.anchorsPassenger(this);   // Update the player's position to follow the mount
            anchorsCrown();
        } else if (keyInput.rightPressed) {
            isFacingLeft = false;
            mount.isFacingLeft = false;
            mount.x += (int) mount.maxSpeed;
            mount.anchorsPassenger(this);
            anchorsCrown();
        }
    }
}