import java.util.ArrayList;

public class MoneyBag {
    private ArrayList<Item> coins;

    public void addCoin(Item coin) {
        if (coin.getName().equals("coin")) {
            this.coins.add(coin);
        }
    }

    public Item tossCoin() {
        if (!this.coins.isEmpty()) {
            return this.coins.remove(this.coins.size() - 1);
        }
        return null;  // No coins to toss
    }
}
