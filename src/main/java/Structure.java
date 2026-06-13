public class Structure extends Entity {
    public int maxHP;
    public int curHP;
    public final GameData.StructureID id;

    /**
     * Initializes the structure with its position, dimensions, hit points, and image path.
     *
     * @param x the x-coordinate of the structure
     * @param y the y-coordinate of the structure
     * @param maxHP the maximum hit points of the structure
     * @param id the id of the object
     * @param gamePanel the screen that the object appear on
     */
    public Structure(int x, int y, int maxHP, GameData.StructureID id, GamePanel gamePanel) {
        super(x, y, 0, gamePanel);
        this.gamePanel = gamePanel;
        this.maxHP = maxHP;
        this.curHP = maxHP;
        this.id = id;
    }

    /**
     * Drops one coin at the center of the structure when it is destroyed
     * @return a Projectile object representing the dropped coin, positioned at the center of the structure
     * */
    public Projectile rewardCoin() {
        int centerX = (int) (x + hitboxWidth / 2);
        ItemData coinData = new ItemData(GameData.ItemID.COIN, GameData.coinImg, GameData.coinImg, true);
        Projectile coin = new Projectile(centerX, y, 6, gamePanel,  coinData);
        coin.setImagesFromPaths(coin.data.thrownImgPathL, coin.data.thrownImgPathR);
        return coin;
    }
}
