import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class GameData implements Serializable {
    // serialVersionUID for Serializable compatibility (no @Serial annotation to keep Java 8 compatibility)
    private static final long serialVersionUID = 1L;
    public static final double GRAVITY = 1.5;
    public static final double UNIVERSAL_TOP_SPEED = 6.0;
    public static final double NPC_TOP_SPEED = 4.0;
    public static final double VAGRANT_TOP_SPEED = 1.0;
    public static final double ENEMY_TOP_SPEED = 5.0;
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
    public static String[] payHint = {
            "/raw images/Coin/Trade/Pay.png"
    };
    public static String[] coinImg;
    public static String[] moneyBagImg = {
            "/raw images/Money bag/empty.png",
            "/raw images/Money bag/1~5.png",
            "/raw images/Money bag/6~10.png"
    };
    public static String[] wallImgL = {
            "/raw images/Wall/lvl0/Wall lvl 0 L.png",
            "/raw images/Wall/lvl1/Wall lvl 1 L.png",
            "/raw images/Wall/lvl2/Wall lvl 2 L.png"
    };
    public static String[] wallImgR = {
            "/raw images/Wall/lvl0/Wall lvl 0 R.png",
            "/raw images/Wall/lvl1/Wall lvl 1 R.png",
            "/raw images/Wall/lvl2/Wall lvl 2 R.png"
    };
    public static String[] townCenterImg = {
            "/raw images/Town center/lvl0/Town center lvl0.png",
            "/raw images/Town center/lvl1/Town center lvl1 anim1.png",
            "/raw images/Town center/lvl2/Town center lvl 2 1.png"
    };
    public static String[] bowShop = {
            "/raw images/vendor/bow shop.png"
    };
    public static String[] sickleShop = {
            "/raw images/vendor/sickle shop.png"
    };
    public static String[] humanImgL = {
            "/raw images/NPC/vagrant L.png",
            "/raw images/NPC/Villager L.png",
            "/raw images/NPC/Farmer L.png",
            "/raw images/NPC/Archer L.png"
    };
    public static String[] humanImgR = {
            "/raw images/NPC/vagrant R.png",
            "/raw images/NPC/Villager R.png",
            "/raw images/NPC/Farmer L.png",
            "/raw images/NPC/Archer R.png"
    };
    public static String[] portalImgL = {
            "/raw images/Portal/Portal L.png",
    };
    public static String[] portalImgR = {
            "/raw images/Portal/Portal R.png"
    };
    public static String[] enemyImgL = {
            "/raw images/Greedling/Greedling L.png"
    };
    public static String[] enemyImgR = {
            "/raw images/Greedling/Greedling R.png"
    };
    public static String[] bowShopImg = {
            "/raw images/vendor/bow shop.png"
    };
    public static String[] sickleShopImg = {
            "/raw images/vendor/sickle shop.png"
    };
    public static String[] bowItemImg = {
            "raw images/Items/bow.png"
    };
    public static String[] sickleItemImg = {
            "raw images/Items/sickle.png"
    };
    public static String[] arrowImgL = {
            "/raw images/Arrow/arrow L.png"
    };
    public static String[] arrowImgR = {
            "/raw images/Arrow/arrow R.png"
    };
    // Object IDs
    public enum ItemID {
        CROWN,
        COIN,
        BOW,
        SICKLE,
        ARROW
    }
    public enum JobID {
        FUGITIVE,
        VILLAGER,
        FARMER,
        ARCHER
    }
    public enum ChunkID {
        SPAWN_CHUNK,
        DIRT,
        PLAIN
    }
    public enum StructureID {
        TOWN_CENTER,
        WALL,
        BOW_SHELF,
        SICKLE_SHELF,
        PORTAL
    }
    // Real time in seconds
    public int framePassed;             // Frames passed in real life
    public final int SUNRISE_DURATION;  // Frames it takes for the sky to turn from dark to bright
    public final int SUNSET_DURATION;   // Frames it takes for the sky to turn from bright to dark in seconds
    public final int NIGHT_FRANE;         // Starts enter night after 120 seconds passed
    public final int NEXT_DAY_FRAME;      // Starts entering next dat after 180 seconds passed
    public int dayPassed;
    Color skyColor;
    // Background objects
    Orbits sun;
    Orbits moon;
    public ArrayList<Chunk> allChunks;
    public int leftBound = 200;
    public int rightBound;
    // Game objects (transient because they're runtime references)
    public transient GamePanel gamePanel;
    public transient KeyHandler keyHandler;
    public Camera camera;
    public Player player;
    public UpgradableStruct townCenter;
    public ArrayList<PickedItem> allPickedItems;
    public ArrayList<Human> allHumans;
    // Do not mix
    public ArrayList<UpgradableStruct> allUpgradable;
    public ArrayList<ContainerStruct> allContainers;
    public ArrayList<Portal> allPortals;
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
        // Initialize time
        framePassed = 0;
        dayPassed = 0;
        // Converts time in seconds to frames
        SUNRISE_DURATION = 8 * GamePanel.FPS;
        SUNSET_DURATION = 5 * GamePanel.FPS;
        NIGHT_FRANE = 60 * GamePanel.FPS;
        NEXT_DAY_FRAME = 90 * GamePanel.FPS;

        // Initialize sun and moon
        sun = new Orbits(
                GamePanel.PANEL_WIDTH + 20 * GamePanel.SCALE_PIXEL,
                GamePanel.PANEL_HEIGHT + 50 * GamePanel.SCALE_PIXEL,
                GamePanel.PANEL_WIDTH / 2,
                GamePanel.HORIZON + 40 * GamePanel.SCALE_PIXEL,  // 50 tiles below horizon
                60 * GamePanel.FPS  // 60 seconds duration
        );
        moon = new Orbits(
                GamePanel.PANEL_WIDTH + 20 * GamePanel.SCALE_PIXEL,
                GamePanel.PANEL_HEIGHT + 50 * GamePanel.SCALE_PIXEL,
                GamePanel.PANEL_WIDTH / 2,
                GamePanel.HORIZON + 40 * GamePanel.SCALE_PIXEL,
                30 * GamePanel.FPS  // 30 seconds duration
        );

        // Initialize the object's data
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        allPickedItems = new ArrayList<>();
        allHumans = new ArrayList<>();
        allChunks = new ArrayList<>();
        allUpgradable = new ArrayList<>();
        allContainers = new ArrayList<>();
        allPortals = new ArrayList<>();
        allEnemies = new ArrayList<>();
        allProjectiles = new ArrayList<>();
        allMounts = new ArrayList<>();

        // Set up the animation image files
        int numCoinFrames = 8;
        coinImg = new String[numCoinFrames];
        String coinImgFormat = "/raw images/Coin/Thrown/coin%d.png";
        for (int i = 1; i <= numCoinFrames; i++) {
            coinImg[i - 1] = String.format(coinImgFormat, i);
        }

        // Setup camera
        camera = new Camera(gamePanel, 0, 0, 180, 100);
        // Set up the map and player with default mount
        int[] map = getLandCode(2, 2);
        initLand(map);
    }

    /**
     * Set up the default horse and add it to the mountable ArrayList
     * */
    public Mountable getHorse(int spawnX, double maxSpeed) {
        // Setup default horse
        Mountable mount = new Mountable(
                spawnX,
                GamePanel.HORIZON,
                maxSpeed,
                100,
                gamePanel
        );
        mount.setImagesFromPaths(brownHorseImgL, brownHorseImgR);
        // adjust y after hitbox height is known
        mount.y -= mount.hitboxHeight;
        mount.setPassengerOffset(14, 7, 12, 7);
        return mount;
    }

    /**
     * Drop a coin on the ground
     * */
    public Projectile getCoinOnGround(int x) {
        ItemData coinData = new ItemData(ItemID.COIN, coinImg, coinImg, true);
        Projectile coin = new Projectile(x, GamePanel.HORIZON, UNIVERSAL_TOP_SPEED, gamePanel, coinData);
        coin.setMotionValues(0, 0, 0, 0, 0, false);
        coin.setImagesFromPaths(coin.data.thrownImgPathL, coin.data.thrownImgPathR);
        coin.data.curPickFrame = coin.data.maxPickDelay;
        coin.y -= coin.hitboxHeight;  // Align to ground
        return coin;
    }

    /**
     * Spawns a default NPC
     * */
    public Human spawnNPC(int x) {
        Human npc = new Human(x, GamePanel.HORIZON, NPC_TOP_SPEED, townCenter, gamePanel);
        npc.setImagesFromPaths(humanImgL, humanImgR);
        npc.y -= npc.hitboxHeight;
        return npc;
    }

    /**
     * Create a wall to the left of the spawn chunk
     * */
    public UpgradableStruct getLeftWall(int alignLeftX) {
        UpgradableStruct wallLeft = new UpgradableStruct(
                alignLeftX, GamePanel.HORIZON,
                GameData.StructureID.WALL, null,  // No one sit on the wall
                gamePanel
        );
        wallLeft.isFacingLeft = true;  // Facing left
        wallLeft.setImagesFromPaths(wallImgL, wallImgR);
        wallLeft.y -= wallLeft.hitboxHeight;  // Align bottom
        return wallLeft;  // Add object to game
    }

    /**
     * Create a wall to the right of the spawn chunk
     * */
    public UpgradableStruct getRightWall(int alignRightX) {
        UpgradableStruct wallRight = new UpgradableStruct(
                alignRightX, GamePanel.HORIZON,
                GameData.StructureID.WALL, null,
                gamePanel
        );
        wallRight.isFacingLeft = false;  // Facing right
        wallRight.setImagesFromPaths(wallImgL, wallImgR);
        wallRight.x -= wallRight.hitboxWidth;   // Align right
        wallRight.y -= wallRight.hitboxHeight;  // Align bottom
        return wallRight;
    }

    /**
     * Create a sickleShop aligning the left position
     * */
    private ContainerStruct getSickleShop(int leftX) {
        // Offsets see "/sickle shop+sickle item reference.png"
        int[][] sickleShopOffsets = {
                {14, 26},
                {14 + 9, 26},
                {14 + 9 + 9, 26},
                {14 + 9 + 9 + 9, 26}
        };
        ContainerStruct sickleShop = new ContainerStruct(
                leftX - 50 * GamePanel.SCALE_PIXEL,
                GamePanel.HORIZON,
                0, StructureID.SICKLE_SHELF,
                sickleShopOffsets, gamePanel
        );
        sickleShop.setImagesFromPaths(sickleShopImg, sickleShopImg);
        sickleShop.y -= sickleShop.hitboxHeight; // Align bottom
        return sickleShop;
    }

    /**
     * Creates a bow shop aligning the right position
     * */
    private ContainerStruct getBowShop(int rightX) {
        // Reference: "src/main/resources/raw images/bow shop+bow item reference.png"
        int[][] bowShopOffsets = {  // Determines the offest of elements in each cell
                {16, 25},
                {16 + 10, 25},
                {16 + 10 + 9, 25},
                {16 + 10 + 9 + 9, 25}
        };
        ContainerStruct bowShop = new ContainerStruct(
                rightX + 50 * GamePanel.SCALE_PIXEL,
                GamePanel.HORIZON,
                0, StructureID.BOW_SHELF,
                bowShopOffsets, gamePanel
        );
        bowShop.setImagesFromPaths(bowShopImg, bowShopImg);
        bowShop.x -= bowShop.hitboxWidth;   // Align right
        bowShop.y -= bowShop.hitboxHeight;  // Align bottom
        return bowShop;
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
        Random randGen = new Random();

        int totalChunks = landRadius * 2 + 1;
        int[] landCode = new int[totalChunks];
        int mid = (int) ((totalChunks - 1) / 2);  // Explicit is better than implicit
        // Loop through the array and add random numbers
        for (int i = 0; i < landCode.length; i++) {
            if (i != mid) {
                int randNum = randGen.nextInt(numChunkOptions - 1 + 1) + 1;  // nextInt(hi - lo + 1) + lo
                landCode[i] = randNum;
            } else {
                landCode[i] = 0;
            }
        }
        return landCode;
    }

    /**
     * Initialize map based on land code
     * */
    public void initLand(int[] landCode) {
        Random randGen = new Random();
        int curChunkX = 0;  // Acts like a cursor
        int midIndex = landCode.length / 2;

        // Generate the left portal
        Portal leftPortal = new Portal(0, GamePanel.HORIZON, gamePanel, false);
        leftPortal.y -= leftPortal.hitboxHeight - 10 * GamePanel.SCALE_PIXEL;
        allPortals.add(leftPortal);

        // Loop through the code
        for (int i = 0; i < landCode.length; i++) {
            int code = landCode[i];
            // Set up the background chunk
            ChunkID chunkID = ChunkID.values()[code];
            // Creates the chunk
            Chunk curChunk = new Chunk(
                    curChunkX, GamePanel.HORIZON - 2 * GamePanel.SCALE_PIXEL, chunkID, chunksImg[code]
            );
            curChunkX += curChunk.hitboxWidth;  // Moves to the next Chunk's start
            allChunks.add(curChunk);  // Adds chunk to the game
            // Parse different codes
            if (i < midIndex) {  // Left of the spawn left
                int leftX = curChunkX - curChunk.hitboxWidth + (randGen.nextInt(150) + 80) * GamePanel.SCALE_PIXEL;
                int rightX = curChunkX - (randGen.nextInt(30) + 100) * GamePanel.SCALE_PIXEL;
                // Randomly add walls at different locations
                allUpgradable.add(getLeftWall(leftX));
                allUpgradable.add(getLeftWall(rightX));
            }
            else if (i == midIndex) {  // Spawn chunk is guaranteed to have specific structures
                // Gets the spawn location for player's mount
                int spawnX = (int) (curChunkX - 2 * curChunk.hitboxWidth / 3);
                Mountable defaultHorse = getHorse(spawnX, UNIVERSAL_TOP_SPEED);
                allMounts.add(defaultHorse);  // Add to game

                // Setup player
                player = new Player(keyHandler, gamePanel, defaultHorse);
                player.setImagesFromPaths(playerImgL, playerImgR);

                // Create a default wall structure to the left of spawn
                int leftX = curChunkX - curChunk.hitboxWidth + 10 * GamePanel.SCALE_PIXEL;
                UpgradableStruct defaultWallL = getLeftWall(leftX);
                allUpgradable.add(defaultWallL);  // Don't forget to add to game

                // Create the default wall to the right of the spawn
                int rightX = curChunkX - 10 * GamePanel.SCALE_PIXEL;
                UpgradableStruct defaultWallR = getRightWall(rightX);
                allUpgradable.add(defaultWallR);

                // Add 2 vagrants
                allHumans.add(spawnNPC(leftX - 70 * GamePanel.SCALE_PIXEL));
                allHumans.add(spawnNPC(leftX - 80 * GamePanel.SCALE_PIXEL));

                // Create the item shops
                ContainerStruct sickleShop = getSickleShop(leftX + 100 *  GamePanel.SCALE_PIXEL);
                allContainers.add(sickleShop);
                ContainerStruct bowShop = getBowShop(rightX - 100 * GamePanel.SCALE_PIXEL);
                allContainers.add(bowShop);

                // Drop 8 coins
                int midX = curChunkX - curChunk.hitboxWidth / 2;
                for (int n = 0; n < 8; n++) {
                    int coinX = midX + 2 * GamePanel.SCALE_PIXEL * n;
                    allProjectiles.add(getCoinOnGround(coinX));
                }

                // Create the town center
                this.townCenter = new UpgradableStruct(
                        midX, GamePanel.HORIZON, StructureID.TOWN_CENTER, null, gamePanel
                );
                this.townCenter.setImagesFromPaths(townCenterImg, townCenterImg);  // Never turns and always at middle
                this.townCenter.x -= (int) (this.townCenter.hitboxWidth / 2);  // Align center
                this.townCenter.y -= this.townCenter.hitboxHeight;             // Align bottom
                allUpgradable.add(this.townCenter);
            }
            else {  // Right of the spawn land
                int leftX = curChunkX - curChunk.hitboxWidth + (randGen.nextInt(150) + 80) * GamePanel.SCALE_PIXEL;
                int rightX = curChunkX - (randGen.nextInt(30) + 100) * GamePanel.SCALE_PIXEL;
                allUpgradable.add(getRightWall(leftX));
                allUpgradable.add(getRightWall(rightX));
            }
        }

        // Generate the right portal
        Portal rightPortal = new Portal(curChunkX, GamePanel.HORIZON, gamePanel, true);
        rightPortal.x -= rightPortal.hitboxWidth;
        rightPortal.y -= rightPortal.hitboxHeight - 10 * GamePanel.SCALE_PIXEL;
        allPortals.add(rightPortal);

        rightBound = curChunkX - 200;
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
     * Custom deserialization to restore transient GamePanel and KeyHandler references
     * This method is called automatically when the GameData object is deserialized
     * */
    private void readObject(ObjectInputStream objIn) throws IOException, ClassNotFoundException {
        objIn.defaultReadObject();
    }

    /**
     * Custom serialization handler
     * Ensures that all necessary fields are properly serialized
     * */
    private void writeObject(ObjectOutputStream objOut) throws IOException {
        objOut.defaultWriteObject();
    }

    /**
     * Reinitialized transient fields after deserialization
     *
     * @param gamePanel the GamePanel instance for the game
     * @param keyHandler the KeyHandler instance for the game
     * */
    public void resetTransient(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        // Resets camera
        if (camera != null) {
            camera.gamePanel = gamePanel;  // Resets gamePanel
        }
        // Reset player's fields
        if (player != null) {
            player.gamePanel = gamePanel;
            player.keyInput = keyHandler;           // Resets player keyboard input
            player.moneyBag.gamePanel = gamePanel;  // Resets player's moneybag
        }
        // Resets all humans
        for (Human human : allHumans) {
            if (human != null) {
                human.gamePanel = gamePanel;
                if (human.moneyBag != null) {  // Reset
                    human.moneyBag.gamePanel = gamePanel;
                }
            }
        }
        // Resets all upgradable structures
        for (UpgradableStruct upgradableStruct : allUpgradable) {
            if (upgradableStruct != null) {
                upgradableStruct.gamePanel = gamePanel;
            }
        }
        // Resets all container structures
        for (ContainerStruct containerStruct : allContainers) {
            if (containerStruct != null) {
                containerStruct.gamePanel = gamePanel;
            }
        }
        // Resets the portal
        for (Portal portal : allPortals) {
            if (portal != null) {
                portal.gamePanel = gamePanel;
            }
        }
        // Resets all enemies
        for (Enemy enemy : allEnemies) {
            if (enemy != null) {
                enemy.gamePanel = gamePanel;
            }
        }
        // Resets all projectiles
        for (Projectile projectile : allProjectiles) {
            if (projectile != null) {
                projectile.gamePanel = gamePanel;
            }
        }
        // Resets all mount
        for (Mountable mount : allMounts) {
            if (mount != null) {
                mount.gamePanel = gamePanel;
            }
        }
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
        int right1 = obj1.x + obj1.hitboxWidth;
        int right2 = obj2.x + obj2.hitboxWidth;
        int bottom1 = obj1.y + obj1.hitboxHeight;
        int bottom2 = obj2.y + obj2.hitboxHeight;
        return isInside(obj1.x, obj1.y, right1, bottom1, obj2.x, obj2.y, right2, bottom2);
    }

    /**
     * Calculate the center x coordinate of an object
     */
    public static int getCenterX(Entity obj) {
        return (int) (obj.x + obj.hitboxWidth / 2);
    }

    /**
     * Set the center x coordinate of an object
     * */
    public static void setCenterX(Entity obj, int newCenterX) {
        obj.x = newCenterX - obj.hitboxWidth / 2;
    }

    /**
     * Get the distance between 2 x values
     * */
    public static int getDist(int x1, int x2) {
        return Math.abs(x1 - x2);
    }

    /**
     * Get the distance between 2 objects
     * */
    public static int getDist(Entity obj1, Entity obj2) {
        if (obj1 == obj2) {
            return 0;
        }
        int center1 = getCenterX(obj1);
        int center2 = getCenterX(obj2);
        return getDist(center1, center2);
    }

    /**
     * Converts path to BufferedImage
     *
     * @param path the path to an image (Must start with a "/")
     * @return the BufferedImage, if exists
     * */
    public static BufferedImage pathToImage(String path) {
        // Learned from YouTube
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
                BufferedImage img = pathToImage(path);
                if (img != null) {
                    imageCount++;
                }
            }
        }

        // Store valid images
        BufferedImage[] images = new BufferedImage[imageCount];
        int index = 0;
        for (String path : paths) {
            if (path != null) {
                images[index] = pathToImage(path);
                index++;
            }
        }
        return images;
    }

    /**
     * Returns the color of the sky with the current time in second stored
     * Precondition: brightestBlue is larger than darkestBlue, they should range between 0 and 255 inclusive
     *               redDiff is smaller than darkestBlue, greenDiff is smaller than darkestBlue
     * Postcondition: changes the rgb color of the current secPassed stored in this class
     * <a href="https://www.desmos.com/calculator/b8fehioczd">Experiment by myself</a>
     * */
    public void changeSkyColor(int brightestBlue, int darkestBlue, int redDiff, int greenDiff) {
        int deltaY = brightestBlue - darkestBlue;  // y2 - y1
        double slope;
        int durationPassed;  // Seconds since the color starts changing
        int blueValue;  // The sky is blue

        // Determine which stage of day it is in
        if (this.framePassed <= SUNRISE_DURATION) {                       // Morning (Dark to bright)
            slope = (double) deltaY / SUNRISE_DURATION;  // Slope is positive
            durationPassed = this.framePassed;
            blueValue = (int) (slope * durationPassed + darkestBlue);  // bracket to avoid truncate too early
        } else if (this.framePassed <= NIGHT_FRANE) {                       // Noon (Brightest unchanged)
            blueValue = brightestBlue;  // Blue value in its brightest stage
        } else if (this.framePassed <= (NIGHT_FRANE + SUNSET_DURATION)) {   // Night (Bright to dark)
            slope = - (double) deltaY / SUNSET_DURATION;  // Slope is negative
            durationPassed = this.framePassed - NIGHT_FRANE;
            blueValue = (int) (slope * durationPassed + brightestBlue);
        } else {                                                        // Midnight (Darkest unchanged)
            blueValue = darkestBlue;  // Blue value in its darkest stage
        }
        // Gets red and green values
        int redValue = blueValue - redDiff;
        int greenValue = blueValue - greenDiff;
        this.skyColor = new Color(redValue, greenValue, blueValue);
    }
}
