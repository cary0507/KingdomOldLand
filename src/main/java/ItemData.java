public class ItemData {
    private final GameData.ID id;
    public String itemIconPath;
    public String thrownImgPath;
    public int iconXOffsetL;
    public int iconYOffsetL;
    public int iconXOffsetR;
    public int iconYOffsetR;

    public ItemData(GameData.ID id) {
        this.id = id;
    }

    public GameData.ID getId() {
        return id;
    }
}
