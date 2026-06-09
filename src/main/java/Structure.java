import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Structure extends Entity {
    public int maxHP;
    public int curHP;
    public GameData.ID id;
    public GameData.ID[] blockedID;
    public BufferedImage image;

    /**
     * Initializes the structure with its position, dimensions, hit points, and image path.
     *
     * @param x the x-coordinate of the structure
     * @param y the y-coordinate of the structure
     * @param imageWidth the width of the structure
     * @param imageHeight the height of the structure
     * @param maxHP the maximum hit points of the structure
     */
    public Structure(int x, int y, int imageWidth, int imageHeight, int maxHP, GameData.ID id, GamePanel gamePanel) {
        super(x, y, imageWidth, imageHeight, 0, gamePanel);
        this.gamePanel = gamePanel;
        this.maxHP = maxHP;
        this.curHP = maxHP;
        this.id = id;
    }

    public void setBlockedID(GameData.ID[] blockedID) {
        this.blockedID = blockedID;
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
    public PickedItem rewardCoin() {
        int centerX = (int) (x + hitboxWidth / 2);
        PickedItem coin = new PickedItem(centerX, y, 20, 20, 6, GameData.ID.COIN_ID, gamePanel);
        return coin;
    }

    @Override
    public void render(Graphics2D g2, Camera referenceCam) {
        int screenX = referenceCam.convertX(this.x);
        int screenY = referenceCam.convertY(this.y);
        g2.drawImage(image, x, y, screenX, screenY, null);
    }
}
