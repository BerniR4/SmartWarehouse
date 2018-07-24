package logic.distribucio;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class BacktrackingMilloresD extends BacktrackingD{
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

    public BacktrackingMilloresD(Warehouse warehouse, Producte[] productes, double[][] graf) {
        super(warehouse, productes, graf);
    }

    @Override
    public void cercaDsitribucio() {
        backtracking(new Punt[productes.length], 0, new Marcatge(warehouse.getMaxY(), warehouse.getMaxX()));
    }

    private void backtracking(Punt[] x, int k, Marcatge m) {
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
                        backtracking(x, k + 1, m);
                    }
                }
            }

            desmarcar(x, k, m);
        }
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
