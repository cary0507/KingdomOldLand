public class Wall extends Structure {
    public int level;
    public final String[] LEVEL_IMG_PATH;
    public int princeLvlUp;

    public Wall(int x, int y, String[] levelImgPath) {
        super(x, y, 0, 0, 0, GameData.ID.WALL_ID, levelImgPath[0]);
        this.LEVEL_IMG_PATH = levelImgPath;
        this.level = 0;
        this.princeLvlUp = 1;
    }

    public void levelUp() {
        if (this.level >= LEVEL_IMG_PATH.length) {
            return;
        }
        this.level++;
        this.princeLvlUp *= 3;
        this.maxHP = 5 + 5 * this.level;
        this.curHP = this.maxHP;
        this.width = 10;
        this.height = 5 + 10 * (this.level  - 1);
        this.imagePath = this.LEVEL_IMG_PATH[this.level];
    }
}
