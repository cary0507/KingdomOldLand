import java.util.Random;

public class Human extends Entity {
    public GameData.JobID id;
    public Structure habitat;
    public final MoneyBag MONEY_BAG;
    public final int MAX_WANDER_FRAME = 3 * GamePanel.FPS;  // 2 seconds max
    public int wanderFrame;
    public int wanderChance = 500;  // Higher = less likely
    public final int SHOOT_CD;
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
        MONEY_BAG = new MoneyBag(1, x, y, gamePanel);
        setImagesFromPaths(GameData.HUMAN_IMG_L,  GameData.HUMAN_IMG_R);
        SHOOT_CD = GamePanel.FPS;  // 1 second cooldown
        curShootCD = SHOOT_CD;
        inSearch = false;
        hp = 1;
    }

    /**
     * Randomly work around in one direction for a period of time
     * */
    public void wander() {
        if (wanderFrame <= 0) return;
        int speed = (int) (maxSpeed * 0.3);
        if (isFacingLeft) {
            x -= speed;
        } else {
            x += speed;
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
                GameData.ItemID.ARROW, GameData.ARROW_IMG_L, GameData.ARROW_IMG_R, false
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
        MONEY_BAG.dropX = x;
        MONEY_BAG.dropY = y;
        // Different job behaves differently
        switch(id) {
            case FUGITIVE:
                MONEY_BAG.capacity = 1;
                if (MONEY_BAG.numCoins >= MONEY_BAG.capacity) {  // Get hired and exhaust a coin
                    this.MONEY_BAG.tossCoin("none");
                    this.id = GameData.JobID.VILLAGER;
                    // Upgrade max speed
                    maxSpeed = GameData.NPC_TOP_SPEED;
                }
                break;
            case VILLAGER:
                MONEY_BAG.capacity = 2;
                wanderChance = 1000;
                break;
            case FARMER:
                MONEY_BAG.capacity = 9;
                break;
            case ARCHER:
                MONEY_BAG.capacity = 11;
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
                wanderFrame = rand.nextInt(MAX_WANDER_FRAME);
            }
            if (wanderFrame > 0) {
                wanderFrame--;
            }
            wander();
        }
    }
}
