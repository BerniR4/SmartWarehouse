package controller;

import model.DataManager;
import model.Punt;
import view.WarehouseView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Material pràctica 1 Programació Avançada i Estructura de Dades
 * Enginyeria Informàtica
 * © La Salle Campus Barcelona - Departament d'Enginyeria
 *
 * Aquesta classe permet l'actualització de la GUI amb les dades del
 * magatzem donat que escolta els clics realitzats sobre la interfície
 * gràfica. El mètode 'mouseClicked' es crida cada cop que una casella
 * del magatzem que sigui d'una prestatgeria s'hagi clicat.
 *
 * Aquesta classe és modificable, en cap cas ha de suposar una limitació
 * en com ha estat implementada. És a dir, si necessiteu algun paràmetre
 * extra o atribut (o bé us sobren) teniu tot el dret a canviar-ho. L'únic
 * que s'ha de mantenir i completar és el mètode 'mouseClicked'.
 *
 * @author Albert Pernía Vázquez
 */
public class BoxListener implements MouseListener {


    private WarehouseView view;
    private DataManager model;



    public BoxListener(WarehouseView view, DataManager model) {
        this.view = view;
        this.model = model;
    }

    public void setScoreInfo(int shelve, long value) {
        view.setScoreInfo(shelve, value);
    }

    public void setTrackCost(int value) {
        view.setTrackCost(value);
    }

    public void paintTrack(Punt[] recorregut, int k) {
        for (int i = 1; i <= k; i++) {
            view.paintCell(recorregut[i].getX(), recorregut[i].getY(), Color.YELLOW);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        Point point = e.getPoint();

        Point p = view.getBoxClickedPosition(point);
        if (p == null) System.out.println("null point");
        else {
            view.setBoxInfo(model.getInfo(new Punt(p.x, p.y)));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
