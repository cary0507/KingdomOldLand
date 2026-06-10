public class ItemData {
    private final GameData.ItemID id;
    public String[] itemIconPathL;
    public String[] itemIconPathR;
    public String[] thrownImgPath;
    public int iconXOffsetL;
    public int iconYOffsetL;
    public int iconXOffsetR;
    public int iconYOffsetR;
    public boolean canPickUp;

    /**
     * Constructor for PickedItem
     * */
    public ItemData(GameData.ItemID id, int iconXOffsetL, int iconYOffsetL, int iconXOffsetR, int iconYOffsetR,
                    String[] itemIconPathL, String[] itemIconPathR) {
        this.id = id;
        this.iconXOffsetL = iconXOffsetL * GamePanel.SCALE_PIXEL;
        this.iconYOffsetL = iconYOffsetL * GamePanel.SCALE_PIXEL;
        this.iconXOffsetR = iconXOffsetR * GamePanel.SCALE_PIXEL;
        this.iconYOffsetR = iconYOffsetR * GamePanel.SCALE_PIXEL;
        this.itemIconPathL = itemIconPathL;
        this.itemIconPathR = itemIconPathR;
    }

    /**
     * Constructor for items as a Projectile
     * */
    public ItemData(GameData.ItemID id, String[] thrownImgPath, boolean canPickUp) {
        this.id = id;
        this.thrownImgPath = thrownImgPath;
        this.canPickUp = canPickUp;
    }

    public GameData.ItemID getId() {
        return id;
    }
}
