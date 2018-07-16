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

}
