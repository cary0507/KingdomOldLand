import java.awt.*;
import java.awt.image.BufferedImage;

public class Chunk {
    public final int x, y;
    public final int hitboxWidth, hitboxHeight;
    public GameData.ChunkID id;
    public BufferedImage img;

    public Chunk(int x, int y, GameData.ChunkID chunkID, String imagePath) {
        this.x = x;
        this.y = y;
        this.id = chunkID;
        img = GameData.pathToImage(imagePath);
        hitboxWidth = img.getWidth() * GamePanel.SCALE_PIXEL;
        hitboxHeight = img.getHeight() * GamePanel.SCALE_PIXEL;
    }

    public void render(Graphics2D g2d,Camera referenceCam) {
        int screenX = referenceCam.convertX(this.x);
        int screenY = referenceCam.convertY(this.y);
        int onScreenWidth = img.getWidth() * GamePanel.SCALE_PIXEL;
        int onScreenHeight = img.getHeight() * GamePanel.SCALE_PIXEL;
        g2d.drawImage(img, screenX, screenY, onScreenWidth, onScreenHeight, null);
    }
}
