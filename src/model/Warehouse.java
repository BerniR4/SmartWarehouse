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
}
