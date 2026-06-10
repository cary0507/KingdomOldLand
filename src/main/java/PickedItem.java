import java.io.Serializable;

public class PickedItem implements Serializable {
    public final ItemData data;
    public int imgIndex;

    public PickedItem(ItemData data) {
        this.data = data;
        this.imgIndex = 0;
    }

    public Projectile toss(int x, int y, int rawHitboxWidth, int rawHitboxHeight,
                           double maxSpeed, GamePanel gamePanel) {
        Projectile projectile =  new Projectile(x, y, rawHitboxWidth, rawHitboxHeight, maxSpeed, gamePanel);
        return projectile;
    }
}
