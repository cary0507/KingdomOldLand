import javax.swing.*;

public class GamePanel extends JPanel {
    final int PANEL_WIDTH = 1200;
    final int PANEL_HEIGHT = 800;

    public GamePanel() {
        // Set the preferred size of the panel
        setPreferredSize(new java.awt.Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(java.awt.Color.WHITE);
        setDoubleBuffered(true);  // Smoother rendering
    }
}
