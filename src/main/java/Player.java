public class Player extends Entity {
    // Offsets to nchors the crown the player's head
    public final int HEAD_OFFSET_X = 15;
    public final int HEAD_OFFSET_Y = -15;
    private final MoneyBag moneyBag;
    private Mountable mount;
    private Item crown;
    KeyHandler keyInput;
    GamePanel gamePanel;

    /**
     * Initializes the player with crown anchored to the head and riding a default horse
     * @param keyHandler the key handler to control the player
     * @param gamePanel the game panel to render the player
     */
    public Player(KeyHandler keyHandler, GamePanel gamePanel, Mountable mount) {
        super(0, 0, 10, 10, 0);
        this.keyInput = keyHandler;
        this.gamePanel = gamePanel;
        this.mount = mount;
        crown = new Item(0, 0, 10, 10, 5, GameData.ID.CROWN_ID);
        mount.isMounted = true;
        anchorsMount(mount);  // Anchor the player to the mount's position
        moneyBag = new MoneyBag(15, ImagePath.MONEY_BAG);
    }

    public void update() {
        if (mount == null) {
            return;
        }
        // Update player's actions based on key inputs while mounted
        if (keyInput.downPressed) {
            Item tossedCoin = moneyBag.tossCoin();
            tossedCoin.x = x;
            tossedCoin.y = y;
            tossedCoin.velX = 0;
            tossedCoin.velY = -5;  // Toss the coin upwards
            tossedCoin.accX = 0;
            tossedCoin.accY = GameData.GRAVITY;  // Apply gravity to the tossed
            GamePanel.gameData.allItems.add(tossedCoin);
        }
        if (keyInput.leftPressed) {
            mount.x += (int) mount.maxSpeed;
            anchorsMount(mount);  // Update the player's position to follow the mount
            return;  // Prevent movement in the opposite direction if both keys are pressed
        }
        if (keyInput.rightPressed) {
            mount.x -= (int) mount.maxSpeed;
            anchorsMount(mount);  // Update the player's position to follow the mount
        }
    }

    /**
     * Fix the player's position to follow the mount, and the crown's position to follow the player's head.
     * */
    public void anchorsMount(Mountable targetMount) {
        // Update the player's position to follow the mount
        x = (int) mount.x + targetMount.hitboxWidth / 2 - hitboxWidth / 2;
        y = (int) mount.x + targetMount.hitboxHeight / 2 + hitboxHeight / 2;
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

    public Item loseCrown() {
        crown.hasPicked = false;
        Item lostCrown = crown.duplicate();  // Create a copy of the crown to drop on the ground
        crown = null;
        return lostCrown;
    }
}