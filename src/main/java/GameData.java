import java.io.*;
import java.util.ArrayList;

public class GameData implements Serializable {
    @Serial
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
    public Player player;
    public ArrayList<Item> allItems;
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
        allItems = new ArrayList<>();
        allHumans = new ArrayList<>();
        allStructures = new ArrayList<>();
        allEnemies = new ArrayList<>();
        allProjectiles = new ArrayList<>();
        allMounts = new ArrayList<>();
        // Setup default horse
        Mountable originHorse = new Mountable(
                (int) (GamePanel.PANEL_WIDTH / 2),
                GamePanel.HORIZON, 43, 29, 5.0, 100, gamePanel
        );
        originHorse.y -= originHorse.hitboxHeight;
        originHorse.setImagesFromPaths(brownHorseImgL, brownHorseImgR);
        allMounts.add(originHorse);
        // Setup player
        player = new Player(keyHandler, gamePanel, originHorse);
        player.setImagesFromPaths(playerImgL, playerImgR);
        player.crown.setImagesFromPaths(crownImgL, crownImgR);
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
}
