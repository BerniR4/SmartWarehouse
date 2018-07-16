package logic;

import helpers.DataReader;
import model.Producte;
import model.Warehouse;

import java.util.Scanner;

public class Manager {
    private Producte[] productes;
    private float[][] graf;
    private Warehouse warehouse;
    private Scanner sc;

    public Manager() {
        sc = new Scanner(System.in);
    }

    public void executeOption(int option) {

        DataReader dataReader = new DataReader();

        switch (option) {
            case 1:
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del magatzem: ");
                warehouse = dataReader.readWarehouse(sc.next());
                break;
            case 2:
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del productes: ");
                productes = dataReader.readProducts(sc.next());
                System.out.println("\r\nIntrodueix l'ubicació del graf: ");
                graf = dataReader.readGraph(sc.next(), productes);

                break;
        }
    }

}
