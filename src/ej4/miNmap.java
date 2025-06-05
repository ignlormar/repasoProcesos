package ej4;

import java.io.IOException;
import java.net.Socket;

public class miNmap {
    public static void main(String[] args) {
        for(int i = 0; i < 65000; i++){
            try{
                Socket socket = new Socket("127.0.0.1", i);
                System.out.println(i + " abierto");
                socket.close();
            } catch (IOException e) {
                //System.err.println("Error");
            }
        }
    }
}
