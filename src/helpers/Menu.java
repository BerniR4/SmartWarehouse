package helpers;

import java.util.Scanner;

public class Menu {

    private int opcio;
    private Scanner sc;

    public Menu(){
        opcio = 0;
        sc = new Scanner(System.in);
    }

    public int getOpcio() {
        return opcio;
    }

    public void mostrarMenu() {

        System.out.println("\r\n1. Configurar magatzem");
        System.out.println("2. Carregar productes");
        System.out.println("3. Distribuir productes");
        System.out.println("4. Distribuir productes (millores)");
        System.out.println("5. Servir comanda");
        System.out.println("6. Servir comanda (millores)");
        System.out.println("7. Sortir\r\n");
        System.out.println("\r\nSel·lecciona una opcio:");
        this.demanarOpcio();

    }

    public void demanarOpcio() {
        int opcio;

        try{

            opcio = sc.nextInt();
            this.opcio = opcio;

        }catch (java.util.InputMismatchException e){

            System.err.println("Error, l'opció ha de ser un enter.");
            System.out.println();
            this.opcio = 0;

            sc.next();

        }
    }
}
