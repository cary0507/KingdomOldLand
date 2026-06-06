import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MoneyBag implements Serializable {
    private final ArrayList<Item> coins;
    private final int DROP_CHANCE = 2;
    public final int capacity;
    public BufferedImage image;
    public int dropX;
    public int dropY;

    /**
     * Initializes the money bag with a specified capacity and an empty list of coins.
     *
     * @param capacity the maximum number of coins the money bag can hold
     * */
    public MoneyBag(int capacity, int dropX, int dropY) {
        this.capacity = capacity;
        this.coins = new ArrayList<Item>(capacity);
        this.dropX = dropX;
        this.dropY = dropY;
    }

    /**
     * Sets the image of the money bag based on the provided file path.
     *
     * @param path the image path
     * */
    public void setImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a coin to the bag and has a 50% chance to drop it to the water when reached max capacity
     *
     * @param coin the coin to be added
     * */
    public void addCoin(Item coin) {
        if (coin.id == GameData.ID.COIN_ID) {
            coins.add(coin);
            if (coins.size() > capacity) {
                // 1/2 = 50% chance
                int rand = (int) (Math.random() * (DROP_CHANCE) + 1);
                if (rand == 1) {
                    Item droppedCoin = tossCoin();
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
    public Item tossCoin() {
        if (!coins.isEmpty()) {
            Item tossedCoin = coins.remove(coins.size() - 1);
            tossedCoin.hasPicked = false;
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
