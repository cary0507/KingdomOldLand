import java.awt.*;

public class Player extends Entity {
    public final MoneyBag moneyBag;
    public Mountable mount;
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
        holdingItem = new PickedItem(new ItemData(
                GameData.ItemID.CROWN, 3, -2, 8, -2,
                GameData.crownImgL, GameData.crownImgR)
        );
        mount.isMounted = true;
        mount.anchorsPassenger(this);  // Anchor the player to the mount's position
        moneyBag = new MoneyBag(15, x, y, gamePanel);
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
            Projectile tossedCoin = moneyBag.tossCoin();
            gamePanel.gameData.allProjectiles.add(tossedCoin);
        }
        if (keyInput.leftPressed) {
            isFacingLeft = true;
            mount.isFacingLeft = true;
            mount.x -= (int) mount.maxSpeed;
            mount.anchorsPassenger(this);   // Update the player's position to follow the mount
        } else if (keyInput.rightPressed) {
            isFacingLeft = false;
            mount.isFacingLeft = false;
            mount.x += (int) mount.maxSpeed;
            mount.anchorsPassenger(this);
        }
    }

    @Override
    public void render(Graphics2D g2, Camera referenceCam) {
        referenceCam.focusOn(this);
        mount.render(g2, referenceCam);
        super.render(g2, referenceCam);
        super.renderItem(g2, referenceCam);
    }
}