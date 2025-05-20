package com.example.ingsf;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ingsf.Admin;

public class LoginController {
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtPassword;

    private Admin admin;

    public void initialize() {
        // Inicializar el objeto Admin con las credenciales correctas
        admin = new Admin(); // Ajusta según tus credenciales
    }

    @FXML
    protected void handleLogin() {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if (validarCredenciales(usuario, password)) {
            abrirEscuelaView();
        } else {
            mostrarError("Credenciales incorrectas");
        }
    }

    private boolean validarCredenciales(String usuario, String password) {
        return admin.getCedula().equals(usuario) &&
                admin.getPassword().equals(password);
    }

    private void abrirEscuelaView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("escuela-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 720, 540);

            // Obtener el stage actual
            Stage currentStage = (Stage) txtUsuario.getScene().getWindow();

            // Configurar el nuevo scene
            currentStage.setTitle("Escuela");
            currentStage.setScene(scene);
            currentStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al abrir la ventana principal");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

