/*
* Author: Cary
* Description:
* */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

public class GamePanel extends JPanel implements Runnable {
    // Environment settings
    final int PANEL_WIDTH = 1200;
    final int PANEL_HEIGHT = 800;
    final int HORIZON = PANEL_HEIGHT - 200;
    final int FPS = 60;
    final int NANO_SEC = 1_000_000_000;
    final int MILLI_SEC = 1_000;
    KeyHandler keyboard = new KeyHandler();
    Thread gameThread;

    public GamePanel() {
        // Set the preferred size of the panel
        this.setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);  // Smoother rendering
        this.addKeyListener(keyboard);  // Add key detection
        this.setFocusable(true);  // Focus on this game panel
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) NANO_SEC / FPS;  // Time per frame in nanoseconds
        double deltaTime = 0;
        long lastTime = System.nanoTime();

        while (gameThread != null) {
            // Calculate the time elapsed since the last frame
            long currentTime = System.nanoTime();
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

    public void update() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.fillRect(0, HORIZON, PANEL_WIDTH, PANEL_HEIGHT - HORIZON);
        // Dispose of the graphics context to free up resources
        g2.dispose();
    }
}
