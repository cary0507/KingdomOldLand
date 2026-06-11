/*
* Author: Cary
* Reference: https://www.youtube.com/watch?v=oPzPpUcDiYY&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=8
* */

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Environment settings
    public static final int PANEL_WIDTH = 1200;
    public static final int PANEL_HEIGHT = 840;
    public static final int HORIZON = PANEL_HEIGHT - 300;
    public static final int SCALE_PIXEL = 4;  // Pixel sizes of a square tile (Learned from YouTube)
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
                gameData.framePassed++;
                if (gameData.framePassed >= gameData.NEXT_DAY_FRAME) {
                    gameData.framePassed = 0;
                }

                // Update game properties
                update();
                // Draws the updated game (Learned from YouTube video)
                repaint();
                deltaTime -= 1;  // Reset the delta time for the next frame
            }
        }
    }

    /**
     * Updates the game state, including player movement, enemy behavior, and other game logic.
     * */
    public void update() {
        // Updates background
        gameData.changeSkyColor(MAX_BLUE, MIN_BLUE, RED_DIFF, GREEN_DIFF);  // Sky
        if (gameData.framePassed <= gameData.NIGHT_FRANE) {  // Noon
            gameData.sun.update(gameData.framePassed);
        } else {  // Night
            int sinceNight = gameData.framePassed - gameData.NIGHT_FRANE;
            gameData.moon.update(sinceNight);
        }
        // Update player data
        gameData.player.update();
        // Update each mount
        for (int i = 0; i < gameData.allMounts.size(); i++) {
            Mountable mount = gameData.allMounts.get(i);
            mount.update();
        }

        // Update each structure
        for (int i = 0; i < gameData.allStructures.size(); i++) {
            Structure structure = gameData.allStructures.get(i);
            structure.update();
        }

        // Update each projectile
        for (int i = 0; i < gameData.allProjectiles.size(); i++) {
            Projectile projectile = gameData.allProjectiles.get(i);
            projectile.update();
            // Check if this projectile is a coin
            switch (projectile.data.getId()) {
                case COIN:
                    // The player will pick up the coin if they collides
                    if (gameData.isInside(projectile, gameData.player)) {
                        gameData.player.moneyBag.addCoin(projectile);
                        gameData.allProjectiles.remove(projectile);
                    }
                    break;
            }
        }

        // Update each human
        for (int i = 0; i < gameData.allHumans.size(); i++) {
            Human human = gameData.allHumans.get(i);
            human.update();
        }

        // Update each enemy
        for (int i = 0; i < gameData.allEnemies.size(); i++) {
            Enemy enemy = gameData.allEnemies.get(i);
            enemy.update();
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
            gameData.moon.render(g2d, 40 * SCALE_PIXEL, 40 * SCALE_PIXEL, moonColor);
        }
        // Renders all structures
        for (Structure structure : gameData.allStructures) {
            structure.render(g2d, gameData.camera);
        }
        for (Human human : gameData.allHumans) {
            human.render(g2d, gameData.camera);
        }
        for (Enemy enemy: gameData.allEnemies) {
            enemy.render(g2d, gameData.camera);
        }
        // Renders all mounts
        for (Mountable mount : gameData.allMounts) {
            mount.render(g2d, gameData.camera);
        }
        // Renders the player
        gameData.player.render(g2d, gameData.camera);
        gameData.player.moneyBag.render(g2d);
        for (Projectile projectile : gameData.allProjectiles) {
            projectile.render(g2d, gameData.camera);
        }
        for (Chunk chunk : gameData.allChunks) {
            chunk.render(g2d, gameData.camera);
        }
        // Dispose of the graphics context to free up resources
        g2d.dispose();
    }
}
