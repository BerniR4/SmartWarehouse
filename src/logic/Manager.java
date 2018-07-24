package logic;

import controller.BoxListener;
import helpers.DataReader;
import logic.comanda.BacktrackingC;
import logic.comanda.BacktrackingMilloresC;
import logic.distribucio.BacktrackingD;
import logic.distribucio.BacktrackingMilloresD;
import model.DataManager;
import model.Producte;
import view.WarehouseView;

import java.util.Scanner;

public class Manager {
    private Scanner sc;
    private BoxListener controller;
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
                controller = new BoxListener(view, dataManager);
                view.setMapMouseListener(controller);

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
                    BacktrackingD backtrackingD = new BacktrackingD(dataManager.getWarehouse(), dataManager.getProductes(),
                            dataManager.getGraf());
                    backtrackingD.cercaDsitribucio();
                    dataManager.setDistribucio(backtrackingD.getxMillor());
                    controller.setScoreInfo(backtrackingD.getvMillorPrest(), backtrackingD.getvMillorAfin());
                }
                break;

            case 4:
                if (!dataManager.isConfOk()) {
                    System.out.println("\r\nError, les dades no estan inicialitzades. " +
                            "Faci les opcions 1 i 2 abans de demanar una distribució.\r\n");
                } else {
                    BacktrackingMilloresD backtrackingMillores = new BacktrackingMilloresD(dataManager.getWarehouse(),
                            dataManager.getProductes(), dataManager.getGraf());
                    backtrackingMillores.cercaDsitribucio();
                    dataManager.setDistribucio(backtrackingMillores.getxMillor());
                    controller.setScoreInfo(backtrackingMillores.getvMillorPrest(),
                            backtrackingMillores.getvMillorAfin());
                }
            break;

            case 5:
                if (!dataManager.isDistOk()) {
                    System.out.println("\r\nError, els productes no estan distribuïts. " +
                            "Faci la opció 3 o 4 abans de fer una comanda.\r\n");
                } else {
                    System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació de la comanda: ");
                    sc.next();
                    Producte[] comanda = dataReader.readProducts("data/products2.json");
                    if (inStock(comanda)) {
                        BacktrackingC backtrackingC = new BacktrackingC(dataManager.getWarehouse(),
                                comanda, dataManager.getDistribucio(), dataManager.getProductes());
                        backtrackingC.cercaRecorregut();
                        controller.paintTrack(backtrackingC.getxMillor(), backtrackingC.getvMillor());
                        controller.setTrackCost(backtrackingC.getvMillor());
                    } else {
                        System.out.println("\r\nError, algun producte de la comanda no està al magatzem.\r\n");
                    }

                }
                break;

            case 6:
                if (!dataManager.isDistOk()) {
                    System.out.println("\r\nError, els productes no estan distribuïts. " +
                            "Faci la opció 3 o 4 abans de fer una comanda.\r\n");
                } else {
                    System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació de la comanda: ");
                    sc.next();
                    Producte[] comanda = dataReader.readProducts("data/products2.json");
                    if (inStock(comanda)) {
                        BacktrackingMilloresC backtrackingMilloresC = new BacktrackingMilloresC(dataManager.getWarehouse(),
                                comanda, dataManager.getDistribucio(), dataManager.getProductes());
                        backtrackingMilloresC.cercaRecorregut();
                        controller.paintTrack(backtrackingMilloresC.getxMillor(), backtrackingMilloresC.getvMillor());
                        controller.setTrackCost(backtrackingMilloresC.getvMillor());
                    } else {
                        System.out.println("\r\nError, algun producte de la comanda no està al magatzem.\r\n");
                    }
                }
                break;
        }
    }

    private boolean inStock(Producte[] comanda) {
        for (Producte p : comanda) {
            if (Producte.getPositionFromID(p.getId(), dataManager.getProductes()) == -1) return false;
        }
        return true;
    }

}
