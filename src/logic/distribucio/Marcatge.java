package logic.distribucio;

import model.Punt;

public class Marcatge {
    private int[][] numProd;    //num de productes en un punt del magatzem
    private long afinDist;    //distancia entre els productes * la seva afinitat
    private int numPrest;       //num de prestatgeries utilitzades

    public Marcatge(int maxY, int maxX) {
        this.numProd = new int[maxY][maxX];
        this.numPrest = 0;
        this.afinDist = 0;
    }

    public long getAfinDist() {
        return afinDist;
    }

    public void setAfinDist(long afinDist) {
        this.afinDist = afinDist;
    }

    public void addAfinDist(double value) {
        this.afinDist += (long) Math.floor(value * Math.pow(10, 6));
    }

    public void subtractAfinDist(double value) {
        this.afinDist -= (long) Math.floor(value * Math.pow(10, 6));

    }

    public void incNumProdIn(Punt p){
        numProd[p.getY()][p.getX()]++;
    }

    public void decNumProdIn(Punt p){
        numProd[p.getY()][p.getX()]--;
    }

    public int getNumProdIn(Punt p){
        return numProd[p.getY()][p.getX()];
    }

    public void incNumPrest() {
        this.numPrest++;
    }

    public void decNumPrest() {
        this.numPrest--;
    }

    public int getNumPrest() {
        return numPrest;
    }

}
