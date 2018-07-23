package model;

public class Producte {

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isID(int id) {
        return this.id == id;
    }

    public static int getPositionFromID(int id, Producte[] productes) {
        for (int i = 0; i < productes.length; i++) {
            if (productes[i].isID(id)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" - ID: ").append(id);
        return sb.toString();
    }
}
