package com.example.sistemaventas.modelo.dominio;


import java.util.Date;

    public class Actividad {
        private int id;
        private String titulo;
        private String descripcion;
        private Date fecha;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }

        @Override
        public String toString() {
            return "Actividad{" +
                    "id=" + id +
                    ", titulo='" + titulo + '\'' +
                    ", descripcion='" + descripcion + '\'' +
                    ", fecha=" + fecha +
                    '}';
        }


}
