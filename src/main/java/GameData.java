import java.io.Serializable;

public class GameData implements Serializable {
    // List all IDs
    public static enum ID {
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
        // Structure's ID
        HEADQUARTER_ID,
        TREE_ID,
        TEMPLE_ID,
        WALL_ID,
        ARCHERY_TOWER_ID,
        PORTAL_ID
    }
    // Object
    Player player;
}
