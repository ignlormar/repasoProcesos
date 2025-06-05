package ej3;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servidor {

    static final int PUERTO = 2000;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket socketServidor = new DatagramSocket(PUERTO);

        String[] burgers = {"Cheeseburger", "Whopper", "Long Chicken"};
        String[] sides = {"Patatas Clasicas", "Patatas Supreme", "Aros de Cebolla"};
        String[] bebidas = {"CocaCola", "Nestea", "Monster"};

        String side_def = "";
        boolean sideExist = false;
        String bebida_def = "";
        boolean bebidaExist = false;

        byte[] buffer = new byte[1024];
        DatagramPacket paqueteEntrada = new DatagramPacket(buffer, buffer.length);
        socketServidor.receive(paqueteEntrada);
        ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
        ObjectInputStream in = new ObjectInputStream(bais);
        Pedido pedidoEntrada = (Pedido) in.readObject();

        String burgerEntrada = pedidoEntrada.getBurger();

        String sideEntrada = pedidoEntrada.getSide();
        for (String side : sides) {
            if (sideEntrada.equalsIgnoreCase(side)){
                sideExist = true;
            }
        }

        String bebidaEntrada = pedidoEntrada.getBebida();
        for (String bebida : bebidas) {
            if (bebidaEntrada.equalsIgnoreCase(bebida)){
                bebidaExist = true;
            }
        }

        Pedido pedidoSalida;
        if (sideExist && bebidaExist) {
            pedidoSalida = pedidoEntrada;
        } else if (bebidaExist) {
            pedidoSalida = new Pedido(burgerEntrada, side_def, bebidaEntrada);
        } else if (sideExist) {
            pedidoSalida = new Pedido(burgerEntrada, sideEntrada, bebida_def);
        } else {
            pedidoSalida = new Pedido(burgerEntrada, side_def, bebida_def);
        }

        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bs);
        out.writeObject(pedidoSalida);
        byte[] bytes = bs.toByteArray();
        InetAddress address = paqueteEntrada.getAddress();
        int port = paqueteEntrada.getPort();
        DatagramPacket paqueteSalida = new DatagramPacket(bytes, bytes.length, address, port);
        socketServidor.send(paqueteSalida);

    }
}
