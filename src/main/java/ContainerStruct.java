public class ContainerStruct extends Structure {
    public Entity[] containing;
    public int[][] relativePos;

    public ContainerStruct(int x, int y, int width, int height, int maxHP, GameData.ID id, int[][] relativePos) {
        super(x, y, width, height, maxHP, id);
        this.relativePos = relativePos;
        this.containing = new Entity[relativePos.length];
    }

    public void anchorEntities() {
        for (int i = 0; i < containing.length; i++) {
            if (containing[i] != null) {
                containing[i].x = this.x + relativePos[i][0];
                containing[i].y = this.y + relativePos[i][1];
            }
        }
    }

    public Entity takeAway(int index) {
        Entity taken = this.containing[index].duplicate();
        this.containing[index] = null;
        return taken;
    }
}
