import java.io.Serializable;
import java.util.ArrayList;

public class Structure implements Serializable {
    // Structures will not move
    private final int x;
    private final int y;
    private final int height;
    private final int width;
    public final int maxHP;
    public int curHP;
    public GameData.ID id;

    /**
     * Initializes the structure with its position, dimensions, hit points, and image path.
     *
     * @param x the x-coordinate of the structure
     * @param y the y-coordinate of the structure
     * @param height the height of the structure
     * @param width the width of the structure
     * @param maxHP the maximum hit points of the structure
     * */
    public Structure(int x, int y, int height, int width, int maxHP, GameData.ID idh) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.maxHP = maxHP;
        this.curHP = maxHP;
        this.id = id;
    }
}
