package ej3;

import java.io.Serializable;

public class Pedido implements Serializable {
    static final long SerialVersionUID = 1L;
    String burger;
    String company;
    String refresco;

    public Pedido(String burger, String company, String refresco) {
        this.burger = burger;
        this.company = company;
        this.refresco = refresco;
    }

    public String getBurger() {
        return burger;
    }

    public String getCompany() {
        return company;
    }

    public String getRefresco() {
        return refresco;
    }

    @Override
    public String toString() {
        return "Tu pedido es: " + burger + " acompanyado con " + company + " y regado con " + refresco;
    }
}
