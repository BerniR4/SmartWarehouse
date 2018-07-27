package logic;

import controller.BoxListener;
import helpers.DataReader;
import logic.comanda.BacktrackingC;
import logic.comanda.BacktrackingMilloresC;
import logic.distribucio.BacktrackingD;
import logic.distribucio.BacktrackingMilloresD;
import model.DataManager;
import model.Producte;
import model.Warehouse;
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
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del magatzem: ");
                Warehouse w = dataReader.readWarehouse(sc.next());
                if (w != null) {
                    dataManager.setWarehouse(w);

                    WarehouseView view = new WarehouseView(dataManager.getBoolMap(), dataManager.getWarehouseEntranceX(),
                            dataManager.getWarehouseEntranceY());
                    if (controller == null) {
                        controller = new BoxListener(view, dataManager);
                    } else {
                        controller.updateWarehouseView(view);
                    }

                    view.setMapMouseListener(controller);
                    view.setVisible(true);
                }
                break;

            case 2:
                System.out.println("\r\nIntrodueix l'ubicació del fitxer que conté la informació del productes: ");
                Producte[] p = dataReader.readProducts(sc.next());
                if (p != null) {
                    dataManager.setProductes(p);
                    System.out.println("\r\nIntrodueix l'ubicació del graf: ");
                    double[][] g = dataReader.readGraph(sc.next(), p);
                    if (g != null) {
                        dataManager.setGraf(g);
                    }
                }
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
                    Producte[] comanda = dataReader.readProducts(sc.next());
                    if (comanda != null && inStock(comanda)) {
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
                    Producte[] comanda = dataReader.readProducts(sc.next());
                    if (comanda != null && inStock(comanda)) {
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

            case 7:
                controller.closeView();
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
