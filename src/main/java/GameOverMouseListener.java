import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverMouseListener extends MouseAdapter {

    private final GamePanel panel;

    public GameOverMouseListener(GamePanel panel) {
        this.panel = panel;
    }

    /**
     * Check for mouse event
     * */
    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();

        if (panel.respawnBtn.contains(p)) {
            // Resets game data
            panel.gameData = new GameData(panel.keyboard, panel);
            panel.leftBound = panel.gameData.leftBound;
            panel.rightBound = panel.gameData.rightBound;
            panel.lost = false;
        }
    }
}