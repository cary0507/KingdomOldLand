import java.io.Serializable;

public class Camera implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;

    public Camera(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}
