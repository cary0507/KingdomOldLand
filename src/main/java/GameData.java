import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class GameData implements Serializable {
    // serialVersionUID for Serializable compatibility (no @Serial annotation to keep Java 8 compatibility)
    private static final long serialVersionUID = 1L;
    public static final double GRAVITY = 9.8;
    public static final double UNIVERSAL_TOP_SPEED = 6.0;
    // Image paths
    public static String[] playerImgL = {
            "/raw images/Player/king left.png",
    };
    public static String[] playerImgR = {
            "/raw images/Player/king right.png",
    };
    public static String[] crownImgL = {
            "/raw images/Crown/crown left.png"
    };
    public static String[] crownImgR = {
            "/raw images/Crown/crown right.png"
    };
    public static String[] brownHorseImgL = {
            "/raw images/Mountable/brown horse left.png",
    };
    public static String[] brownHorseImgR = {
            "/raw images/Mountable/brown horse right.png",
    };
    public static String[] chunksImg = {
            "/raw images/Chunk/bare dirt.png",
            "/raw images/Chunk/bare dirt.png",
            "/raw images/Chunk/Plain.png"
    };
    public static String[] coinSlotImg = {
            "/raw images/Coin/Slot/Empty.png",
            "/raw images/Coin/Slot/Filled.png"
    };
    public static String[] coinImg = {
            "/raw images/Coin/Thrown/coin1.png"
    };
    public static String[] wallImgL = {

    };
    // Object IDs
    public enum ItemID {
        CROWN,
        COIN,
        BOW,
        HAMMER,
    }
    public enum JobID {
        FUGITIVE,
        VILLAGER,
        HUNTER,
        BUILDER,
        BANKER
    }
    public enum ChunkID {
        SPAWN_CHUNK,
        DIRT,
        PLAIN
    }
    public enum StructureID {
        HEADQUARTER,
        TREE,
        GRASS,
        TEMPLE,
        WALL,
        ARCHERY_TOWER,
        PORTAL,
        CAMP,
        HORSE_CORRAL,
        BOW_SHELF,
        HAMMER_SHELF
    }
    // Game objects
    public GamePanel gamePanel;
    public Camera camera;
    public Player player;
    public ArrayList<PickedItem> allPickedItems;
    public ArrayList<Human> allHumans;
    public ArrayList<Chunk> allChunks;
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
        allChunks = new ArrayList<>();
        allStructures = new ArrayList<>();
        allEnemies = new ArrayList<>();
        allProjectiles = new ArrayList<>();
        allMounts = new ArrayList<>();
        // Setup camera
        camera = new Camera(gamePanel, 0, 0, 400, 100);
        // Set up the map and player with default mount
        int[] map = getLandCode(2, 2);
        initLand(map);
        // Setup player
        player = new Player(keyHandler, gamePanel, allMounts.get(0));
        player.setImagesFromPaths(playerImgL, playerImgR);
    }

    public void setUpHorse(int spawnX) {
        // Setup default horse
        Mountable originHorse = new Mountable(
                spawnX,
                GamePanel.HORIZON, 43, 29, 5.0, 100, gamePanel
        );
        originHorse.y -= originHorse.hitboxHeight;
        originHorse.setImagesFromPaths(brownHorseImgL, brownHorseImgR);
        originHorse.setPassengerOffset(14, 7, 12, 7);
        allMounts.add(originHorse);
    }

    /**
     * Randomize an array of numerical code representing the types of chunks
     * Number 0 is always the spawn chunk at the center of map
     *
     * @param landRadius the number of chunks from left/right side of the spawn chunk
     * @param numChunkOptions the total number of variations of chunks other than the spawn chunk (Always >= 1)
     * @return the codes stored in an int array
     * */
    public int[] getLandCode(int landRadius, int numChunkOptions) {
        int totalChunks = landRadius * 2 + 1;
        int[] landCode = new int[totalChunks];
        int mid = (int) ((totalChunks - 1) / 2);  // Explicit is better than implicit
        for (int i = 0; i < landCode.length; i++) {
            if (i != mid) {
                int rand = (int) (Math.random() * (numChunkOptions) + 1);  // Randomize the land code
                landCode[i] = rand;
            } else {
                landCode[i] = 0;
            }
        }
        return landCode;
    }

    /**
     * Generates structures based on land code
     * */
    public void initLand(int[] landCode) {
        int curChunkX = 0;  // Acts like a cursor
        for (int code : landCode) {
            // Set up the background chunk
            ChunkID chunkID = ChunkID.values()[code];
            // Creates the chunk
            Chunk curChunk = new Chunk(
                    curChunkX, GamePanel.HORIZON - 2 * GamePanel.SCALE_PIXEL, chunkID, chunksImg[code]
            );
            curChunkX += curChunk.hitboxWidth;  // Moves to the next Chunk's start
            allChunks.add(curChunk);  // Adds chunk to the game
            // Parse different codes
            if (code == 0) {  // Spawn chunk is guaranteed to have specific structures
                int spawnX = (int) (curChunkX - 2 * curChunk.hitboxWidth / 3);
                setUpHorse(spawnX);
            }
        }
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

    // Helper methods
    /**
     * Check if two rectangles are colliding
     * 
     * @param left1 the left x value of the first rectangle
     * @param top1 the top y value of the first rectangle
     * @param right1 the right x value of the first rectangle
     * @param bottom1 the bottom y value of the first rectangle
     * @param left2 the left x value of the second rectangle
     * @param top2 the top y value of the second rectangle
     * @param right2 the right x value of the second rectangle
     * @param bottom2 the bottom y value of the second rectangle
     * @return true if the rectangles are colliding, false otherwise
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

    /**
     * Check if 2 Entity objects are colliding
     * 
     * @param obj1 the first Entity object
     * @param obj2 the second Entity object
     * @return true if the entities are colliding, false otherwise
     * */
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

    /**
     * Converts path to BufferedImage
     *
     * @param path the path to an image (Must start with a "/")
     * @return the BufferedImage, if exists
     * */
    public static BufferedImage pathToImage(String path) {
        BufferedImage img;
        try {
            img = ImageIO.read(GameData.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return img;
    }

    /**
     * Converts String paths to image array
     *
     * @param paths the array of image paths to load
     * @return an array of BufferedImages loaded from the provided paths
     * */
    public static BufferedImage[] pathsToImages(String[] paths) {
        if (paths == null) {
            return new BufferedImage[0];
        }

        // Count valid paths
        int imageCount = 0;
        for (String path : paths) {
            if (path != null) {
                try {
                    BufferedImage img = ImageIO.read(GameData.class.getResourceAsStream(path));
                    if (img != null) {
                        imageCount++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Store valid images
        BufferedImage[] images = new BufferedImage[imageCount];
        int index = 0;
        for (String path : paths) {
            if (path != null) {
                images[index] = pathToImage(path);
            }
        }
        return images;
    }
}
