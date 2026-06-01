public class Wall {
    // Composition of Structure
    public Structure[] levelWalls = new Structure[5];

    public Wall(int x, int y) {
        // Initialize the walls for each level
        this.levelWalls = new Structure[] {
            new Structure(x, y, 0, 0, 0, GameData.ID.WALL_ID),      // Level 0 wall (non-existent)
            new Structure(x, y, 25, 10, 10, GameData.ID.WALL_ID),   // Level 1 wall
            new Structure(x, y, 50, 10, 15, GameData.ID.WALL_ID),   // Level 2 wall
            new Structure(x, y, 60, 10, 20, GameData.ID.WALL_ID),   // Level 3 wall
            new Structure(x, y, 60, 10, 30, GameData.ID.WALL_ID)    // Level 4 wall
        };
    }
}
