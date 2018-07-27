import controller.BoxListener;
import helpers.Menu;
import logic.Manager;
import model.Producte;
import view.WarehouseView;

import java.awt.*;

public class Main {
    public static void main(String args[]){
        Menu menu = new Menu();
        Manager manager = new Manager();

        do {
            menu.mostrarMenu();
            manager.executeOption(menu.getOpcio());
        }while(menu.getOpcio() != 7);
    }

}
