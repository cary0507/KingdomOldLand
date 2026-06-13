import java.util.Random;

public class Human extends Entity {
    public GameData.JobID id;
    public Structure habitat;
    public MoneyBag moneyBag;
    public PickedItem equipping;
    public int maxWanderFrame = 2 * GamePanel.FPS;  // 2 seconds max
    public int wanderFrame;
    public int wanderChance = 500;  // Higher = less likely
    public int shootCD;
    public int curShootCD;  // Frames until able to shoot
    public boolean inSearch;  // If NPC is looking for something
    public int hp;

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
        shootCD = GamePanel.FPS;  // 1 second cooldown
        curShootCD = shootCD;
        inSearch = false;
        hp = 1;
    }

    /**
     * Randomly work around in one direction for a period of time
     * */
    public void wander() {
        if (wanderFrame <= 0) return;
        if (isFacingLeft) {
            x -= 1;
        } else {
            x += 1;
        }
    }

    /**
     * Run to the destination
     * */
    public void goToDestination() {
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

    /**
     * Shoot an arrow at the target
     * */
    public Projectile shoot(Entity target) {
        if (curShootCD > 0) return null;
        if (target == null) return null;
        if (id != GameData.JobID.ARCHER) return null;  // Only archer is able to shoot
        Random rand = new Random();
        double facing;
        // Get facing
        if (x > target.x) {
            isFacingLeft = true;
            facing = rand.nextInt(-135 - (-165) + 1) - 165;
        } else {
            isFacingLeft = false;
            facing = rand.nextInt(-15 - (-45) + 1) - 45;
        }
        ItemData arrowData = new ItemData(
                GameData.ItemID.ARROW, GameData.arrowImgL, GameData.arrowImgR, false
        );
        Projectile arrow = new Projectile(
                GameData.getCenterX(this), y + hitboxHeight / 2,
                40, gamePanel, arrowData
        );
        arrow.setMotionValues(0, 0, 0, GameData.GRAVITY, 1, false);
        arrow.setVelFromDir(Math.toRadians(facing), arrow.maxSpeed);
        arrow.isFacingLeft = isFacingLeft;
        return arrow;
    }

    public void update(boolean isNight) {
        Random rand = new Random();
        imgIndex = id.ordinal();
        // Update money bag
        moneyBag.dropX = x;
        moneyBag.dropY = y;
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
                if (curShootCD > 0) {
                    curShootCD--;
                }
                break;
        }
        // Day & night activities
        if (isNight || inSearch) {
            goToDestination();  // Head back to habitat during nighttime
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
