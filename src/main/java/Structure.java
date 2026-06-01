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
    public ArrayList<Item> containedItems;

    public Structure(int x, int y, int height, int width, int maxHP) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.maxHP = maxHP;
        this.curHP = maxHP;
    }
}
