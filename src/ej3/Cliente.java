package ej3;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {

    static final String HOST;
    static final int PUERTO = 2000;

    static {
        try {
            HOST = String.valueOf(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket socketCliente = new DatagramSocket(PUERTO);
        boolean burgerExist = false;
        String[] burgers = {"Cheeseburger", "Whopper", "Long Chicken"};
        Scanner sc = new Scanner(System.in);
        InetAddress destino = InetAddress.getByName(HOST);
        System.out.println("-----MENU-----");
        System.out.println("Burgers: Cheeseburger, Whopper, Long Chicken");
        System.out.println("Side: Patatas Clasicas, Patatas Supreme, Aros de Cebolla");
        System.out.println("Bebida: CocaCola, Nestea, Monster");

        String burgerPedida = "";
        while (!burgerExist) {
            System.out.print("Indica que hamburgesa quieres: ");
            burgerPedida = sc.nextLine();
            for (String burger : burgers){
                if (burgerPedida.equalsIgnoreCase(burger)) {
                    burgerExist = true;
                }
            }
        }

        System.out.print("Indica que side quieres: ");
        String side = sc.nextLine();
        System.out.print("Indica que bebida quieres: ");
        String bebida = sc.nextLine();

        Pedido pedidoSalida = new Pedido(burgerPedida, side, bebida);

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
    }

}
