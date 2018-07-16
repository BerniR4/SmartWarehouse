package logic.distribucio;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class Backtracking {
    private final static int V_INDEF = -1;

    private Punt[] xMillor;
    private double vMillor;
    private Warehouse warehouse;
    private Producte[] productes;
    private float[][] graf;

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

    public Backtracking(Warehouse warehouse, Producte[] productes) {
        this.xMillor = null;
        this.vMillor = V_INDEF;
        this.warehouse = warehouse;
        this.productes = productes;
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

    public Punt[] backtrackingMillores(Punt[] x, int k, Marcatge m) {
        x[k] = new Punt();
        while (hiHaSuccessor(x[k])) {
            seguentGerma(x, k);
            marcar(x, k, m);
            if (esSolucio(k)) {
                //TODO mirar si es bona i tractar-la
            } else {
                //TODO mirar si es bona o si s'ha de desfer.
            }
        }
        return x;
    }

    private boolean hiHaSuccessor(Punt p) {
        return (p.getX() < warehouse.getMaxX() - 1) || (p.getY() < warehouse.getMaxY());
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
        return k == productes.length;
    }

    private boolean esBona(Punt[] x, int k, Marcatge m) {
        return (m.getNumProdIn(x[k])) <= 3 && (warehouse.getPrestatgeriaIdIn(x[k]) != 0);
    }

    private void marcar(Punt[] x, int k, Marcatge m) {
        double afinitat = m.getAfinDist();
        boolean samePrest = false;

        for (int i = 0; i < k; i++) {
            afinitat += Punt.getDistancia(x[i], x[k]) * graf[i][k];
            if (warehouse.getPrestatgeriaIdIn(x[k]) == warehouse.getPrestatgeriaIdIn(x[i])) {
                samePrest = true;
            }
        }

        m.incNumProdIn(x[k]);
        m.setAfinDist(afinitat);
        if (!samePrest) m.incNumPrest();
    }

}
