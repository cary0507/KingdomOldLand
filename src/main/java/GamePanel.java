/*
* Author: Cary
* Description:
* */

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // Environment settings
    public static final int PANEL_WIDTH = 1200;
    public static final int PANEL_HEIGHT = 840;
    public static final int HORIZON = PANEL_HEIGHT - 300;
    public static final int SCALE_FACTOR = 4;
    final int FPS = 60;
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
        // Set the preferred size of the panel
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);  // Smoother rendering
        this.addKeyListener(keyboard);  // Add key detection
        this.setFocusable(true);  // Focus on this game panel
        // Setup saved game data
        gameData = new GameData(keyboard, this);
        int[] map = gameData.getLandCode(1, 2);
        gameData.parseLandCode(map);
    }

    public void startGameThread() {
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
                // Draws the updated game
                repaint();
                deltaTime -= 1;  // Reset the delta time for the next frame
            }
        }
    }

    /**
     * Updates the game state, including player movement, enemy behavior, and other game logic.
     * */
    public void update() {
        gameData.player.update();
    }

    /**
     * Render the components with override codes from the original method
     * */
    @Override

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Structure structure : gameData.allStructures) {
            structure.render(g2, gameData.camera);
        }
        gameData.player.render(g2, gameData.camera);
        // Dispose of the graphics context to free up resources
        g2.dispose();
    }
}
