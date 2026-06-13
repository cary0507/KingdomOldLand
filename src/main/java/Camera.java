import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Camera implements Serializable {
    public int x;
    public int y;
    public final int WIDTH;
    public final int HEIGHT;
    public final int DEAD_ZONE_WIDTH;
    public final int DEAD_ZONE_HEIGHT;
    transient GamePanel gamePanel;

    /**
     * Initialize the Camera object
     *
     * @param gamePanel the screen that is being tracked by the camera
     * @param x left of the camera
     * @param y top of the camera
     * @param deadZoneWidth the height of the dead zone (Main target will never appear inside)
     * @param deadZoneHeight the width of the dead zone
     * */
    public Camera(GamePanel gamePanel, int x, int y, int deadZoneWidth, int deadZoneHeight) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.WIDTH = GamePanel.PANEL_WIDTH;
        this.HEIGHT = GamePanel.PANEL_HEIGHT;
        this.DEAD_ZONE_WIDTH = deadZoneWidth;
        this.DEAD_ZONE_HEIGHT = deadZoneHeight;
    }

    /**
     * Converts a game X-coordinate to a screen X-coordinate based on the camera's position.
     * @param actualX the X-coordinate in the game world
     * @return the X-coordinate on the screen
     */
    public int convertX(int actualX) {
        return actualX - x;
    }

    /**
     * Converts a game Y-coordinate to a screen Y-coordinate based on the camera's position.
     * @param actualY the Y-coordinate in the game world
     * @return the Y-coordinate on the screen
     */
    public int convertY(int actualY) {
        return actualY - y;
    }

    /**
     * Warps the main focus Entity inside the free moving zone
     *
     * @param mainFocus the Entity to be focused on
     * */
    public void focusOn(Entity mainFocus) {
        // Stores calculation in variables
        // See /rough draft.png
        int right = x + this.WIDTH;
        int bottom = y + this.HEIGHT;
        int freeLeft = x + DEAD_ZONE_WIDTH;
        int freeTop = y + DEAD_ZONE_HEIGHT;
        int freeRight = right - DEAD_ZONE_WIDTH;
        int freeBottom = bottom - DEAD_ZONE_HEIGHT;
        int mainFocusRight = mainFocus.x + mainFocus.hitboxWidth;
        int mainFocusBottom = mainFocus.y + mainFocus.hitboxHeight;
        // Check for each boundary
        if (mainFocus.x < freeLeft) {       // Out of left bound
            this.x = mainFocus.x - DEAD_ZONE_WIDTH;
        }
        if (mainFocus.y < freeTop) {        // Out of top bound
            this.y = mainFocus.y - DEAD_ZONE_HEIGHT;
        }
        if (mainFocusRight > freeRight) {   // Out of right bound
            this.x = mainFocusRight + DEAD_ZONE_WIDTH - this.WIDTH;
        }
        if (mainFocusBottom > freeBottom) { // Out of bottom bound
            this.y = mainFocusBottom + DEAD_ZONE_HEIGHT - this.HEIGHT;
        }
    }

    /**
     * Custom deserialization to handle transient gamePanel
     * gamePanel must be set externally after deserialization
     * */
    private void readObject(ObjectInputStream objIn) throws IOException, ClassNotFoundException {
        objIn.defaultReadObject();
        // gamePanel is transient and must be set by the caller
    }

    /**
     * Custom serialization handler
     * */
    private void writeObject(ObjectOutputStream objOut) throws IOException {
        objOut.defaultWriteObject();
    }
}
