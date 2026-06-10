import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Entity implements Serializable {
    public final double maxSpeed;
    // The pixels on the screen has to be a whole number, hence the int type.
    public int x;
    public int y;
    public int hitboxWidth;
    public int hitboxHeight;
    public PickedItem holdingItem;
    // Stores image files
    public boolean isFacingLeft;    // Since it's a 2D game and there's only left and right
                                    // This value only affects which image to use, not the actual movement direction
    public int imgIndex;            // Determines which image to use for animation
    private long animeDuration;     // How long before switching to the next animation of image in nanoseconds
    public BufferedImage[] leftImages;
    public BufferedImage[] rightImages;
    // Environment
    public GamePanel gamePanel;

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * @param x the initial x-coordinate of the entity
     * @param y the initial y-coordinate of the entity
     * @param rawHitboxWidth the width in tiles of the entity's image
     * @param rawHitboxHeight the height in tiles of the entity's image
     * @param maxSpeed the maximum speed the entity can reach
     * */
    public Entity(int x, int y, int rawHitboxWidth, int rawHitboxHeight, double maxSpeed, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.hitboxWidth = rawHitboxWidth * GamePanel.SCALE_PIXEL;  // Scale up the image
        this.hitboxHeight = rawHitboxHeight * GamePanel.SCALE_PIXEL;
        this.maxSpeed = maxSpeed;
        imgIndex = 0;
    }

    /**
     * Load images from file paths and populate leftImages and rightImages arrays.
     * If any image fails to load it will be skipped.
     *
     * @param leftImagePaths  paths to left-facing images
     * @param rightImagePaths paths to right-facing images
     */
    public void setImagesFromPaths(String[] leftImagePaths, String[] rightImagePaths) {
        leftImages = GameData.pathsToImages(leftImagePaths);
        rightImages = GameData.pathsToImages(rightImagePaths);
        // ensure imgIndex is valid
        if (imgIndex < 0) {
            imgIndex = 0;
        }
        if (isFacingLeft && this.leftImages.length == 0) {
            imgIndex = 0;
        }
        if (!isFacingLeft && this.rightImages.length == 0) {
            imgIndex = 0;
        }
    }

    /**
     * Set images from arrays of BufferedImage
     *
     * @param leftImages the images when facing left
     * @param rightImages the images when facing right
     * */
    public void setImages(BufferedImage[] leftImages, BufferedImage[] rightImages) {
        this.leftImages = leftImages;
        this.rightImages = rightImages;
    }

    /**
     * Returns a duplicate of the entity with the same position, hitbox dimensions, movement parameters, and images.
     * */
    public Entity duplicate() {
        Entity duplicate = new Entity(x, y, hitboxWidth, hitboxHeight, maxSpeed, gamePanel);
        duplicate.setImages(leftImages, rightImages);
        return duplicate;
    }

    /**
     * Updates the in-game behavior (Movement, animation, etc.) of the entity.
     * */
    public void update() {
        if (isFacingLeft && imgIndex < leftImages.length - 1) {
            imgIndex++;
        } else if (!isFacingLeft && imgIndex < rightImages.length - 1) {
            imgIndex++;
        } else {
            imgIndex = 0;
        }
    }

    /**
     * Renders the holding item
     * */
    public void renderItem(Graphics2D g2d, Camera referenceCam) {
        if (holdingItem == null) {  // Prevents null pointer exception if the owner is not holding anything
            return;
        }
        String path;
        int itemX, itemY;
        // Resolve the facing
        if (isFacingLeft) {
            path = holdingItem.data.itemIconPathL[holdingItem.imgIndex];
            itemX = x + holdingItem.data.iconXOffsetL;
            itemY = y + holdingItem.data.iconYOffsetL;
        } else {
            path = holdingItem.data.itemIconPathR[holdingItem.imgIndex];
            itemX = x + holdingItem.data.iconXOffsetR;
            itemY = y + holdingItem.data.iconYOffsetR;
        }
        BufferedImage img = GameData.pathToImage(path);
        if (img == null) {
            return;
        }
        int screenX = referenceCam.convertX(itemX);
        int screenY = referenceCam.convertY(itemY);
        int onScreenWidth = img.getWidth() * GamePanel.SCALE_PIXEL;
        int onScreenHeight = img.getHeight() * GamePanel.SCALE_PIXEL;
        g2d.drawImage(img, screenX, screenY, onScreenWidth, onScreenHeight, null);
    }

    /**
     * Renders the entity on the screen using the appropriate image based on its facing direction and scale factor
     *
     * @param g2d the Graphics2D object used for drawing the entity on the screen
     * */
    public void render(Graphics2D g2d, Camera referenceCam) {
        BufferedImage img;
        // Check the facing
        if (isFacingLeft) {
            img = leftImages[imgIndex];
        } else {
            img = rightImages[imgIndex];
        }
        int screenX = referenceCam.convertX(this.x);
        int screenY = referenceCam.convertY(this.y);
        int onScreenWidth = img.getWidth() * GamePanel.SCALE_PIXEL;
        int onScreenHeight = img.getHeight() * GamePanel.SCALE_PIXEL;
        g2d.drawImage(img, screenX, screenY, onScreenWidth, onScreenHeight, null);
    }
}
