import java.io.Serializable;

/**
 * The very fundamental data structure for item appearing as either a PickedItem or a Projectile
 * */
public class PickedItem implements Serializable {
    public final ItemData data;
    public int imgIndex;

    public PickedItem(ItemData data) {
        this.data = data;
        this.imgIndex = 0;
    }

    public Projectile toss(int x, int y, double maxSpeed, GamePanel gamePanel) {
        Projectile projectile =  new Projectile(x, y, maxSpeed, gamePanel);
        return projectile;
    }
}
