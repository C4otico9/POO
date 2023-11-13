package com.example.sistemaventas.modelo.dominio;

import java.util.Date;

public class Tarea extends Actividad {

    private String curso;
    private String fechaEntrega;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" +
                ", curso='" + curso + '\'' +
                ", fechaEntrega='" + fechaEntrega + '\'' +
                '}';
    }
}
