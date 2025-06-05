package ej2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int numCoches;
        String marca;
        Scanner sc = new Scanner(System.in);

        System.out.print("Indica el numero de cocches que van a competir: ");
        numCoches = sc.nextInt();
        sc.nextLine();

        Coche[] coches = new Coche[numCoches];
        for(int i =0; i < coches.length; i++){
            System.out.print("Indica la marca del coche " + (i+1) + ": ");
            marca = sc.nextLine();
            coches[i] = new Coche(marca, i+1);
        }

        for (Coche coche : coches){
            coche.start();
        }

        sc.close();
    }
}
