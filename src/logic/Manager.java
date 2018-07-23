package logic;

import controller.BoxListener;
import helpers.DataReader;
import logic.distribucio.Backtracking;
import logic.distribucio.BacktrackingMillores;
import model.DataManager;
import model.Punt;
import model.Warehouse;
import view.WarehouseView;

import java.util.Scanner;

public class Manager {
    private Scanner sc;
    private BoxListener controlador;
    private DataManager dataManager;

    public Manager() {
        sc = new Scanner(System.in);
        dataManager = new DataManager();
    }

    public void executeOption(int option) {

        DataReader dataReader = new DataReader();

        switch (option) {
            case 1:
                //TODO
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del magatzem: ");
                sc.next();
                dataManager.setWarehouse(dataReader.readWarehouse("data/warehouse.json"));

                WarehouseView view = new WarehouseView(dataManager.getBoolMap(), dataManager.getWarehouseEntranceX(),
                        dataManager.getWarehouseEntranceY());
                controlador = new BoxListener(view, dataManager);
                view.setMapMouseListener(controlador);

                view.setVisible(true);

                break;

            case 2:
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del productes: ");
                sc.next();
                dataManager.setProductes(dataReader.readProducts("data/products.json"));
                System.out.println("\r\nIntrodueix l'ubicació del graf: ");
                sc.next();
                dataManager.setGraf(dataReader.readGraph("data/graph.txt", dataManager.getProductes()));
                break;

            case 3:
                if (!dataManager.isConfOk()) {
                    System.out.println("\r\nError, les dades no estan inicialitzades. " +
                            "Faci les opcions 1 i 2 abans de demanar una distribució.\r\n");
                } else {
                    Backtracking backtracking = new Backtracking(dataManager.getWarehouse(), dataManager.getProductes(),
                            dataManager.getGraf());
                    backtracking.cercaDsitribucio();
                    dataManager.setConfiguracio(backtracking.getxMillor());
                    controlador.setScoreInfo(backtracking.getvMillorPrest(), backtracking.getvMillorAfin());
                }
                break;

            case 4:
                if (!dataManager.isConfOk()) {
                    System.out.println("\r\nError, les dades no estan inicialitzades. " +
                            "Faci les opcions 1 i 2 abans de demanar una distribució.\r\n");
                } else {
                    BacktrackingMillores backtrackingMillores = new BacktrackingMillores(dataManager.getWarehouse(),
                            dataManager.getProductes(), dataManager.getGraf());
                    backtrackingMillores.cercaDsitribucioMillores();
                    dataManager.setConfiguracio(backtrackingMillores.getxMillor());
                    controlador.setScoreInfo(backtrackingMillores.getvMillorPrest(),
                            backtrackingMillores.getvMillorAfin());
                }
            break;
        }
    }

}
