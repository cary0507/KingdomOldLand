import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * <a href="https://www.desmos.com/calculator/yj0hqvmoor">Experiment by myself</a>
 * */
public class Orbits implements Serializable {
    private static final long serialVersionUID = 1L;
    int x, y;  // The left top corner of the orbiting object
    int width, height;
    int centerX, centerY;  // Orbit's location
    transient BufferedImage image;
    String imagePath;  // Store path for serialization/deserialization
    final int TRACK_WIDTH, TRACK_HEIGHT;
    final int TRACK_CENTER_X, TRACK_CENTER_Y;  // Trajectory's location
    final int HALF_PERIOD;  // Frames it takes to complete half a period

    public Orbits(int trackWidth, int trackHeight, int trackCenterX, int trackCenterY, int halfTrackTime) {
        this.TRACK_WIDTH = trackWidth;
        this.TRACK_HEIGHT = trackHeight;
        this.TRACK_CENTER_X = trackCenterX;
        this.TRACK_CENTER_Y = trackCenterY;
        this.HALF_PERIOD = halfTrackTime;
    }

    public void setImageFromPath(String imgPath) {
        this.imagePath = imgPath;  // Store path for serialization
        image = GameData.pathToImage(imgPath);
        width = image.getWidth() * GamePanel.SCALE_PIXEL;
        height = image.getHeight() * GamePanel.SCALE_PIXEL;
    }

    /**
     * Uses trig functions to calculate the x and y values of the object
     * Formula: f(x)=amplitude * sin(k(x-p))+q
     * */
    public void update(int passedFrame) {
        double kVal = Math.PI / HALF_PERIOD;  // Calculates k value
        int amplitudeWidth = TRACK_WIDTH / 2;
        int amplitudeHeight = TRACK_HEIGHT / 2;
        centerX = (int) Math.floor(
                -amplitudeWidth * Math.cos(kVal * passedFrame) + TRACK_CENTER_X
        );
        centerY = (int) Math.floor(  // Windows y-axis is reflected, therefore - amplitude
                -amplitudeHeight * Math.sin(kVal * passedFrame) + TRACK_CENTER_Y
        );
    }

    /**
     * Draws the orbiting object on screen with absolute location
     * */
    public void render(Graphics2D g2d) {
        if (image == null) {
            return;
        }
        x = centerX - width / 2;
        y = centerY - height / 2;
        g2d.drawImage(image, x, y, width, height, null);
    }

    /**
     * Draws the orbiting object as an oval shape
     * */
    public void render(Graphics2D g2d, int width, int height, Color color) {
        g2d.setColor(color);
        x = centerX - width / 2;
        y = centerY - height / 2;
        g2d.fillOval(x, y, width, height);
    }

    /**
     * Custom deserialization to restore transient BufferedImage from stored path
     * */
    private void readObject(ObjectInputStream objIn) throws IOException, ClassNotFoundException {
        objIn.defaultReadObject();
        // Reload image from stored path
        if (imagePath != null) {
            image = GameData.pathToImage(imagePath);
        }
    }

    /**
     * Custom serialization handler
     * */
    private void writeObject(ObjectOutputStream objOut) throws IOException {
        objOut.defaultWriteObject();
    }
}
