package ej3;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Servidor {
    static final int PUERTO = 2000;

    public static void main(String[] args) {
        try {

            DatagramSocket socketServidor = new DatagramSocket(PUERTO);
            System.out.println("Servidor escuchando por el puerto " + PUERTO);

            String[] burgers = {"Cheeseburger", "Whopper", "Long chicken"};
            String[] company = {"Patatas clasicas", "Patatas supreme", "Aros de cebolla"};
            String[] refrescos = {"Cocacola", "Nestea", "Monster"};

            String burger_def = "";
            String company_def = "";
            String refresco_def = "";
            boolean burgerOk = false;
            boolean companyOk = false;
            boolean refrescoOk = false;

            byte[] buffer = new byte[1024];
            DatagramPacket paqueteEntrada = new DatagramPacket(buffer, buffer.length);
            socketServidor.receive(paqueteEntrada);
            ByteArrayInputStream bais = new ByteArrayInputStream(paqueteEntrada.getData());
            ObjectInputStream in = new ObjectInputStream(bais);
            Pedido pedidoEntrada = (Pedido) in.readObject();

            String burgerEntrada = pedidoEntrada.getBurger();
            for (String burger : burgers){
                if (burgerEntrada.equals(burger)){
                    burgerOk = true;
                }
            }

            String companyEntrada = pedidoEntrada.getCompany();
            for (String guarnicion : company){
                if (companyEntrada.equals(guarnicion)){
                    companyOk = true;
                }
            }

            String refrescoEntrada = pedidoEntrada.getRefresco();
            for (String refresco : refrescos){
                if (refrescoEntrada.equals(refresco)){
                    refrescoOk = true;
                }
            }

            Pedido pedidoSalida;

            if (companyOk && refrescoOk){
                pedidoSalida = pedidoEntrada;
            } else if (refrescoOk){
                pedidoSalida = new Pedido(burgerEntrada, company_def, refrescoEntrada);
            } else if (companyOk){
                pedidoSalida = new Pedido(burgerEntrada, companyEntrada, refresco_def);
            } else {
                pedidoSalida = new Pedido(burgerEntrada, company_def, refresco_def);
            }

            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bs);
            out.writeObject(pedidoSalida);
            byte[] bytes = bs.toByteArray();
            InetAddress address = paqueteEntrada.getAddress();
            int port = paqueteEntrada.getPort();
            DatagramPacket paqueteSalida = new DatagramPacket(bytes, bytes.length, address, port);
            socketServidor.send(paqueteSalida);


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
