package model;

public class DataManager {
    private Warehouse warehouse;
    private Producte[] productes;
    private double[][] graf;
    private Punt[] distribucio;

    public DataManager() {
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Producte[] getProductes() {
        return productes;
    }

    public void setProductes(Producte[] productes) {
        this.productes = productes;
    }

    public double[][] getGraf() {
        return graf;
    }

    public void setGraf(double[][] graf) {
        this.graf = graf;
    }

    public Punt[] getDistribucio() {
        return distribucio;
    }

    public void setDistribucio(Punt[] distribucio) {
        this.distribucio = distribucio;
    }


    public boolean[][] getBoolMap() {
        boolean[][] map = new boolean[warehouse.getMaxX()][warehouse.getMaxY()];
        for (int i = 0; i < warehouse.getMaxY(); i++) {
            for (int j = 0; j < warehouse.getMaxX(); j++){
                if (warehouse.getPrestatgeriaIdIn(new Punt(j,i)) != 0) {
                    map[j][i] = true;
                }
            }
        }
        return map;
    }

    public int getWarehouseEntranceX() {
        return warehouse.getEntrance().getX();
    }

    public int getWarehouseEntranceY() {
        return warehouse.getEntrance().getY();
    }

    public boolean isConfOk() {
        return !(warehouse == null || productes == null || graf == null);
    }

    public boolean isDistOk() {
        return (distribucio != null);
    }

    public String[] getInfo(Punt p) {
        String[] info = new String[3];
        int i = 0;
        if (distribucio != null){
            for (int j = 0; j < distribucio.length; j++) {
                if (distribucio[j].equals(p)) {
                    info[i] = String.format("(x,y,z)=(%d,%d,%d) %s", p.getX(), p.getY(), i, productes[j].toString());
                    i++;
                }
            }
        }

        while (i < 3) {
            info[i] = String.format("(x,y,z)=(%d,%d,%d) (empty)", p.getX(), p.getY(), i);
            i++;
        }

        return info;
    }

}
