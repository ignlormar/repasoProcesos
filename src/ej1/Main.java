package ej1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String base = System.getProperty("user.dir");
        String ruta = base + "/src/historico.txt";
        int opcion;
        String[] comando;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("1. Imprimir las 10 primeras lineas.");
            System.out.println("2. Imprimir las 10 ultimas lineas.");
            System.out.println("3. Imprimir las lineas pares");
            System.out.println("4. Imprimir las lineas impares");
            System.out.println("0. Salir");
            System.out.print("Indica una opcion: ");
            opcion = sc.nextInt();

            switch (opcion){

                case 1:
                    comando = new String[]{"head", "-n", "10", ruta};
                    ejecutar(comando);
                    break;

                case 2:
                    comando = new String[]{"tail", "-n", "10", ruta};
                    ejecutar(comando);
                    break;

                case 3:
                    comando = new String[]{"awk", "NR % 2 == 0", ruta};
                    ejecutar(comando);
                    break;

                case 4:
                    comando = new String[]{"awk", "NR % 2 == 1", ruta};
                    ejecutar(comando);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Esta opcion no es valida");

            }
        } while (opcion != 0);
    }

    public static void ejecutar(String[] comando){
        ProcessBuilder pb = new ProcessBuilder(comando);
        try {
            Process process = pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String linea;
            while((linea = br.readLine()) != null){
                System.out.println(linea);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}