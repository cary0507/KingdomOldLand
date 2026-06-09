import java.awt.*;

public class Player extends Entity {
    public final MoneyBag moneyBag;
    public Mountable mount;
    public PickedItem crown;
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
        crown = new PickedItem(0, 0, 6, 2, 5, GameData.ID.CROWN_ID,
                gamePanel);
        mount.isMounted = true;
        mount.anchorsPassenger(this);  // Anchor the player to the mount's position
        moneyBag = new MoneyBag(15, x, y);
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
    public PickedItem loseCrown() {

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
            PickedItem tossedCoin = moneyBag.tossCoin();
            gamePanel.gameData.allPickedItems.add(tossedCoin);
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

    @Override
    public void render(Graphics2D g2, Camera referenceCam) {
        referenceCam.focusOn(this);
        mount.render(g2, referenceCam);
        super.render(g2, referenceCam);
    }
}