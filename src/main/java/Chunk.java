import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Chunk implements Serializable {
    public final int x, y;
    public final int HITBOX_WIDTH, HITBOX_HEIGHT;
    public GameData.ChunkID id;
    public transient BufferedImage img;
    public String imagePath;  // Store path for serialization/deserialization

    public Chunk(int x, int y, GameData.ChunkID chunkID, String imagePath) {
        this.x = x;
        this.y = y;
        this.id = chunkID;
        this.imagePath = imagePath;  // Store path for serialization
        img = GameData.pathToImage(imagePath);
        HITBOX_WIDTH = img.getWidth() * GamePanel.SCALE_PIXEL;
        HITBOX_HEIGHT = img.getHeight() * GamePanel.SCALE_PIXEL;
    }

    public void update(Player player) {

    }

    public void render(Graphics2D g2d, Camera referenceCam) {
        int screenX = referenceCam.convertX(this.x);
        int screenY = referenceCam.convertY(this.y);
        int onScreenWidth = img.getWidth() * GamePanel.SCALE_PIXEL;
        int onScreenHeight = img.getHeight() * GamePanel.SCALE_PIXEL;
        g2d.drawImage(img, screenX, screenY, onScreenWidth, onScreenHeight, null);
    }

    /**
     * Custom deserialization to restore transient BufferedImage from stored path
     *
     */
    private void readObject(ObjectInputStream objIn) throws IOException, ClassNotFoundException {
        objIn.defaultReadObject();
        // Reload image from stored path
        if (imagePath != null) {
            img = GameData.pathToImage(imagePath);
        }
    }

    /**
     * Custom serialization handler
     *
     */
    private void writeObject(ObjectOutputStream objOut) throws IOException {
        objOut.defaultWriteObject();
    }
}
