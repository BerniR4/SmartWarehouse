package logic.distribucio;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class Backtracking {
    private final static int V_INDEF = -1;

    private Punt[] xMillor;
    private long vMillorAfin;     //millor valor d'afinitat entre els productes
    private int vMillorPrest;       //millor valor de prestatgeries utilitzades
    private Warehouse warehouse;
    private Producte[] productes;
    private double[][] graf;

    /*
     * tipus
     *      Configuracio = array [1..MAX_PRODUCTES] de Punt
     *      Marcatge = registre
     *                      numProd: array [1..MAX_Y] de array [1..MAX_X] de enter
     *                      relacioDistancia: real
     *                      numPrestatgeries: int
     *                 firegistre
     * fitupus
     */

    public Backtracking(Warehouse warehouse, Producte[] productes, double[][] graf) {
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

    /* Solució 1: array d'n caselles on cada posició és un producte i el que hi ha a la casella indica a quin prestatge està.
     *          +Menys memoria.
     *          +Sembla més senzill.
     *          +Per la fase 2 millor
     *          -Funció esBona més costosa.
     */

    /* Solució 2: matriu de la mida del magatzem, on a cada casella hi ha quins productes hi van.
     *          +Funció esBona més ràpida.
     *          -Més memoria.
     *          -Per la fase 2 anirà pitjor
     */

    // S'utilitzarà la solució 1 i, la solució 2 es farà servir com a marcatge.

    // La funció a reduir serà la distancia entre els productes per la seva relació (graf) i el nombre de prestatgeries utilitzades (més important).

    // Una configuració serà posible si el producte està situat en una prestatgeria i en aquella prestatgeria
    // no hi ha més de 3 productes.

    // És solució quan s'han col·locat tots els productes.

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

    private boolean hiHaSuccessor(Punt p) {
        return (p.getX() != warehouse.getMaxX() - 1) || (p.getY() != warehouse.getMaxY() - 1);
    }

    private void seguentGerma(Punt[] x, int k) {
        Punt p = x[k];
        p.setX(p.getX() + 1);
        if (p.getX() >= warehouse.getMaxX()) {
            p.setX(0);
            p.setY(p.getY() + 1);
        }
        x[k] = p;
    }

    private boolean esSolucio(int k) {
        return k == productes.length - 1;
    }

    private boolean esBona(Punt[] x, int k) {
        int numProd = 0;
        for (int i = 0; i < k; i++) {
            if (x[k].getX() == x[i].getX() && x[k].getY() == x[i].getY()) {
                numProd++;
            }
        }

        return (numProd < 3) && (warehouse.getPrestatgeriaIdIn(x[k]) != 0);
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
