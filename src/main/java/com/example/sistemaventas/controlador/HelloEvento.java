package com.example.sistemaventas.controlador;

import com.example.sistemaventas.modelo.dao.CategoriaDao;
import com.example.sistemaventas.modelo.dao.EventoDao;
import com.example.sistemaventas.modelo.dominio.Categoria;
import com.example.sistemaventas.modelo.dominio.Evento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

    public class HelloEvento implements Initializable {
        @FXML
        private TableView<Evento> tablaEventos;
        @FXML
        private TableColumn<Evento, String> colTitulo;
        @FXML
        public TableColumn<Evento, String> colDescripcion;
        @FXML
        public TableColumn<Evento, String> colLugar;
        @FXML
        public TableColumn<Evento, String> colOrganizador;
        @FXML
        public TableColumn<Evento, String> colFecha;
        @FXML
        private TextField tituloText;
        @FXML
        private TextField descripcionText;
        @FXML
        private TextField lugarText;
        @FXML
        private TextField organizadorText;

        @FXML
        public Button guardarBtn;
        Integer idEvento = 0;
        private ObservableList<Evento> eventosObservableList = FXCollections.observableArrayList();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            listarEventos();

        }

        public void listarEventos() {
            tablaEventos.getItems().clear();

            EventoDao eventoDao = new EventoDao();
            List<Evento> eventos = eventoDao.listarEventos();
            eventosObservableList.addAll(eventos);
            colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
            colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
            colLugar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLugar()));
            colOrganizador.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganizador()));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getFecha())));
            tablaEventos.setItems(eventosObservableList);

            tablaEventos.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        if (newValue != null) {
                            seleccionarEvento(newValue);
                        }
                    }
            );

        }
        public void onInsertarButtonClick(ActionEvent actionEvent) {
            EventoDao eventoDao = new EventoDao();
            Evento evento = new Evento();
            if (idEvento == 0) {
                evento.setTitulo(tituloText.getText());
                evento.setDescripcion(descripcionText.getText());
                evento.setLugar(lugarText.getText());
                evento.setOrganizador(organizadorText.getText());
                evento.setFecha(new Date());
                eventoDao.insertarEvento(evento);
            } else {
                evento.setId(idEvento);
                evento.setTitulo(tituloText.getText());
                evento.setDescripcion(descripcionText.getText());
                evento.setLugar(lugarText.getText());
                evento.setOrganizador(organizadorText.getText());
                eventoDao.actulizarEvento(evento);


            }
            idEvento = 0;
            onLimpiarButtonClick(null);
            listarEventos();
            guardarBtn.setText("Guardar");
        }


        public void onLimpiarButtonClick(ActionEvent actionEvent) {
            tituloText.clear();
            descripcionText.clear();
            lugarText.clear();
            organizadorText.clear();
        }
        public void onSeleccionarButtonClick(ActionEvent actionEvent) {
            EventoDao eventoDao = new EventoDao();
            Evento evento = eventoDao.eventoPorId(idEvento);
            tituloText.setText(evento.getTitulo());
            descripcionText.setText(evento.getDescripcion());
            lugarText.setText(evento.getLugar());
            organizadorText.setText(evento.getOrganizador());
            guardarBtn.setText("Guardar Editar");
        }

        private void seleccionarEvento(Evento evento) {
            System.out.println("id:"+ evento.getId());
            idEvento = evento.getId();
        }
        public void eliminarButtonClick(ActionEvent actionEvent) {
            EventoDao eventoDao = new EventoDao();
            eventoDao.eliminarEventoPorId(idEvento);
            listarEventos();
        }




}
