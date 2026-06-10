public class Structure extends Entity {
    public int maxHP;
    public int curHP;
    public GameData.StructureID id;
    public GameData.ItemID[] blockedID;

    /**
     * Initializes the structure with its position, dimensions, hit points, and image path.
     *
     * @param x the x-coordinate of the structure
     * @param y the y-coordinate of the structure
     * @param rawHitboxWidth the width in tiles of the structure
     * @param rawHitboxHeight the height in tiles of the structure
     * @param maxHP the maximum hit points of the structure
     * @param id the id of the object
     * @param gamePanel the screen that the object appear on
     */
    public Structure(int x, int y, int rawHitboxWidth, int rawHitboxHeight, int maxHP, GameData.StructureID id,
                     GamePanel gamePanel) {
        super(x, y, rawHitboxWidth, rawHitboxHeight, 0, gamePanel);
        this.gamePanel = gamePanel;
        this.maxHP = maxHP;
        this.curHP = maxHP;
        this.id = id;
    }

    /**
     * Determines Entity with which IDs cannot go through
     * */
    public void setBlockedID(GameData.ItemID[] blockedID) {
        this.blockedID = blockedID;
    }

    /**
     * Drops one coin at the center of the structure when it is destroyed
     * @return a Projectile object representing the dropped coin, positioned at the center of the structure
     * */
    public Projectile rewardCoin() {
        int centerX = (int) (x + hitboxWidth / 2);
        Projectile coin = new Projectile(
                centerX, y, 20, 20, 6, gamePanel
        );
        return coin;
    }
}
