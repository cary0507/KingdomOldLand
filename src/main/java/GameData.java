import java.io.Serializable;

public class GameData implements Serializable {
    public static final double GRAVITY = 9.8;
    public static final String COIN_IMG_PATH = "Fix later";
    // List all IDs
    public enum ID {
        // Item's ID
        CROWN_ID,
        COIN_ID,
        BOW_ID,
        HAMMER_ID,
        // Human's job ID
        FUGITIVE_ID,
        VILLAGER_ID,
        HUNTER_ID,
        BUILDER_ID,
        BANKER_ID,
        // Enemy's ID
        GREED_ID,
        BREEDER_ID,
        // Structure's ID
        HEADQUARTER_ID,
        TREE_ID,
        GRASS_ID,
        TEMPLE_ID,
        WALL_ID,
        ARCHERY_TOWER_ID,
        PORTAL_ID,
        CAMP_ID,
        HORSE_CORRAL_ID,
        BOW_SHELF_ID,
        HAMMER_SHELF_ID
    }
    // Objects
    MoneyBag moneyBag;
    Player player;
    Item[] allItems;
    Human[] allHumans;
    Structure[] allStructures;
}
