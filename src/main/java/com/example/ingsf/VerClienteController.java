package com.example.ingsf;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.ingsf.Admin;
import modelo.ingsf.Cliente;
import modelo.ingsf.Inscripcion;
import modelo.ingsf.InscripcionManager;

import java.io.IOException;
import java.util.List;

public class VerClienteController {
    @FXML private Label lblNombre;
    @FXML private Label lblApellido;
    @FXML private Label lblCedula;
    @FXML private Label lblTelefono;
    @FXML private Label lblCorreo;
    @FXML private Label lblCurso;
    @FXML private Label lblEstadoMatricula;
    public InscripcionManager inscripcionManager;
    private Cliente clienteActual;
    private String cursoActual;
    private Inscripcion inscripcionActual;
    @FXML private TextArea areaFactura;

    public void setDatosCliente(Cliente cliente, String curso, Inscripcion inscripcion, InscripcionManager inscripcionManager) {
        this.inscripcionManager = inscripcionManager;
        this.clienteActual = cliente;
        this.cursoActual = curso;
        this.inscripcionActual = inscripcion;

        lblNombre.setText(cliente.getNombre());
        lblApellido.setText(cliente.getApellido());
        lblCedula.setText(cliente.getCedula());
        lblTelefono.setText(cliente.getTelefono());
        lblCorreo.setText(cliente.getCorreo());
        lblCurso.setText(curso);

        // Establecer el estado de la matrícula
        if (inscripcion != null && inscripcion.getEstado() != null) {
            lblEstadoMatricula.setText(inscripcion.getEstado());
        } else {
            lblEstadoMatricula.setText("Pendiente");
        }
    }

    @FXML
    private void generarRecibo() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Generar Recibo");
        alert.setHeaderText(null);
        alert.setContentText("Generando recibo para " + clienteActual.getNombre() +
                " " + clienteActual.getApellido() +
                "\nCurso: " + cursoActual +
                "\nEstado: " + lblEstadoMatricula.getText());
        alert.showAndWait();

        this.inscripcionActual.getFactura().imprimirFactura();
    }

    @FXML
    private void completarInscripcion() {
        // Crear ventana de login
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        // Crear campos y botón
        Label userLabel = new Label("Usuario:");
        TextField userField = new TextField();
        Label passLabel = new Label("Contraseña:");
        PasswordField passField = new PasswordField();
        Button loginButton = new Button("Entrar");

        // Layout simple
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginButton, 1, 2);

        Scene scene = new Scene(grid);
        loginStage.setScene(scene);
        loginStage.initModality(Modality.APPLICATION_MODAL); // bloquea ventana principal

        // Acción del botón login
        loginButton.setOnAction(e -> {
            String usuario = userField.getText();
            String contrasena = passField.getText();
            Admin admin = new Admin();
            // Validación simple (por ejemplo usuario=admin y contraseña=1234)
            if (admin.getCedula().equals(usuario) && admin.getPassword().equals(contrasena)) {

                loginStage.close();

                // Actualizar el estado de inscripción y factura
                inscripcionActual.setEstado("Inscripto");
                if (inscripcionActual.getFactura() != null) {
                    inscripcionActual.getFactura().setEstado("PAGADO");
                }
                inscripcionManager.guardarInscripciones();

                // Actualizar la vista del estado en pantalla
                lblEstadoMatricula.setText(inscripcionActual.getEstado());

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Completar Inscripción");
                alert.setHeaderText(null);
                alert.setContentText("Completando inscripción para " + clienteActual.getNombre() +
                        " " + clienteActual.getApellido() +
                        "\nCurso: " + cursoActual);
                alert.showAndWait();

            } else {
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error de Login");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Usuario o contraseña incorrectos");
                errorAlert.showAndWait();
            }
        });

        loginStage.showAndWait();
    }
    @FXML
    public void EstadoRecibo() {
        this.inscripcionActual.mostarFactura();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblNombre.getScene().getWindow();
        stage.close();
    }

}

