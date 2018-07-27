package logic.comanda;

import model.Punt;

public class Marcatge {
    private int[] prodRecollits;
    private int[][] visites;

    public Marcatge(int midaProd, int maxX, int maxY) {
        this.prodRecollits = new int[midaProd];
        this.visites = new int[maxX][maxY];
    }

    public void incVisites(Punt p) {
        if (p.getY() >= 0 && p.getX() >= 0  && p.getY() < visites[0].length && p.getX() < visites.length)
            this.visites[p.getX()][p.getY()]++;
    }

    public void decVisites(Punt p) {
        if (p.getY() >= 0 && p.getX() >= 0  && p.getY() < visites[0].length && p.getX() < visites.length)
            this.visites[p.getX()][p.getY()]--;
    }

    public int getVisitesAt(Punt p) {
        return visites[p.getX()][p.getY()];
    }

    public int getProdRecAt(int pos) {
        return prodRecollits[pos];
    }

    public void setProdRecAt(int pos, int value) {
        prodRecollits[pos] = value;
    }

}
