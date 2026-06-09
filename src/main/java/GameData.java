import java.io.*;
import java.util.ArrayList;

public class GameData implements Serializable {
    // serialVersionUID for Serializable compatibility (no @Serial annotation to keep Java 8 compatibility)
    private static final long serialVersionUID = 1L;
    public static final double GRAVITY = 9.8;
    // Image paths
    public static String[] playerImgL = {
            "/raw images/king left.png",
    };
    public static String[] playerImgR = {
            "/raw images/king right.png",
    };
    public static String[] crownImgL = {
            "/raw images/crown left.png"
    };
    public static String[] crownImgR = {
            "/raw images/crown right.png"
    };
    public static String[] brownHorseImgL = {
            "/raw images/brown horse left.png",
    };
    public static String[] brownHorseImgR = {
            "/raw images/brown horse right.png",
    };
    // Object IDs
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
    public GamePanel gamePanel;
    public Camera camera;
    public Player player;
    public ArrayList<PickedItem> allPickedItems;
    public ArrayList<Human> allHumans;
    public ArrayList<Structure> allStructures;
    public ArrayList<Enemy> allEnemies;
    public ArrayList<Projectile> allProjectiles;
    public ArrayList<Mountable> allMounts;

    /**
     * Initialize all game object
     *
     * @param keyHandler the key input
     * @param gamePanel the GamePanel object of main screen
     * */
    public GameData(KeyHandler keyHandler, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        allPickedItems = new ArrayList<>();
        allHumans = new ArrayList<>();
        allStructures = new ArrayList<>();
        allEnemies = new ArrayList<>();
        allProjectiles = new ArrayList<>();
        allMounts = new ArrayList<>();
        // Setup camera
        camera = new Camera(gamePanel, 0, 0, 400, 100);
        // Setup default horse
        Mountable originHorse = new Mountable(
                (int) (gamePanel.PANEL_WIDTH / 2),
                gamePanel.HORIZON, 43, 29, 5.0, 100, gamePanel
        );
        originHorse.y -= originHorse.hitboxHeight;
        originHorse.setImagesFromPaths(brownHorseImgL, brownHorseImgR);
        originHorse.setPassengerOffset(14, 7, 12, 7);
        allMounts.add(originHorse);
        // Setup player
        player = new Player(keyHandler, gamePanel, originHorse);
        player.setImagesFromPaths(playerImgL, playerImgR);
    }

    /**
     * Outputs the serialized objects to a specified file path
     *
     * @param filePath the path to save the game data file
     * */
    public void saveGame(String filePath) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filePath);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this);
        objOut.close();
    }

    /**
     * Loads the game data from a specified file path and updates the current game state accordingly
     *
     * @param filePath the path to load the game data file from
     * */
    public Object loadGame(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream objIn = new ObjectInputStream(fileIn);
        Object loadedData = objIn.readObject();
        objIn.close();
        return loadedData;
    }

    /**
     * Check if
     * */
    public static boolean isInside(int left1, int top1, int right1, int bottom1,
                                   int left2, int top2, int right2, int bottom2) {
        if (right1 >= left2 && left1 <= right2) {
            if (bottom1 >= top2 && top1 <= bottom2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInside(Entity obj1, Entity obj2) {
        if (obj1 == obj2) {  // Same object
            return true;
        }
        int right1 = obj1.x + obj1.hitboxHeight;
        int right2 = obj2.x + obj2.hitboxHeight;
        int bottom1 = obj1.y + obj1.hitboxHeight;
        int bottom2 = obj2.y + obj2.hitboxHeight;
        return isInside(obj1.x, obj1.y, right1, bottom1, obj2.x, obj2.y, right2, bottom2);
    }
}
