public class UpgradableStruct extends ContainerStruct {
    public int level;
    public final String[][] LEVEL_IMG_PATH;
    public int priceLvlUp;

    /**
     * Creates a new UpgradableStruct.
     *
     * @param x            initial x position in the world
     * @param y            initial y position in the world
     * @param levelImgPath array of image path strings for each upgrade level
     * @param id           identifier for the GameData entity type
     * @param relativePos  relative inventory/slot positions (passed to parent)
     * @param gamePanel    reference to the GamePanel the structure belongs to
     */
    public UpgradableStruct(int x, int y, String[][] levelImgPath, GameData.StructureID id, int[][] relativePos,
                            GamePanel gamePanel) {
        super(x, y, 0, 0, 0, id, relativePos, gamePanel);
        LEVEL_IMG_PATH = levelImgPath;
        level = 0;
        priceLvlUp = 1;
    }

    /**
     * Level up this structure to the next available level.
     *
     * @param newWidth  new width to apply after leveling up
     * @param newHeight new height to apply after leveling up
     */
    public void levelUp(int newWidth, int newHeight) {
        if (this.level >= LEVEL_IMG_PATH.length) {
            return;
        }
        level++;
        priceLvlUp *= 3;
        maxHP = 5 + 5 * (this.level - 1);
        curHP = this.maxHP;
        hitboxWidth = newWidth;
        hitboxHeight = newHeight;
    }
}
