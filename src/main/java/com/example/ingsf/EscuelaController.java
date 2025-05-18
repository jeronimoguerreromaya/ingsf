package com.example.ingsf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ingsf.*;



public class EscuelaController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCedula;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCorreo;
    @FXML private ComboBox<String> comboCurso;
    @FXML private ComboBox<String> comboEstado;
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellido;
    @FXML private TableColumn<Cliente, String> colCedula;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colCorreo;



    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        // Vinculamos la lista observable a la tabla
        tablaClientes.setItems(clientes);

        // Agregamos cliente de prueba a la lista observable
        clientes.add(new Cliente( "Laura", "Gómez", "12345678", "3111234567", "laura@email.com"));
        System.out.println("Inicializando...");
        System.out.println("Tamaño de la lista de clientes: " + clientes.size());
    }


    @FXML
    public void agregarCliente() {

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();


        if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            System.out.println("Campos Vacios");
        }else{
            clientes.add(new Cliente( nombre, apellido, cedula, telefono, correo));
            System.out.println("Cliente agregado");
        }
    }

    @FXML
    public void actualizarCliente() {

    }

    @FXML
    public void eliminarCliente() {

    }


    @FXML
    public void limpiarCampos() {

    }

}

