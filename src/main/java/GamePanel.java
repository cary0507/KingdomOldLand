import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements  Runnable{

    final int PANEL_WIDTH = 1200;
    final int PANEL_HEIGHT = 800;

    Thread gameThread;

    public GamePanel() {
        // Set the preferred size of the panel
        setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(java.awt.Color.WHITE);
        setDoubleBuffered(true);  // Smoother rendering
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // Update game state
        update();
        // Refresh background
        repaint();
        // Render objects on screen

    }

    public void update() {

    }

    public void renderComponent(Graphics g) {
        paintComponent(g);
    }
}
