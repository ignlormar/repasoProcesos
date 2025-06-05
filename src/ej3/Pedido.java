package ej3;

import java.io.Serializable;

public class Pedido implements Serializable {

    static final long SerialVersionUID = 1L;

    String burger;
    String side;
    String bebida;

    public Pedido(String burger,String side, String bebida){
        this.burger = burger;
        this.side = side;
        this.bebida = bebida;
    }

    public String getBurger() {
        return burger;
    }

    public String getSide() {
        return side;
    }

    public String getBebida() {
        return bebida;
    }

    @Override
    public String toString() {
        String resultado;
        
        if (side.isEmpty() && bebida.isEmpty()){
            resultado = ("Tu pedido es " + burger);
        } else if (bebida.isEmpty()) {
            
        }

        return "Pedido{" +
                "burger='" + burger + '\'' +
                ", side='" + side + '\'' +
                ", bebida='" + bebida + '\'' +
                '}';
    }
}
