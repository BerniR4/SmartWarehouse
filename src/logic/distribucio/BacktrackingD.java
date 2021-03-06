package logic.distribucio;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class BacktrackingD {
    protected final static int V_INDEF = -1;

    protected Punt[] xMillor;
    protected long vMillorAfin;     //millor valor d'afinitat entre els productes
    protected int vMillorPrest;       //millor valor de prestatgeries utilitzades
    protected Warehouse warehouse;
    protected Producte[] productes;
    protected double[][] graf;

    /*
     * tipus
     *      Configuracio = array [1..MAX_PRODUCTES] de Punt
     * fitupus
     */

    /* Proposta 1: array d'n caselles on cada posició és un producte i el que hi ha a la casella indica a quin prestatge està.
     *          + Menys memoria.
     *          + Sembla més senzill.
     *          + Per la fase 2 millor
     *          - Funció esBona més costosa.
     */

    /* Proposta 2: matriu de la mida del magatzem, on a cada casella hi ha quins productes hi van.
     *          + Funció esBona més ràpida.
     *          - Més memoria.
     *          - Per la fase 2 anirà pitjor
     */

    // S'utilitzarà la proposta 1 i, la proposta 2 es farà servir com a marcatge.

    // La funció a reduir serà la distancia entre els productes per la seva relació (graf) i el nombre de prestatgeries utilitzades (més important).

    // Una configuració serà posible si el producte està situat en una prestatgeria i en aquella prestatgeria
    // no hi ha més de 3 productes.

    // És solució quan s'han col·locat tots els productes.

    public BacktrackingD(Warehouse warehouse, Producte[] productes, double[][] graf) {
        this.xMillor = new Punt[productes.length];
        for (int i = 0; i < xMillor.length; i++) {
            xMillor[i] = new Punt();
        }
        this.vMillorPrest = V_INDEF;
        this.vMillorAfin = V_INDEF;
        this.warehouse = warehouse;
        this.productes = productes;
        this.graf = graf;
    }

    public Punt[] getxMillor() {
        return xMillor;
    }

    public long getvMillorAfin() {
        return vMillorAfin;
    }

    public int getvMillorPrest() {
        return vMillorPrest;
    }

    public void cercaDsitribucio() {
        backtracking(new Punt[productes.length], 0);
    }

    private void backtracking(Punt[] x, int k) {
        x[k] = new Punt(-1,0);
        while (hiHaSuccessor(x[k])) {
            seguentGerma(x, k);
            if (esSolucio(k)) {
                if (esBona(x, k)) {
                    tractarSolucio(x);
                }
            } else {
                if (esBona(x, k)) {
                    backtracking(x, k + 1);
                }
            }
        }
    }

    protected boolean hiHaSuccessor(Punt p) {
        return (p.getX() != warehouse.getMaxX() - 1) || (p.getY() != warehouse.getMaxY() - 1);
    }

    protected void seguentGerma(Punt[] x, int k) {
        Punt p = x[k];
        p.setX(p.getX() + 1);
        if (p.getX() >= warehouse.getMaxX()) {
            p.setX(0);
            p.setY(p.getY() + 1);
        }
        x[k] = p;
    }

    protected boolean esSolucio(int k) {
        return k == productes.length - 1;
    }

    private boolean esBona(Punt[] x, int k) {
        int numProd = 0;
        if (warehouse.getPrestatgeriaIdIn(x[k]) != 0) {
            for (int i = 0; i < k; i++) {
                if (x[k].equals(x[i])) {
                    numProd++;
                }
            }
            return numProd < 3;
        } else {
            return false;
        }
    }

    private void tractarSolucio(Punt[] x) {
        double afinitat = 0.0;
        boolean samePrest = false;
        int numPrest = 0;

        for (int i = 0; i < x.length; i++){
            for (int j = 0; j < i; j++) {
                afinitat += Punt.getDistancia(x[i], x[j]) / graf[i][j];
                if (warehouse.getPrestatgeriaIdIn(x[j]) == warehouse.getPrestatgeriaIdIn(x[i])) {
                    samePrest = true;
                }
            }
            if (!samePrest) {
                numPrest++;
            }
            samePrest = false;
        }

        long vAfinDist = (long) Math.floor(afinitat * Math.pow(10, 6));

        if (numPrest < vMillorPrest || vMillorPrest == V_INDEF ||
                (numPrest == vMillorPrest && vAfinDist < vMillorAfin)) {
            vMillorAfin = vAfinDist;
            vMillorPrest = numPrest;
            for (int i = 0; i < x.length; i++) {
                xMillor[i].setX(x[i].getX());
                xMillor[i].setY(x[i].getY());
            }
        }
    }
}
