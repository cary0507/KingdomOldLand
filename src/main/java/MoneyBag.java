import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

public class MoneyBag implements Serializable {
    private final ArrayList<PickedItem> coins;
    public final int capacity;
    public String imagePath;
    public int dropX;
    public int dropY;
    public GamePanel gamePanel;

    /**
     * Initializes the money bag with a specified capacity and an empty list of coins.
     *
     * @param capacity the maximum number of coins the money bag can hold
     * */
    public MoneyBag(int capacity, int dropX, int dropY, GamePanel gamePanel) {
        this.capacity = capacity;
        this.coins = new ArrayList<>(capacity);
        this.dropX = dropX;
        this.dropY = dropY;
        this.gamePanel = gamePanel;
    }

    /**
     * Adds a coin to the bag and has a 50% chance to drop it to the water when reached max capacity
     *
     * @param coin the coin to be added
     * */
    public void addCoin(Projectile coin) {
        final int DROP_CHANCE = 2;  // 1/2 = 50% chance
        if (coin.data.getId() == GameData.ItemID.COIN) {
            coins.add(coin.getPicked(coin.data));
            if (coins.size() > capacity) {
                int rand = (int) (Math.random() * (DROP_CHANCE) + 1);
                if (rand == 1) {
                    Projectile droppedCoin = tossCoin();
                    droppedCoin.isOutOfBound = true;
                }
            }
        }
    }

    /**
     * Toss one coin from the bag
     *
     * @return the coin that is removed
     * */
    public Projectile tossCoin() {
        if (!coins.isEmpty()) {
            PickedItem selectCoin = coins.remove(coins.size() - 1);
            // Determines the dimensions of the projectile from the image
            BufferedImage[] img = GameData.pathsToImages(selectCoin.data.thrownImgPath);
            Projectile tossedCoin = selectCoin.toss(
                    dropX, dropY, img[0].getWidth(), img[0].getHeight(),
                    GameData.UNIVERSAL_TOP_SPEED, gamePanel
            );
            tossedCoin.x = dropX;
            tossedCoin.y = dropY;
            tossedCoin.velX = 0;
            tossedCoin.velY = -5;                   // Toss the coin upwards
            tossedCoin.accX = 0;
            tossedCoin.accY = GameData.GRAVITY;     // Apply gravity to the tossed
            return tossedCoin;
        }
        return null;  // No coins to toss
    }
}
