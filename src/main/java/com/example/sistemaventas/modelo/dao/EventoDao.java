package com.example.sistemaventas.modelo.dao;


//import com.example.sistemaventas.modelo.dominio.Cliente;
import com.example.sistemaventas.modelo.dominio.Evento;
import com.example.sistemaventas.util.ConexionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

    public class EventoDao {
        private final ConexionDB conexionDB;
        private PreparedStatement preparedStatement;

        public EventoDao() {
            this.conexionDB = new ConexionDB();
        }

        public void crearTablaEvento() {
            String sqlEvento = "CREATE TABLE IF NOT EXISTS evento (id integer primary key,titulo text,descripcion text, lugar text, organizador text, fecha  DATE)";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(sqlEvento);
                preparedStatement.execute();
                System.out.println("Tabla evento creada o actualizada correctamente");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public List<Evento> listarEventos() {
            crearTablaEvento();
            List<Evento> eventos = new ArrayList<>();
            String consultaListarEventos = "select id, titulo, descripcion, lugar, organizador, fecha from evento";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaListarEventos);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Evento evento = new Evento();
                    evento.setId(resultSet.getInt(1));
                    evento.setTitulo(resultSet.getString(2));
                    evento.setDescripcion(resultSet.getString(3));
                    evento.setLugar(resultSet.getString(4));
                    evento.setOrganizador(resultSet.getString(5));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    evento.setFecha(formatter.parse(resultSet.getString(6)));
                    eventos.add(evento);
                }
            } catch (SQLException | ParseException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("Listar eventos correctamente");
            return eventos;
        }
        public boolean insertarEvento(Evento evento) {
            crearTablaEvento();
            String consultaInsertarEvento = "INSERT INTO evento(titulo, descripcion, lugar, organizador, fecha) VALUES(?,?,?,?,?)";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarEvento);
                preparedStatement.setString(1, evento.getTitulo());
                preparedStatement.setString(2, evento.getDescripcion());
                preparedStatement.setString(3, evento.getLugar());
                preparedStatement.setString(4, evento.getOrganizador());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                preparedStatement.setString(5, formatter.format(evento.getFecha()));
                preparedStatement.execute();
                System.out.println("Insertar evento correctamente");
                this.conexionDB.cerrarConexionDB();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public Evento eventoPorId(Integer id) {
            crearTablaEvento();
            String consultaEventoPorId = "select id, titulo, descripcion, lugar, organizador, fecha from evento where id =?";
            Evento evento = new Evento();
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaEventoPorId);
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    evento.setId(resultSet.getInt(1));
                    evento.setTitulo(resultSet.getString(2));
                    evento.setDescripcion(resultSet.getString(3));
                    evento.setLugar(resultSet.getString(4));
                    evento.setOrganizador(resultSet.getString(5));
                    //cliente.setDescripcion(resultSet.getString(4));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("Lista evento por id correctamente");
            return evento;
        }

        public boolean actulizarEvento(Evento evento) {
            crearTablaEvento();
            String consultaInsertarEvento = "UPDATE evento SET titulo =?,descripcion=?,lugar=?,organizador=? WHERE id=?";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaInsertarEvento);
                preparedStatement.setString(1, evento.getTitulo());
                preparedStatement.setString(2, evento.getDescripcion());
                preparedStatement.setString(3, evento.getLugar());
                preparedStatement.setString(4, evento.getOrganizador());
                preparedStatement.setInt(5, evento.getId());
                preparedStatement.execute();
                System.out.println("Actualizar evento correctamente");
                this.conexionDB.cerrarConexionDB();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void eliminarEventoPorId(Integer id) {
            crearTablaEvento();
            String consultaEventoPorId = "delete from evento where id =?";
            try {
                preparedStatement = conexionDB.connection.prepareStatement(consultaEventoPorId);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.conexionDB.cerrarConexionDB();
            System.out.println("eliminacion correcta");

        }


}
