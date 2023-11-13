package com.example.sistemaventas.modelo.dominio;

import java.util.Date;

public class Evento extends Actividad {
    private String lugar;
    private String organizador;


    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    @Override
    public String toString() {
        return "Evento{" +
                ", organizador='" + organizador + '\'' +
                ", lugar=" + lugar +
                '}';
    }

}
