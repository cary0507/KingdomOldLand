public class UpgradableStruct extends ContainerStruct {
    public int level;
    public int maxLevel;

    /**
     * Creates a new UpgradableStruct.
     *
     * @param x            initial x position in the world
     * @param y            initial y position in the world
     * @param id           identifier for the GameData entity type
     * @param relativePos  relative inventory/slot positions (passed to parent)
     * @param gamePanel    reference to the GamePanel the structure belongs to
     */
    public UpgradableStruct(int x, int y, GameData.StructureID id, int[][] relativePos, GamePanel gamePanel) {
        super(x, y, 0, id, relativePos, gamePanel);
        level = 0;
        maxLevel = 0;
    }

    /**
     * Level up this structure to the next available level.
     * This time, instead of storing animations, left/rightImages store image of each level
     */
    public void levelUp(int newHP) {
        if (this.level >= leftImages.length || this.level >= rightImages.length) {
            return;
        }
        level++;
        maxHP = newHP;
        curHP = this.maxHP;
        this.hitboxWidth = leftImages[level].getWidth() * GamePanel.SCALE_PIXEL;
        this.hitboxHeight = leftImages[level].getHeight() * GamePanel.SCALE_PIXEL;
    }

    /**
     * Does not have animations anymore
     * */
    @Override
    public void update() {
        imgIndex = level;
        maxLevel = leftImages.length - 1;
    }
}
