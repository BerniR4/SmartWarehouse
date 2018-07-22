package logic.distribucio;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class BacktrackingMillores {
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

    public BacktrackingMillores(Warehouse warehouse, Producte[] productes, double[][] graf) {
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

    public void cercaDsitribucioMillores() {
        backtrackingMillores(new Punt[productes.length], 0, new Marcatge(warehouse.getMaxY(), warehouse.getMaxX()));
    }

    private void backtrackingMillores(Punt[] x, int k, Marcatge m) {
        x[k] = new Punt(-1,0);
        while (hiHaSuccessor(x[k])) {
            seguentGerma(x, k);
            marcar(x, k, m);

            if (esSolucio(k)) {
                if (esBona(x, k, m)) {
                    tractarSolucio(x, m);
                }
            } else {
                if (esBona(x, k, m)) {
                    if (m.getNumPrest() < vMillorPrest || vMillorPrest == V_INDEF ||
                            (m.getNumPrest() == vMillorPrest && m.getAfinDist() < vMillorAfin)) {
                        backtrackingMillores(x, k + 1, m);
                    }
                }
            }

            desmarcar(x, k, m);
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

    private boolean esBona(Punt[] x, int k, Marcatge m) {
        return (m.getNumProdIn(x[k])) <= 3 && (warehouse.getPrestatgeriaIdIn(x[k]) != 0);
    }

    private void marcar(Punt[] x, int k, Marcatge m) {
        double afinitat = 0.0;
        boolean samePrest = false;

        for (int i = 0; i < k; i++) {
            afinitat += Punt.getDistancia(x[i], x[k]) / graf[i][k];
            if (warehouse.getPrestatgeriaIdIn(x[k]) == warehouse.getPrestatgeriaIdIn(x[i])) {
                samePrest = true;
            }
        }

        m.incNumProdIn(x[k]);
        m.addAfinDist(afinitat);
        if (!samePrest) m.incNumPrest();
    }

    private void desmarcar(Punt[] x, int k, Marcatge m) {
        double afinitat = 0.0;
        boolean samePrest = false;

        for (int i = 0; i < k; i++) {
            afinitat += Punt.getDistancia(x[i], x[k]) / graf[i][k];
            if (warehouse.getPrestatgeriaIdIn(x[k]) == warehouse.getPrestatgeriaIdIn(x[i])) {
                samePrest = true;
            }
        }

        m.decNumProdIn(x[k]);
        m.subtractAfinDist(afinitat);
        if (!samePrest) m.decNumPrest();
    }

    private void tractarSolucio(Punt[] x, Marcatge m) {
        if (m.getNumPrest() < vMillorPrest || vMillorPrest == V_INDEF ||
                (m.getNumPrest() == vMillorPrest && m.getAfinDist() < vMillorAfin)) {
            vMillorAfin = m.getAfinDist();
            vMillorPrest = m.getNumPrest();
            for (int i = 0; i < x.length; i++) {
                xMillor[i].setX(x[i].getX());
                xMillor[i].setY(x[i].getY());
            }
        }
    }

}
