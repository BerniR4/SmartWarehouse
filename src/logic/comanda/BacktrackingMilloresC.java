package logic.comanda;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class BacktrackingMilloresC extends BacktrackingC{
    /*
     * tipus
     *      Configuracio = array [1..MAX_X * MAX_Y] de Punt
     * fitupus
     */

    public BacktrackingMilloresC(Warehouse warehouse, Producte[] comanda, Punt[] distribucio, Producte[] productes) {
        super(warehouse, comanda, distribucio, productes);
    }

    @Override
    public void cercaRecorregut() {
        Punt[] x = new Punt[warehouse.getMaxX() * warehouse.getMaxY()];
        x[0] = new Punt(warehouse.getEntrance().getX(), warehouse.getEntrance().getY());
        int[] m = new int[comanda.length];
        marcar(x, 0, m);
        if (!esSolucio(m)) {
            backtracking(x, 1, 0, m);
        } else {
            tractarSolucio(x, 0);
            System.out.println("\r\nEls productes de la comanda estaven a les prestatgeries de l'entrada");
        }
    }

    private void backtracking(Punt[] x, int k, int mov, int[] m) {
        int vegades = 0;
        x[k] = new Punt(x[k - 1].getX(),x[k - 1].getY());
        while (hiHaSuccessor(x, k, vegades)) {
            mov = seguentGerma(x, k, mov);
            marcar(x, k, m);
            vegades++;

            if (esSolucio(m)) {
                if (esBona(x, k)) {
                    tractarSolucio(x, k);
                }
            } else {
                if (esBona(x, k)) {
                    if (k < vMillor || vMillor == V_INDEF) {
                        backtracking(x, k + 1, tractaMov(mov), m);
                    }
                }
            }
            desmarcar(x, k, m);
        }
    }

    private boolean esSolucio(int[] m) {
        boolean ok = true;
        for (int i = 0; i < m.length; i++) {
            ok = (m[i] != 0);
        }
        return ok;
    }

    private void marcar(Punt[] x, int k, int[] m) {
        for (int i = 0; i < m.length; i++) {
            if (m[i] == 0 && Punt.isAdjacent(x[k], distribucio[Producte.getPositionFromID(comanda[i].getId(), productes)])) {
                m[i] = k + 1;
            }
        }
    }

    private void desmarcar(Punt[] x, int k, int[] m) {
        for (int i = 0; i < m.length; i++) {
            if (m[i] == k + 1) {
                m[i] = 0;
            }
        }
    }
}
