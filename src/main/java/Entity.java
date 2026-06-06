import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;

public class Entity implements Serializable {
    public final double maxSpeed;
    // The pixels on the screen has to be a whole number, hence the int type.
    public int x;
    public int y;
    public final int hitboxWidth;
    public final int hitboxHeight;
    // Stores image files
    public boolean isFacingLeft;    // Since it's a 2D game and there's only left and right
                                    // This value only affects which image to use, not the actual movement direction
    public int imgIndex;            // Determines which image to use for animation
    public BufferedImage[] leftImages;
    public BufferedImage[] rightImages;
    // Environment
    public GamePanel gamePanel;

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * @param x the initial x-coordinate of the entity
     * @param y the initial y-coordinate of the entity
     * @param imageWidth the width of the entity's image
     * @param imageHeight the height of the entity's image
     * @param maxSpeed the maximum speed the entity can reach
     * */
    public Entity(int x, int y, int imageWidth, int imageHeight, double maxSpeed, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.x = x;
        this.y = y;
        this.hitboxWidth = imageWidth * gamePanel.SCALE_IMAGE;  // Scale up the image
        this.hitboxHeight = imageHeight * gamePanel.SCALE_IMAGE;
        this.maxSpeed = maxSpeed;
        imgIndex = 0;
    }

    /**
     * Converts String paths to image array
     *
     * @param paths the array of image paths to load
     * @return an array of BufferedImages loaded from the provided paths
     * */
    public BufferedImage[] pathsToImages(String[] paths) {
        if (paths == null) {
            return new BufferedImage[0];
        }

        // Count valid paths
        int imageCount = 0;
        for (String path : paths) {
            if (path != null) {
                try {
                    BufferedImage img = ImageIO.read(getClass().getResourceAsStream(path));
                    if (img != null) {
                        imageCount++;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Store valid images
        BufferedImage[] images = new BufferedImage[imageCount];
        int index = 0;
        for (String path : paths) {
            if (path != null) {
                try {
                    BufferedImage img = ImageIO.read(getClass().getResourceAsStream(path));
                    if (img != null) {
                        images[index++] = img;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return images;
    }

    /**
     * Load images from file paths and populate leftImages and rightImages arrays.
     * If any image fails to load it will be skipped.
     *
     * @param leftImagePaths  paths to left-facing images
     * @param rightImagePaths paths to right-facing images
     */
    public void setImagesFromPaths(String[] leftImagePaths, String[] rightImagePaths) {
        leftImages = pathsToImages(leftImagePaths);
        rightImages = pathsToImages(rightImagePaths);
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
    private void setImages(BufferedImage[] leftImages, BufferedImage[] rightImages) {
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

    }

    /**
     * Renders the entity on the screen using the appropriate image based on its facing direction and scale factor
     *
     * @param g2 the Graphics2D object used for drawing the entity on the screen
     * */
    public void render(Graphics2D g2) {
        BufferedImage img;
        if (isFacingLeft) {
            img = leftImages[imgIndex];
        } else {
            img = rightImages[imgIndex];
        }
        g2.drawImage(img, x, y, hitboxWidth, hitboxHeight, null);
    }
}
