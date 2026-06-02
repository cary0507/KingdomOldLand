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
    public String imagePath;

    /**
     * Initializes the structure with its position, dimensions, hit points, and image path.
     *
     * @param x the x-coordinate of the structure
     * @param y the y-coordinate of the structure
     * @param height the height of the structure
     * @param width the width of the structure
     * @param maxHP the maximum hit points of the structure
     * */
    public Structure(int x, int y, int height, int width, int maxHP, GameData.ID id, String imagePath) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.maxHP = maxHP;
        this.curHP = maxHP;
        this.id = id;
        this.imagePath = imagePath;
    }
}
