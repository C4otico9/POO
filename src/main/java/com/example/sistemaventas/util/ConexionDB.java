package com.example.sistemaventas.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    public Connection connection;
    private String dbName = "ventas.db";

    public ConexionDB() {
        try {

            String url = "jdbc:sqlite:" + dbName;
            connection = DriverManager.getConnection(url);
            System.out.println("Conexión exitosa a SQlite");

        } catch (SQLException e) {
            System.err.println("Error al conectar a SQLite: " + e.getMessage());

        }
    }

    public void cerrarConexionDB() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexión cerrada");

            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexón: " + e.getMessage());

        }
    }

}

