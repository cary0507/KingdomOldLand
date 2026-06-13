import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ContainerStruct extends Structure {
    public Projectile[] containing;
    public int[][] relativePos;  // Stored x and y positions to anchor entities to the structure
    transient BufferedImage payImg;
    public int numItems;

    /**
     * Initializes the structure with its position, hitbox dimensions, health points, and the entities it contains.
     * */
    public ContainerStruct(int x, int y, int maxHP, GameData.StructureID id, int[][] relativePos, GamePanel gamePanel) {
        super(x, y, maxHP, id, gamePanel);
        this.relativePos = relativePos;
        if (relativePos != null) {
            this.containing = new Projectile[relativePos.length];
        } else {
            this.containing = null;
        }
        payImg = GameData.pathToImage(GameData.PAY_HINT[0]);
        numItems = 0;
    }

    /**
     * Adds an Projectile to an empty slot of "containing"
     * */
    public void addItem(Projectile entity) {
        // Loop through the array to find an empty spot
        for (int i = 0; i < containing.length; i++) {
            if (containing[i] == null) {
                containing[i] = entity;
                return;
            }
        }
    }

    /**
     * Anchors the contained ordered entities to the structure by updating their positions based on the structure's
     * current position and their relative positions.
     * Precondition: none
     * Postcondition: anchors the entities to the respective coordinates, or do nothing
     * */
    public void anchorEntities() {
        if (containing == null || relativePos == null) {
            return;
        }
        numItems = 0;
        int x = 0, y = 1;  // Too lazy to create a Coordinate class

        for (int i = 0; i < containing.length; i++) {
            if (containing[i] != null) {
                containing[i].x = this.x + relativePos[i][x] * GamePanel.SCALE_PIXEL;
                containing[i].y = this.y + relativePos[i][y] * GamePanel.SCALE_PIXEL;
                numItems++;
            }
        }
    }

    /**
     * Take away the Projectile at the specified index from the structure, returning a duplicate of it and setting the
     * original reference to null.
     *
     * @param index the index of the entity to be taken away from the structure
     * */
    public Projectile takeAway(int index) {
        Projectile taken = this.containing[index].duplicate();
        containing[index] = null;
        return taken;
    }

    @Override
    public void update() {
        anchorEntities();
    }

    @Override
    public void render(Graphics2D g2d, Camera cam) {
        super.render(g2d, cam);
        // Draw each item, if has any
        if (containing != null) {
            for (int i = 0; i < containing.length; i++) {
                if (containing[i] != null) {
                    containing[i].render(g2d, cam);
                }
            }
        }
    }
    /**
     * Renders tip to hint player to pay a coin
     * */
    public void renderHint(Graphics2D g2d, Camera cam) {
        int hintWidth = payImg.getWidth() * GamePanel.SCALE_PIXEL;
        int hintHeight = payImg.getHeight() * GamePanel.SCALE_PIXEL;
        int imgX = x + hitboxWidth / 2 - hintWidth / 2;
        int imgY = y - hintHeight;
        int xOnScreen = cam.convertX(imgX);
        int yOnScreen = cam.convertY(imgY);
        g2d.drawImage(
                payImg, xOnScreen, yOnScreen, hintWidth, hintHeight, null);
    }

    /**
     * Custom deserialization to restore transient BufferedImage
     * */
    private void readObject(ObjectInputStream objIn) throws IOException, ClassNotFoundException {
        objIn.defaultReadObject();
        // Reload payImg from data
        payImg = GameData.pathToImage(GameData.PAY_HINT[0]);
    }

    /**
     * Custom serialization handler
     * */
    private void writeObject(ObjectOutputStream objOut) throws IOException {
        objOut.defaultWriteObject();
    }
}
