import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class Entity implements Serializable {
    public final double MAX_SPEED;
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
    public int animeDuration;       // How many frames before switching to the next animation of image
    public int passedFrame;         // Frame passed
    public transient BufferedImage[] leftImages;
    public transient BufferedImage[] rightImages;
    // Store image paths for serialization/deserialization
    public String[] leftImagePaths;
    public String[] rightImagePaths;
    // Environment
    public transient GamePanel gamePanel;

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * @param x the initial x-coordinate of the entity
     * @param y the initial y-coordinate of the entity
     * @param maxSpeed the maximum speed the entity can reach
     * */
    public Entity(int x, int y, double maxSpeed, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.MAX_SPEED = maxSpeed;
        imgIndex = 0;
        passedFrame = 0;
    }

    /**
     * Load images from file paths and populate leftImages and rightImages arrays.
     * If any image fails to load it will be skipped.
     *
     * @param leftImagePaths  paths to left-facing images
     * @param rightImagePaths paths to right-facing images
     */
    public void setImagesFromPaths(String[] leftImagePaths, String[] rightImagePaths) {
        // Store the paths for deserialization
        this.leftImagePaths = leftImagePaths;
        this.rightImagePaths = rightImagePaths;
        
        // Load the actual images
        leftImages = GameData.pathsToImages(leftImagePaths);
        rightImages = GameData.pathsToImages(rightImagePaths);
        // set hitbox size from the first available image
        BufferedImage baseImg = null;
        if (leftImages != null && leftImages.length > 0) {
            baseImg = leftImages[0];
        } else if (rightImages != null && rightImages.length > 0) {
            baseImg = rightImages[0];
        }
        if (baseImg != null) {
            // Get the dimensions
            this.hitboxWidth = baseImg.getWidth() * GamePanel.SCALE_PIXEL;
            this.hitboxHeight = baseImg.getHeight() * GamePanel.SCALE_PIXEL;
        }
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
        // set hitbox size from the first available image
        BufferedImage baseImg = null;
        if (this.leftImages != null && this.leftImages.length > 0) {
            baseImg = this.leftImages[0];
        } else if (this.rightImages != null && this.rightImages.length > 0) {
            baseImg = this.rightImages[0];
        }
        if (baseImg != null) {
            this.hitboxWidth = baseImg.getWidth() * GamePanel.SCALE_PIXEL;
            this.hitboxHeight = baseImg.getHeight() * GamePanel.SCALE_PIXEL;
        }
    }

    /**
     * Returns a duplicate of the entity with the same position, hitbox dimensions, movement parameters, and images.
     * */
    public Entity duplicate() {
        Entity duplicate = new Entity(x, y, MAX_SPEED, gamePanel);
        duplicate.setImages(leftImages, rightImages);
        return duplicate;
    }

    /**
     * Updates the in-game behavior (Movement, animation, etc.) of the entity.
     * */
    public void update() {
        if (this.leftImages == null || this.rightImages == null) {
            return;
        }
        if (this.leftImages.length == 0 || this.rightImages.length == 0) {
            return;
        }
        passedFrame++;
        int maxIndex;;
        if (isFacingLeft) {
            animeDuration = (int) (GamePanel.FPS / leftImages.length);
            maxIndex = leftImages.length - 1;
        } else {
            animeDuration = (int) (GamePanel.FPS / rightImages.length);
            maxIndex = rightImages.length - 1;
        }
        if (passedFrame >= animeDuration) {
            passedFrame = 0;
            imgIndex++;
            if (imgIndex >= maxIndex) {
                imgIndex = 0;
            }
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
        if (leftImages == null || rightImages == null) {
            return;
        }
        if (leftImages.length == 0 || rightImages.length == 0) {
            return;
        }
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
        // Renders item holding, if has any
        renderItem(g2d, referenceCam);
    }

    /**
     * Custom deserialization to restore transient BufferedImage arrays from stored paths
     * */
    private void readObject(ObjectInputStream objIn) throws IOException, ClassNotFoundException {
        objIn.defaultReadObject();
        // Reload images from stored paths
        if (leftImagePaths != null || rightImagePaths != null) {
            leftImages = GameData.pathsToImages(leftImagePaths);
            rightImages = GameData.pathsToImages(rightImagePaths);
        }
    }

    /**
     * Custom serialization handler
     * */
    private void writeObject(ObjectOutputStream objOut) throws IOException {
        objOut.defaultWriteObject();
    }
}
