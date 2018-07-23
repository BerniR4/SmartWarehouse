package logic.comanda;

import model.Producte;
import model.Punt;
import model.Warehouse;

public class BacktrackingMilloresC {
    private final static int V_INDEF = -1;

    private Punt[] xMillor;
    private int vMillor;
    private Warehouse warehouse;
    private Producte[] comanda;
    private Punt[] distribucio;
    private Producte[] productes;

    /*
     * tipus
     *      Configuracio = array [1..MAX_X * MAX_Y] de Punt
     * fitupus
     */

    /* Proposta 1: array de MAX_X * MAX_Y on es guardi si el robot avança cap a la dreta, esquerra...
     *              + Següent germa més facil
     *              - No es sap a quin punt s'està
     *              - No es sap per quins puns s'ha passat
     */

    /* Proposta 2: array de MAX_X * MAX_Y on es guardi cada punt per on es passa
     *              + Es sap tots els punts pels que s'ha passat (per a la visualització serà més senzill
     *              + És més fàcil saber en tot moment per quins punts s'ha passat (per a no repetir)
     *              - Següent germa més difícil
     *
     */

    // El camí que es cercarà en aquesta fase només serà el de recollir els paquets; el camí de tornada no està inclós.

    // S'utilitzarà la proposta 2.

    // La funció a reduir és el camí que segueix el robot (estarà indicat per k quan es sigui solució).

    // El marcatge podria ser un array de la mida dels productes de la comanda on es desi si s'ha agafat el producte o no.

    // Una configuració serà solució quan s'hagin agafat tots els productes.

    public BacktrackingMilloresC(Warehouse warehouse, Producte[] comanda, Punt[] distribucio, Producte[] productes) {
        this.xMillor = new Punt[warehouse.getMaxX() * warehouse.getMaxY()];
        for (int i = 0; i < xMillor.length; i++) {
            xMillor[i] = new Punt();
        }
        this.vMillor = V_INDEF;
        this.warehouse = warehouse;
        this.comanda = comanda;
        this.distribucio = distribucio;
        this.productes = productes;
    }

    public Punt[] getxMillor() {
        return xMillor;
    }

    public int getvMillor() {
        return vMillor;
    }

    public void cercaRecorregutMillores() {
        Punt[] x = new Punt[warehouse.getMaxX() * warehouse.getMaxY()];
        x[0] = new Punt(warehouse.getEntrance().getX(), warehouse.getEntrance().getY());
        int[] m = new int[comanda.length];
        marcar(x, 0, m);
        backtrackingMillores(x, 1, 0, m);
    }

    private void backtrackingMillores(Punt[] x, int k, int mov, int[] m) {
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
                        backtrackingMillores(x, k + 1, tractaMov(mov), m);
                    }
                }
            }
            desmarcar(x, k, m);
        }
    }

    private boolean hiHaSuccessor(Punt[] x, int k, int vegades) {
        return vegades < 4 && k < x.length - 1;
    }

    private int tractaMov(int mov) {
        if (mov >= 2) return mov - 2;
        return mov + 2;
    }

    private int seguentGerma(Punt[] x, int k, int mov) {
        switch (mov) {
            case 0:
                //es mou a dalt
                x[k].setX(x[k - 1].getX());
                x[k].setY(x[k - 1].getY() - 1);
                return 1;

            case 1:
                //es mou dreta
                x[k].setX(x[k - 1].getX() + 1);
                x[k].setY(x[k - 1].getY());
                return 2;

            case 2:
                //es mou baix
                x[k].setX(x[k - 1].getX());
                x[k].setY(x[k - 1].getY() + 1);
                return 3;

            case 3:
                //es mou esquerra
                x[k].setX(x[k - 1].getX() - 1);
                x[k].setY(x[k - 1].getY());
                return 0;
        }
        return 0;
    }

    private boolean esSolucio(int[] m) {
        boolean ok = true;
        for (int i = 0; i < m.length; i++) {
            ok = (m[i] != 0);
        }
        return ok;
    }

    private boolean esBona(Punt[] x, int k) {
        return (x[k].getY() < warehouse.getMaxY() && x[k].getY() >= 0
                && x[k].getX() < warehouse.getMaxX() && x[k].getX() >= 0
                && warehouse.getPrestatgeriaIdIn(x[k]) == 0);
    }

    private void marcar(Punt[] x, int k, int[] m) {
        for (int i = 0; i < m.length; i++) {
            if (m[i] == 0 && Punt.isAdjacent(x[k], distribucio[Producte.getPositionFromID(comanda[i].getId(), productes)])) {
                m[i] = k;
            }
        }
    }

    private void desmarcar(Punt[] x, int k, int[] m) {
        for (int i = 0; i < m.length; i++) {
            if (m[i] == k) {
                m[i] = 0;
            }
        }
    }

    private void tractarSolucio(Punt[] x, int k) {
        if (k < vMillor || vMillor == V_INDEF) {
            vMillor = k;
            for (int i = 0; i <= k; i++) {
                xMillor[i].setX(x[i].getX());
                xMillor[i].setY(x[i].getY());
            }
        }
    }



}
