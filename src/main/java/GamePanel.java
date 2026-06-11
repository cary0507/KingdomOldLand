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
    public static final int FPS = 60;
    final int NANO_SEC = 1_000_000_000;
    final int MILLI_SEC = 1_000;
    // Scale settings
    KeyHandler keyboard = new KeyHandler();
    Thread gameThread;
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
        double drawInterval = (double) NANO_SEC / FPS;  // Time per frame in nanoseconds
        double deltaTime = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            // Calculate the time elapsed since the last frame
            currentTime = System.nanoTime();
            deltaTime += (currentTime - lastTime) / drawInterval;  // Accumulate the time in terms of frames
            lastTime = currentTime;
            if (deltaTime >= 1) {
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
        // Update player data
        gameData.player.update();
        // Update all mounts
        for (Mountable mount : gameData.allMounts) {
            mount.update();
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
        Graphics2D g2 = (Graphics2D) g;
        // Renders all structures
        for (Structure structure : gameData.allStructures) {
            structure.render(g2, gameData.camera);
        }
        // Renders all mounts
        for (Mountable mount : gameData.allMounts) {
            mount.render(g2, gameData.camera);
        }
        // Renders the player
        gameData.player.render(g2, gameData.camera);
        // Renders all chunks
        for (Chunk chunk : gameData.allChunks) {
            chunk.render(g2, gameData.camera);
        }
        // Dispose of the graphics context to free up resources
        g2.dispose();
    }
}
