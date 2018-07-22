package model;

public class Warehouse {
    private int[][] shelves;
    private Punt entrance;

    public Warehouse(int[][] shelves, Punt entrance) {
        this.shelves = shelves;
        this.entrance = entrance;
    }

    public int getMaxY() {
        if (shelves != null) return shelves.length;
        return -1;
    }

    public int getMaxX() {
        if (shelves != null) return shelves[0].length;
        return -1;
    }

    public int getPrestatgeriaIdIn(Punt p) {
        return shelves[p.getY()][p.getX()];
    }

    public boolean[][] getBoolMap() {
        boolean[][] map = new boolean[getMaxY()][getMaxX()];
        for (int i = 0; i < getMaxY(); i++) {
            for (int j = 0; j < getMaxX(); j++){
                if (shelves[i][j] != 0) {
                    map[i][j] = true;
                }
            }
        }
        return map;
    }
}
