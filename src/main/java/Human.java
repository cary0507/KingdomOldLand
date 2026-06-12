import java.util.Random;

public class Human extends Entity {
    public GameData.JobID id;
    public Structure habitat;
    public MoneyBag moneyBag;
    public PickedItem equipping;
    public int maxWanderFrame = 2 * GamePanel.FPS;  // 2 seconds max
    public int wanderFrame;
    public int wanderChance = 500;  // Higher = less likely

    /**
     * Initializes the entity with its position, hitbox dimensions, and movement parameters.
     * Humans are spawned as unemployed fugitives
     */
    public Human(int x, int y, double maxSpeed, Structure habitat,
                 GamePanel gamePanel) {
        super(x, y, maxSpeed, gamePanel);
        this.id = GameData.JobID.FUGITIVE;
        this.habitat = habitat;
        wanderFrame = 0;
        moneyBag = new MoneyBag(1, x, y, gamePanel);
        setImagesFromPaths(GameData.humanImgL,  GameData.humanImgR);
    }

    public void wander() {
        if (wanderFrame <= 0) return;
        if (isFacingLeft) {
            x -= 1;
        } else {
            x += 1;
        }
    }

    public void goHme() {
        if (this.habitat == null) {
            return;
        }
        if (GameData.getDist(this, this.habitat) <= this.maxSpeed) {  // Anchors, if their distance is close enough
            GameData.setCenterX(this, GameData.getCenterX(this.habitat));
        } else if (GameData.getCenterX(this) < GameData.getCenterX(this.habitat)) {
            this.isFacingLeft = false;
            this.x += (int) this.maxSpeed;
        } else if (GameData.getCenterX(this) > GameData.getCenterX(this.habitat)) {
            this.isFacingLeft = true;
            this.x -= (int) this.maxSpeed;
        }
    }

    public void update(boolean isNight) {
        Random rand = new Random();
        imgIndex = id.ordinal();
        // Different job behaves differently
        switch(id) {
            case FUGITIVE:
                moneyBag.capacity = 1;
                if (moneyBag.numCoins >= moneyBag.capacity) {  // Get hired and exhaust a coin
                    this.moneyBag.exhaustCoin();
                    this.id = GameData.JobID.VILLAGER;
                }
                break;
            case VILLAGER:
                moneyBag.capacity = 2;
                wanderChance = 1000;
                break;
            case FARMER:
                moneyBag.capacity = 9;
                break;
            case ARCHER:
                moneyBag.capacity = 11;
                break;
        }
        // Day & night activities
        if (isNight) {
            goHme();  // Head back to habitat during night time
        } else {
            int rollWander = rand.nextInt(wanderChance);
            if (rollWander == 67) {
                int randDir = rand.nextInt(2);
                if (randDir == 0) {
                    isFacingLeft = true;
                } else {
                    isFacingLeft = false;
                }
                wanderFrame = rand.nextInt(maxWanderFrame);
            }
            if (wanderFrame > 0) {
                wanderFrame--;
            }
            wander();
        }
    }
}
