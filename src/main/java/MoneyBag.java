import java.io.Serializable;
import java.util.ArrayList;

public class MoneyBag implements Serializable {
    private final ArrayList<Item> coins;
    public final int capacity;
    public String imagePath;

    public MoneyBag(int capacity, String imagePath) {
        this.capacity = capacity;
        this.coins = new ArrayList<Item>(capacity);
        this.imagePath = imagePath;
    }

    public void addCoin(Item coin) {
        if (coin.id == GameData.ID.COIN_ID) {
            coins.add(coin);
        }
    }

    public Item tossCoin() {
        if (!coins.isEmpty()) {
            Item coin = coins.remove(coins.size() - 1);
            coin.hasPicked = false;
            return coin;
        }
        return null;  // No coins to toss
    }
}
