package com.example.ingsf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.ingsf.Curso;
import java.util.List;

public class VerCursosController {

    @FXML private TableView<Curso> tablaCursos;
    @FXML private TableColumn<Curso, Integer> colId;
    @FXML private TableColumn<Curso, String> colNombre;
    @FXML private TableColumn<Curso, String> colDescripcion;
    @FXML private TableColumn<Curso, Integer> colDuracion;
    @FXML private TableColumn<Curso, Double> colPrecio;

    private Stage stage;

    @FXML
    public void initialize() {
        // Configuro las columnas para que lean las propiedades de Curso
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
    }

    public void setCursos(List<Curso> cursos) {
        ObservableList<Curso> lista = FXCollections.observableArrayList(cursos);
        tablaCursos.setItems(lista);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
