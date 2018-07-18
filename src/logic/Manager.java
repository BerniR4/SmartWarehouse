package logic;

import helpers.DataReader;
import logic.distribucio.Backtracking;
import model.Producte;
import model.Punt;
import model.Warehouse;

import java.util.Scanner;

public class Manager {
    private Producte[] productes;
    private double[][] graf;
    private Warehouse warehouse;
    private Backtracking backtracking;
    private Scanner sc;

    public Manager() {
        sc = new Scanner(System.in);
    }

    public void executeOption(int option) {

        DataReader dataReader = new DataReader();

        switch (option) {
            case 1:
                //TODO
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del magatzem: ");
                sc.next();
                warehouse = dataReader.readWarehouse("data/warehouse.json");
                break;

            case 2:
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del productes: ");
                sc.next();
                productes = dataReader.readProducts("data/products2.json");
                System.out.println("\r\nIntrodueix l'ubicació del graf: ");
                sc.next();
                graf = dataReader.readGraph("data/graph.txt", productes);
                break;

            case 3:
                if (warehouse == null || productes == null || graf == null) {
                    System.out.println("\r\nError, les dades no estan inicialitzades. " +
                            "Faci les opcions 1 i 2 abans de demanar una distribució.\r\n");
                } else {
                    backtracking = new Backtracking(warehouse, productes, graf);
                    backtracking.cercaDsitribucioMillores();
                    Punt[] distribucio = backtracking.getxMillor();
                }
        }
    }

}
