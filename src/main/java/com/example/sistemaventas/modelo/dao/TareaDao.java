package com.example.sistemaventas.modelo.dao;

import com.example.sistemaventas.modelo.dominio.Evento;
import com.example.sistemaventas.modelo.dominio.Tarea;
import com.example.sistemaventas.util.ConexionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TareaDao {
    private final ConexionDB conexionDB;
    private PreparedStatement preparedStatement;

    public TareaDao() {
        this.conexionDB = new ConexionDB();
    }

    public void crearTablaTarea() {
        String sqlTarea = "CREATE TABLE IF NOT EXISTS tarea (id integer primary key,titulo text,descripcion text, curso text, fecha_entrega text, fecha  DATE)";
        try {
            preparedStatement = conexionDB.connection.prepareStatement(sqlTarea);
            preparedStatement.execute();
            System.out.println("Tabla tarea creada o actualizada correctamente");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Tarea> listarTareas() {
        crearTablaTarea();
        List<Tarea> tareas = new ArrayList<>();
        String consultaListarTareas = "select id, titulo, descripcion, curso, fecha_entrega, fecha from tarea";
        try {
            preparedStatement = conexionDB.connection.prepareStatement(consultaListarTareas);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt(1));
                tarea.setTitulo(resultSet.getString(2));
                tarea.setDescripcion(resultSet.getString(3));
                tarea.setCurso(resultSet.getString(4));
                tarea.setFechaEntrega(resultSet.getString(5));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                tarea.setFecha(formatter.parse(resultSet.getString(6)));
                tareas.add(tarea);
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
        this.conexionDB.cerrarConexionDB();
        System.out.println("Listar tareas correctamente");
        return tareas;
    }
    public boolean insertarTarea(Tarea tarea) {
        crearTablaTarea();
        String consultaInsertarTarea = "INSERT INTO tarea(titulo, descripcion, curso, fecha_entrega, fecha) VALUES(?,?,?,?,?)";
        try {
            preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarTarea);
            preparedStatement.setString(1, tarea.getTitulo());
            preparedStatement.setString(2, tarea.getDescripcion());
            preparedStatement.setString(3, tarea.getCurso());
            preparedStatement.setString(4, tarea.getFechaEntrega());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            preparedStatement.setString(5, formatter.format(tarea.getFecha()));
            preparedStatement.execute();
            System.out.println("Insertar tarea correctamente");
            this.conexionDB.cerrarConexionDB();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Tarea tareaPorId(Integer id) {
        crearTablaTarea();
        String consultaTareaPorId = "select id, titulo, descripcion, curso, fecha_entrega, fecha from tarea where id =?";
        Tarea tarea = new Tarea();
        try {
            preparedStatement = conexionDB.connection.prepareStatement(consultaTareaPorId);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tarea.setId(resultSet.getInt(1));
                tarea.setTitulo(resultSet.getString(2));
                tarea.setDescripcion(resultSet.getString(3));
                tarea.setCurso(resultSet.getString(4));
                tarea.setFechaEntrega(resultSet.getString(5));
                //cliente.setDescripcion(resultSet.getString(4));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.conexionDB.cerrarConexionDB();
        System.out.println("Lista tarea por id correctamente");
        return tarea;
    }

    public boolean actulizarTarea(Tarea tarea) {
        crearTablaTarea();
        String consultaInsertarTarea = "UPDATE tarea SET titulo =?,descripcion=?,curso=?,fecha_entrega=? WHERE id=?";
        try {
            preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarTarea);
            preparedStatement.setString(1, tarea.getTitulo());
            preparedStatement.setString(2, tarea.getDescripcion());
            preparedStatement.setString(3, tarea.getCurso());
            preparedStatement.setString(4, tarea.getFechaEntrega());
            preparedStatement.setInt(5, tarea.getId());
            preparedStatement.execute();
            System.out.println("Actualizar tarea correctamente");
            this.conexionDB.cerrarConexionDB();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void eliminarTareaPorId(Integer id) {
        crearTablaTarea();
        String consultaTareaPorId = "delete from tarea where id =?";
        try {
            preparedStatement = conexionDB.connection.prepareStatement(consultaTareaPorId);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.conexionDB.cerrarConexionDB();
        System.out.println("eliminacion correcta");

    }
}
