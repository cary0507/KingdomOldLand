import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Structure implements Serializable {
    public int x;
    public int y;
    public int height;
    public int width;
    public int maxHP;
    public int curHP;
    public GameData.ID id;
    public BufferedImage image;
    public GamePanel gamePanel;

    /**
     * Initializes the structure with its position, dimensions, hit points, and image path.
     *
     * @param x the x-coordinate of the structure
     * @param y the y-coordinate of the structure
     * @param width the width of the structure
     * @param height the height of the structure
     * @param maxHP the maximum hit points of the structure
     */
    public Structure(int x, int y, int width, int height, int maxHP, GameData.ID id, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.width = width * gamePanel.SCALE_IMAGE;
        this.height = height * gamePanel.SCALE_IMAGE;
        this.maxHP = maxHP;
        this.curHP = maxHP;
        this.id = id;
    }

    /**
     * Set up the image based on image path      *
      * @param imagePath the path to the image file to be loaded for this structure
     * */
    public void setImage(String imagePath) {
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drops one coin at the center of the structure when it is destroyed
      * @return an Item object representing the dropped coin, positioned at the center of the structure
     * */
    public Item rewardCoin() {
        int centerX = (int) (x + width / 2);
        Item coin = new Item(centerX, y, 20, 20, 6, GameData.ID.COIN_ID, gamePanel);
        return coin;
    }
    
    public void render(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height, null);
    }
}
