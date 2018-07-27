package logic.comanda;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class BacktrackingMilloresC extends BacktrackingC{
    /*
     * tipus
     *      Configuracio = array [1..MAX_X * MAX_Y] de Punt
     *      Marcatge = registre
     *                      prodRecollits: array [1..MAX_COMANDA] de enter
     *                      visites: array [1..MAX_X] de array [1..MAX_Y] de enter
     * fitupus
     */

    public BacktrackingMilloresC(Warehouse warehouse, Producte[] comanda, Punt[] distribucio, Producte[] productes) {
        super(warehouse, comanda, distribucio, productes);
    }

    @Override
    public void cercaRecorregut() {
        Punt[] x = new Punt[warehouse.getMaxX() * warehouse.getMaxY()];
        x[0] = new Punt(warehouse.getEntrance().getX(), warehouse.getEntrance().getY());
        Marcatge m = new Marcatge(comanda.length, warehouse.getMaxX(), warehouse.getMaxY());
        marcar(x, 0, m);
        if (!esSolucio(m)) {
            backtracking(x, 1, 0, m);
        } else {
            tractarSolucio(x, 0);
            System.out.println("\r\nEls productes de la comanda estaven a les prestatgeries de l'entrada");
        }
    }

    private void backtracking(Punt[] x, int k, int mov, Marcatge m) {
        int vegades = 0;
        x[k] = new Punt(x[k - 1].getX(),x[k - 1].getY());
        while (hiHaSuccessor(x, k, vegades)) {
            mov = seguentGerma(x, k, mov);
            marcar(x, k, m);
            vegades++;

            if (esSolucio(m)) {
                if (esBona(x, k, m)) {
                    tractarSolucio(x, k);
                }
            } else {
                if (esBona(x, k, m)) {
                    if (k < vMillor || vMillor == V_INDEF) {
                        backtracking(x, k + 1, tractaMov(mov), m);
                    }
                }
            }
            desmarcar(x, k, m);
        }
    }

    private boolean esSolucio(Marcatge m) {
        boolean ok = true;
        for (int i = 0; i < comanda.length; i++) {
            ok = ok && (m.getProdRecAt(i) != 0);
        }
        return ok;
    }

    private boolean esBona(Punt[] x, int k, Marcatge m) {
        return (x[k].getY() < warehouse.getMaxY() && x[k].getY() >= 0
                && x[k].getX() < warehouse.getMaxX() && x[k].getX() >= 0
                && warehouse.getPrestatgeriaIdIn(x[k]) == 0 && m.getVisitesAt(x[k]) <= V_VISITES);
    }

    private void marcar(Punt[] x, int k, Marcatge m) {
        m.incVisites(x[k]);
        for (int i = 0; i < comanda.length; i++) {
            if (m.getProdRecAt(i) == 0 && Punt.isAdjacent(x[k],
                    distribucio[Producte.getPositionFromID(comanda[i].getId(), productes)])) {
                m.setProdRecAt(i, k+1);
            }
        }
    }

    private void desmarcar(Punt[] x, int k, Marcatge m) {
        m.decVisites(x[k]);
        for (int i = 0; i < comanda.length; i++) {
            if (m.getProdRecAt(i) == k + 1) {
                m.setProdRecAt(i, 0);
            }
        }
    }
}
