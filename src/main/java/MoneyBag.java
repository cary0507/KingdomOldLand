import java.io.Serializable;
import java.util.ArrayList;

public class MoneyBag implements Serializable {
    private ArrayList<Item> coins;
    private ArrayList<Item> items;

    public void addCoin(Item coin) {
        if (coin.id == GameData.ID.COIN_ID) {
            coins.add(coin);
        }
    }

    public Item tossCoin() {
        if (!coins.isEmpty()) {
            return coins.remove(coins.size() - 1);
        }
        return null;  // No coins to toss
    }
}
