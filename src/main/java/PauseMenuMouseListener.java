import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * <a href="https://www.geeksforgeeks.org/java/mouselistener-mousemotionlistener-java/">Click</a>
 * */
public class PauseMenuMouseListener extends MouseAdapter {

    /**
     * Reference to the GamePanel so the listener can access UI state and game data.
     * */
    private final GamePanel panel;

    /**
     * Creates a listener bound to a specific GamePanel.
     * */
    public PauseMenuMouseListener(GamePanel panel) {
        this.panel = panel;
    }

    /**
     * Handles mouse presses on pause‑menu buttons.
     * Executes resume, restart, or save‑and‑quit actions.
     * */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!panel.paused) return;   // Ignore clicks when not paused

        Point p = e.getPoint();

        if (panel.resumeBtn.contains(p)) {  // Resume button
            panel.paused = false;
        } else if (panel.restartBtn.contains(p)) { // Restart button
            // Resets game data
            panel.gameData = new GameData(panel.keyboard, panel);
            panel.leftBound = panel.gameData.leftBound;
            panel.rightBound = panel.gameData.rightBound;
            panel.paused = false;
        } else if (panel.saveQuitBtn.contains(p)) {  // Save & Quit button
            try {
                panel.gameData.saveGame(GamePanel.SAVE_FILE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.exit(0);  // Quit game
        }
    }
}