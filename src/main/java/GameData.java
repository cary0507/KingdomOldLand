import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {
    public static final double GRAVITY = 9.8;
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
    public Player player;
    public ArrayList<Item> allItems;
    public ArrayList<Human> allHumans;
    public ArrayList<Structure> allStructures;
    public ArrayList<Enemy> allEnemies;
    public ArrayList<Projectile> allProjectiles;
    public ArrayList<Mountable> allMounts;

    public GameData(KeyHandler keyHandler, GamePanel gamePanel) {
        Mountable originHorse = new Mountable(
                600, 280, 100, 100, 5.0, 100
        );
        player = new Player(keyHandler, gamePanel, originHorse);
    }


}
