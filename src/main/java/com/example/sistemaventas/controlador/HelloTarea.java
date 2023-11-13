package com.example.sistemaventas.controlador;

import com.example.sistemaventas.modelo.dao.EventoDao;
import com.example.sistemaventas.modelo.dao.TareaDao;
import com.example.sistemaventas.modelo.dominio.Evento;
import com.example.sistemaventas.modelo.dominio.Tarea;
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

public class HelloTarea implements Initializable {
    @FXML
    private TableView<Tarea> tablaTareas;
    @FXML
    private TableColumn<Tarea, String> colTitulo;
    @FXML
    public TableColumn<Tarea, String> colDescripcion;
    @FXML
    public TableColumn<Tarea, String> colCurso;
    @FXML
    public TableColumn<Tarea, String> colFechaEntrega;
    @FXML
    public TableColumn<Tarea, String> colFecha;
    @FXML
    private TextField tituloText;
    @FXML
    private TextField descripcionText;
    @FXML
    private TextField cursoText;
    @FXML
    private TextField fechaEntregaText;

    @FXML
    public Button guardarBtn;
    Integer idTarea = 0;
    private ObservableList<Tarea> tareasObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listarTareas();

    }

    public void listarTareas() {
        tablaTareas.getItems().clear();

        TareaDao tareaDao = new TareaDao();
        List<Tarea> tareas = tareaDao.listarTareas();
        tareasObservableList.addAll(tareas);
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colDescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));
        colCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCurso()));
        colFechaEntrega.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaEntrega()));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        colFecha.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getFecha())));
        tablaTareas.setItems(tareasObservableList);

        tablaTareas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        seleccionarTarea(newValue);
                    }
                }
        );

    }
    public void onInsertarButtonClick(ActionEvent actionEvent) {
        TareaDao tareaDao = new TareaDao();
        Tarea tarea = new Tarea();
        if (idTarea == 0) {
            tarea.setTitulo(tituloText.getText());
            tarea.setDescripcion(descripcionText.getText());
            tarea.setCurso(cursoText.getText());
            tarea.setFechaEntrega(fechaEntregaText.getText());
            tarea.setFecha(new Date());
            tareaDao.insertarTarea(tarea);
        } else {
            tarea.setId(idTarea);
            tarea.setTitulo(tituloText.getText());
            tarea.setDescripcion(descripcionText.getText());
            tarea.setCurso(cursoText.getText());
            tarea.setFechaEntrega(fechaEntregaText.getText());
            tareaDao.actulizarTarea(tarea);


        }
        idTarea = 0;
        onLimpiarButtonClick(null);
        listarTareas();
        guardarBtn.setText("Guardar");
    }


    public void onLimpiarButtonClick(ActionEvent actionEvent) {
        tituloText.clear();
        descripcionText.clear();
        cursoText.clear();
        fechaEntregaText.clear();
    }
    public void onSeleccionarButtonClick(ActionEvent actionEvent) {
        TareaDao tareaDao = new TareaDao();
        Tarea tarea= tareaDao.tareaPorId(idTarea);
        tituloText.setText(tarea.getTitulo());
        descripcionText.setText(tarea.getDescripcion());
        cursoText.setText(tarea.getCurso());
        fechaEntregaText.setText(tarea.getFechaEntrega());
        guardarBtn.setText("Guardar Editar");
    }

    private void seleccionarTarea(Tarea tarea) {
        System.out.println("id:"+ tarea.getId());
        idTarea = tarea.getId();
    }
    public void eliminarButtonClick(ActionEvent actionEvent) {
        TareaDao tareaDao = new TareaDao();
        tareaDao.eliminarTareaPorId(idTarea);
        listarTareas();
    }
}

