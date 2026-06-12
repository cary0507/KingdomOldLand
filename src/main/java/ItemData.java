import java.io.Serializable;

public class ItemData implements Serializable {
    private final GameData.ItemID id;
    public String[] itemIconPathL;
    public String[] itemIconPathR;
    public String[] thrownImgPath;
    public int iconXOffsetL;
    public int iconYOffsetL;
    public int iconXOffsetR;
    public int iconYOffsetR;
    public boolean canPickUp;
    public int maxPickDelay;  // Delay until the item can be pick up in frames
    public int curPickFrame;
    public Entity owner;

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
        maxPickDelay = 2 * GamePanel.FPS;  // 2 seconds until the projectile can be pickup
        curPickFrame = 0;
    }

    /**
     * Constructor for items as a Projectile
     * */
    public ItemData(GameData.ItemID id, String[] thrownImgPath, boolean canPickUp) {
        this.id = id;
        this.thrownImgPath = thrownImgPath;
        this.canPickUp = canPickUp;
        maxPickDelay = 2 * GamePanel.FPS;  // 2 seconds until the projectile can be pickup
        curPickFrame = 0;
    }

    public GameData.ItemID getId() {
        return id;
    }
}
