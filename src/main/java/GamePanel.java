/*
* Author: Cary
* Reference: https://www.youtube.com/watch?v=oPzPpUcDiYY&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=8
* */

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    // Environment settings
    public static final int PANEL_WIDTH = 1200;
    public static final int PANEL_HEIGHT = 840;
    public static final int HORIZON = PANEL_HEIGHT - 300;
    public static final int SCALE_PIXEL = 4;  // Pixel sizes of a square tile (Learned from YouTube)
    public int leftBound;
    public int rightBound;
    // Game state
    public boolean paused = false;
    // Time
    final int SEC_IN_NANO = 1_000_000_000;  // 1_000_000_000 nanosecond = 1 second
    public static final int FPS = 60;
    // Sky colors
    public final int MAX_BLUE = 255;
    public final int MIN_BLUE = 52;
    public final int RED_DIFF = 52;
    public final int GREEN_DIFF = 22;
    // Scale settings
    KeyHandler keyboard = new KeyHandler();
    Thread gameThread;
    // Game data setting
    public GameData gameData;

    /**
     * Initializes the game panel with its dimensions, background color, and key listener.
     * */
    public GamePanel() {
        // Set the preferred size of the panel (From YouTube)
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);  // Smoother rendering
        this.addKeyListener(keyboard);  // Add key detection
        this.setFocusable(true);  // Focus on this game panel
        // Setup saved game data
        gameData = new GameData(keyboard, this);
        leftBound = gameData.leftBound;
        rightBound = gameData.rightBound;
    }

    public void startGameThread() {
        // Learned from YouTube
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The main game loop that updates the game state and renders the graphics at a consistent frame rate.
     * */
    @Override
    public void run() {
        double drawInterval = (double) SEC_IN_NANO / FPS;  // Time per frame in nanoseconds
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (gameThread != null) {
            // Calculate the time elapsed since the last frame (Learned from YouTube)
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / drawInterval;  // Accumulate the time in terms of frames
            lastTime = currentTime;

            if (deltaTime >= 1) {
                // Store the current frame
                gameData.framePassed+=10;
                if (gameData.framePassed >= gameData.NEXT_DAY_FRAME) {
                    gameData.framePassed = 0;
                }
                // Update game properties
                update();
                // Resets keys after the update
                keyboard.resetTiggerKey();
                // Draws the updated game (Learned from YouTube video)
                repaint();
                deltaTime -= 1;  // Reset the delta time for the next frame
            }
        }
    }

    /**
     * Handles the interactions between projectiles and other objects
     * */
    public void updateProjectiles () {
        for (int i = 0; i < gameData.allProjectiles.size(); i++) {
            Projectile projectile = gameData.allProjectiles.get(i);
            projectile.update();
            // Check if this projectile is a coin
            if (Objects.requireNonNull(projectile.data.getId()) == GameData.ItemID.COIN) {
                // The player will pick up the coin if they collide and the coin is not in pickup delay
                if (GameData.isInside(projectile, gameData.player.mount)
                        && projectile.data.curPickFrame >= projectile.data.maxPickDelay) {  // Prevents instant pick

                    gameData.player.moneyBag.addCoin(projectile, gameData.player);
                    gameData.allProjectiles.remove(projectile);
                }
                // Humans can pick up coin only if they have space for their money bag
                for (Human human : gameData.allHumans) {
                    if (GameData.isInside(human, projectile)
                            && human.moneyBag.capacity > human.moneyBag.numCoins
                            && projectile.data.curPickFrame >= projectile.data.maxPickDelay) {

                        human.moneyBag.addCoin(projectile, human);
                        gameData.allProjectiles.remove(projectile);
                        break;
                    }
                }
                // When the coin interacts with a tradable structure
                for (UpgradableStruct upgradeStruct : gameData.allUpgradable) {
                    if (GameData.isInside(upgradeStruct, projectile)
                            // Check if the object can further level up
                            && upgradeStruct.level < upgradeStruct.leftImages.length) {

                        // Calculate new hp
                        int newHP = upgradeStruct.maxHP + 5 + 2 * upgradeStruct.level;
                        upgradeStruct.levelUp(newHP);
                        gameData.allUpgradable.remove(upgradeStruct);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Updates the game state, including player movement, enemy behavior, and other game logic.
     * */
    public void update() {
        Random rand = new Random();
        // Determines the stage of the day
        boolean isNight;
        // Updates background
        gameData.changeSkyColor(MAX_BLUE, MIN_BLUE, RED_DIFF, GREEN_DIFF);  // Sky
        if (gameData.framePassed < gameData.NIGHT_FRANE) {  // Noon
            // Sun's orbit
            gameData.sun.update(gameData.framePassed);
            isNight = false;
        } else if (gameData.framePassed == gameData.NIGHT_FRANE) {  // At Night
            // Choose a random portal to spawn enemy
            int bound = gameData.allPortals.size();
            int choice = rand.nextInt(bound);
            isNight = true;
            gameData.allEnemies.addAll(gameData.allPortals.get(choice).generateEnemies());
        } else {  // Night
            // Moon's orbit
            int sinceNight = gameData.framePassed - gameData.NIGHT_FRANE;
            gameData.moon.update(sinceNight);
            isNight = true;
        }

        // Update player data
        gameData.player.update();

        // Update each mount
        for (int i = 0; i < gameData.allMounts.size(); i++) {
            Mountable mount = gameData.allMounts.get(i);
            mount.update();
        }

        // Update each structure
        for (int i = 0; i < gameData.allContainers.size(); i++) {
            ContainerStruct container = gameData.allContainers.get(i);
            container.update();
        }
        for (int i = 0; i < gameData.allUpgradable.size(); i++) {
            UpgradableStruct upgradeStruct = gameData.allUpgradable.get(i);
            upgradeStruct.update();
        }

        updateProjectiles();

        // Update each human
        for (int i = 0; i < gameData.allHumans.size(); i++) {
            Human human = gameData.allHumans.get(i);
            // Set the habitate
            switch (human.id) {
                case FUGITIVE:
                case VILLAGER:
                case FARMER:   // Fall through
                    human.habitat = gameData.townCenter;
                    break;
            }
            human.update(isNight);
        }

        // Update each enemy
        for (int i = 0; i < gameData.allEnemies.size(); i++) {
            Enemy enemy = gameData.allEnemies.get(i);
            enemy.update(gameData.player);
        }

        // Update each chunk
        for (int i = 0; i < gameData.allChunks.size(); i++) {
            Chunk chunk = gameData.allChunks.get(i);
            chunk.update(gameData.player);
        }
        // The order of the update matters because if camera is updated first, it will take some delay for the camera
        // to focus on the main objects again
        gameData.camera.focusOn(gameData.player.mount);  // Update camera

    }

    /**
     * Render the components with override codes from the original method
     * */
    @Override  // Learned from YouTube video
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Renders background
        this.setBackground(gameData.skyColor);
        if (gameData.framePassed <= gameData.NIGHT_FRANE) {
            gameData.sun.render(g2d, 40 * SCALE_PIXEL, 40 * SCALE_PIXEL, Color.WHITE);
        } else {
            Color moonColor = new Color(133, 147, 154);
            gameData.moon.render(g2d, 35 * SCALE_PIXEL, 35 * SCALE_PIXEL, moonColor);
        }

        // Draw river front
        Color riverColor = new Color(93, 112, 106);
        g2d.setColor(riverColor);
        g2d.fillRect(0, HORIZON + 120, GamePanel.PANEL_WIDTH, 50);

        // Renders all structures
        for (ContainerStruct container : gameData.allContainers) {
            container.render(g2d, gameData.camera);
            if (GameData.isInside(container, gameData.player.mount)
                    // If shelf is not full
                    && container.numItems < container.containing.length) {

                container.renderHint(g2d, gameData.camera);
            }
        }
        for (UpgradableStruct upgradeStruct : gameData.allUpgradable) {
            upgradeStruct.render(g2d, gameData.camera);
            if (GameData.isInside(upgradeStruct, gameData.player.mount)
                    // If not at max level
                    && upgradeStruct.level < upgradeStruct.maxLevel) {

                upgradeStruct.renderHint(g2d, gameData.camera);
            }
        }

        // Renders all NPC
        for (Human human : gameData.allHumans) {
            human.render(g2d, gameData.camera);
        }

        // Render all enemies
        for (Enemy enemy: gameData.allEnemies) {
            enemy.render(g2d, gameData.camera);
        }

        // Render all mounts
        for (Mountable mount : gameData.allMounts) {
            mount.render(g2d, gameData.camera);
        }

        // Render the player
        gameData.player.render(g2d, gameData.camera);

        // Render the projectiles
        for (Projectile projectile : gameData.allProjectiles) {
            projectile.render(g2d, gameData.camera);
        }

        // Render second layer of river
        g2d.setColor(riverColor);
        g2d.fillRect(0, HORIZON + 170, GamePanel.PANEL_WIDTH, 140);
        // Render chunks
        for (Chunk chunk : gameData.allChunks) {
            chunk.render(g2d, gameData.camera);
        }

        // Render all portals
        for (int i = 0; i < gameData.allPortals.size(); i++) {
            Portal portal = gameData.allPortals.get(i);
            portal.render(g2d, gameData.camera);
        }
        // Dispose of the graphics context to free up resources
        g2d.dispose();
    }
}
