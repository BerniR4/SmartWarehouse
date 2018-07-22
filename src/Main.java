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

        /*
         ****************
         * SOFTWARE BASE
         ****************
         */

        boolean [][]matrix = new boolean[7][4]; // instanciem una matriu que representa el magatzem

        for (int i = 0; i < matrix[0].length - 1; i++)
            matrix[0][i] = matrix[2][i] = true; // afegim prestatgeries

        // Creem la vista
        // usem el magatzem amb la matriu anterior i el punt (x,y) d'entrada
        WarehouseView view = new WarehouseView(matrix, 1, 0);

        // Creem el controlador
        BoxListener controlador = new BoxListener(view);
        view.setMapMouseListener(controlador);

        view.setVisible(true);

    }

}
