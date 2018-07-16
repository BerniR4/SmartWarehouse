import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import controller.BoxListener;
import helpers.Menu;
import logic.Manager;
import model.Producte;
import view.WarehouseView;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String args[]){
        Menu menu = new Menu();
        Producte[] productes;
        Manager manager = new Manager();

        do {
            menu.mostrarMenu();
            manager.executeOption(menu.getOpcio());
        }while(menu.getOpcio() != 5);

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
        // Establim la relació entre la vista i el controlador
        view.setMapMouseListener(controlador);

        /* Actualitzem el panell d'informació d'una casella en les diferents altures
         * en, imaginariament, el punt (x,y)=(0,0).
         */
        view.setBoxInfo(
                new String[] {
                        "(x,y,z)=(0,0,0) P1 - ID: 1234",
                        "(x,y,z)=(0,0,1) P2 - ID: 4321",
                        "(x,y,z)=(0,0,2) (empty)"
                }
        );

        view.setScoreInfo(5.6); // actualitzem el panell de puntuació de la distribució feta
        view.setTrackCost(15);  // actualitzem el cost que té un recorregut realitzat

        view.paintCell(3, 3, Color.RED);    // la casella en el punt (3,3) passarà a ser vermella
        // Mostrem la vista
        view.setVisible(true);

    }

}
