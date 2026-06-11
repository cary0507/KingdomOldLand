public class UpgradableStruct extends ContainerStruct {
    public int level;
    public String[][] levelImgPath;
    public int priceLvlUp;

    /**
     * Creates a new UpgradableStruct.
     *
     * @param x            initial x position in the world
     * @param y            initial y position in the world
     * @param id           identifier for the GameData entity type
     * @param relativePos  relative inventory/slot positions (passed to parent)
     * @param gamePanel    reference to the GamePanel the structure belongs to
     */
    public UpgradableStruct(int x, int y, GameData.StructureID id, int[][] relativePos,
                            GamePanel gamePanel) {
        super(x, y, 0, id, relativePos, gamePanel);
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
        if (this.level >= levelImgPath.length) {
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
