import java.util.ArrayList;

public class Portal extends Structure {
    public int numEnemy;  // Number of enemies will generate
    public final int NUM_NPC;    // Number of NPC will generate

    public Portal(int x, int y, GamePanel gamePanel, boolean isFacingLeft) {
        super(x, y, 0, GameData.StructureID.PORTAL, gamePanel);
        setImagesFromPaths(GameData.PORTAL_IMG_L, GameData.PORTAL_IMG_R);
        this.isFacingLeft = isFacingLeft;
        numEnemy = 1;
        NUM_NPC = 2;
    }

    /**
     * Generates a specific number of enemies
     * */
    public ArrayList<Enemy> generateEnemies() {
        ArrayList<Enemy> genEnemies = new ArrayList<>();
        for (int i = 0; i < numEnemy; i++) {
            Enemy enemy = new Enemy(this.x, GamePanel.HORIZON, GameData.ENEMY_TOP_SPEED, this.gamePanel);
            enemy.isFacingLeft = this.isFacingLeft;
            enemy.setBaseStats(1, 1, 4 * GamePanel.FPS);
            enemy.y -= enemy.hitboxHeight;
            genEnemies.add(enemy);
        }
        numEnemy += 2;  // Enhance
        return genEnemies;
    }

    /**
     * Generates a specific number of NPC
     * */
    public ArrayList<Human> generateNPC(Structure habitat) {
        ArrayList<Human> genNPC = new ArrayList<>();
        for (int i = 0; i < NUM_NPC; i++) {
            Human human = new Human(x, GamePanel.HORIZON, GameData.VAGRANT_TOP_SPEED, habitat, gamePanel);
            human.isFacingLeft = this.isFacingLeft;
            human.y -= human.hitboxHeight;
            genNPC.add(human);
        }
        return genNPC;
    }
}
