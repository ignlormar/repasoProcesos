package ej3;

import java.net.DatagramPacket;
import java.io.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente{
    static final String HOST = "localhost";
    static final int PUERTO = 2000;

    public static void main(String[] args) {
        try {
            DatagramSocket socketCliente = new DatagramSocket(2001);
            boolean burgerOk = false;
            String[] burgers = {"Cheeseburger", "Whopper", "Long chicken"};
            Scanner sc = new Scanner(System.in);
            InetAddress destino = InetAddress.getByName(HOST);
            System.out.println("------MENU------");
            System.out.println("Burgers: Cheeseburger, Whopper, Long chicken");
            System.out.println("Companys: Patatas clasicas, Patatas supreme, Aros de cebolla");
            System.out.println("Refrescos: Cocacola, Nestea, Monster");

            String burgerPedida = "";
            while (!burgerOk){
                System.out.println("Indica que hamburguesa quieres: ");
                burgerPedida = sc.nextLine();
                for (String burger : burgers){
                    if (burgerPedida.equals(burger)){
                        burgerOk = true;
                    }
                }
            }

            System.out.println("Indica que company quieres: ");
            String company = sc.nextLine();
            System.out.println("Indica que refresco quieres: ");
            String refresco = sc.nextLine();

            Pedido pedidoSalida = new Pedido(burgerPedida, company, refresco);

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bs);
            out.writeObject(pedidoSalida);
            byte[] bytes = bs.toByteArray();
            DatagramPacket paqueteSalida = new DatagramPacket(bytes, bytes.length, InetAddress.getByName(HOST), PUERTO);
            socketCliente.send(paqueteSalida);

            byte[] buffer = new byte[1024];
            DatagramPacket paqueteEntrada = new DatagramPacket(buffer, buffer.length);
            socketCliente.receive(paqueteEntrada);
            ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
            ObjectInputStream in = new ObjectInputStream(bais);
            Pedido pedidoEntrada = (Pedido) in.readObject();
            System.out.println(pedidoEntrada.toString());

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
