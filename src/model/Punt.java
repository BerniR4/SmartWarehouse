package model;

public class Punt {

    private int x;
    private int y;

    public Punt() {}

    public Punt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static double getDistancia(Punt to, Punt from) {
        return Math.hypot(to.x - from.x, to.y - from.y);
    }

    public static boolean isAdjacent(Punt p1, Punt p2) {
        return (getDistancia(p1, p2) == 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Punt) {
            Punt p = (Punt)obj;
            return (x == p.x) && (y == p.y);
        }
        return super.equals(obj);
    }

}
