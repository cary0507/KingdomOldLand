public class Player {
    private MoneyBag moneyBag;
    private Mountable mount;
    private Item crown;

    public Player(MoneyBag moneyBag, Mountable mount) {
        this.moneyBag = moneyBag;
        this.mount = mount;
        this.crown = new Item(0, 0, 10, 10, 0, 0);
    }
}
