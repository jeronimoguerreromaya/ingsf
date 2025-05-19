package com.example.ingsf;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modelo.ingsf.Cliente;
import modelo.ingsf.Inscripcion;

public class VerClienteController {
    @FXML private Label lblNombre;
    @FXML private Label lblApellido;
    @FXML private Label lblCedula;
    @FXML private Label lblTelefono;
    @FXML private Label lblCorreo;
    @FXML private Label lblCurso;

    private Cliente clienteActual;
    private String cursoActual;

    public void setDatosCliente(Cliente cliente, String curso) {
        this.clienteActual = cliente;
        this.cursoActual = curso;
        lblNombre.setText(cliente.getNombre());
        lblApellido.setText(cliente.getApellido());
        lblCedula.setText(cliente.getCedula());
        lblTelefono.setText(cliente.getTelefono());
        lblCorreo.setText(cliente.getCorreo());
        lblCurso.setText(curso);
    }

    @FXML
    private void generarRecibo() {
        // Aquí implementarás la lógica para generar el recibo
        // Por ahora mostraremos un mensaje temporal
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Generar Recibo");
        alert.setHeaderText(null);
        alert.setContentText("Generando recibo para " + clienteActual.getNombre() +
                " " + clienteActual.getApellido() +
                "\nCurso: " + cursoActual);
        alert.showAndWait();
    }

    @FXML
    private void completarInscripcion() {
        // Aquí implementarás la lógica para completar la inscripción
        // Por ahora mostraremos un mensaje temporal
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Completar Inscripción");
        alert.setHeaderText(null);
        alert.setContentText("Completando inscripción para " + clienteActual.getNombre() +
                " " + clienteActual.getApellido() +
                "\nCurso: " + cursoActual);
        alert.showAndWait();
    }
    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        stage.close();
    }
}