package com.example.ingsf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.ingsf.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


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
    @FXML private ComboBox<String> combxCurso;
    public InscripcionManager inscripcionManager;
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        inscripcionManager = new InscripcionManager();

        // Solo números para cédula
        txtCedula.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCedula.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.length() > 10) {
                txtCedula.setText(oldValue);
            }
        });

        // Solo números para teléfono
        txtTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtTelefono.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (newValue.length() > 10) {
                txtTelefono.setText(oldValue);
            }
        });



        combxCurso.getItems().addAll("Moto A1","Moto A2","Carro B1","Carro B2","Servicio Publico");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        // Vinculamos la lista observable a la tabla
        tablaClientes.setItems(clientes);

        // Agregamos cliente de prueba a la lista observable
        System.out.println("Inicializando...");
        System.out.println("Tamaño de la lista de clientes: " + clientes.size());
    }


    @FXML
    public void agregarCliente() {
        ArrayList<Curso> cursos = getCursos();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String cedula = txtCedula.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String seleccion = combxCurso.getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || correo.isEmpty() || seleccion.isEmpty()) {
            System.out.println("Campos Vacios");
        }else{
            if(!validarDocumento(cedula)){
                Cliente cliente = new Cliente(nombre, apellido, cedula, telefono, correo);
                Inscripcion inscripcion = new Inscripcion(1, cliente, cursos.get(0));
                if (seleccion.equals("Moto A1")) {
                    inscripcion.setCurso(cursos.get(1));
                } else if (seleccion.equals("Moto A2")) {
                    inscripcion.setCurso(cursos.get(2));
                } else if (seleccion.equals("Carro B1")) {
                    inscripcion.setCurso(cursos.get(3));
                } else if (seleccion.equals("Carro B2")) {
                    inscripcion.setCurso(cursos.get(4));
                } else if (seleccion.equals("Servicio Publico")) {
                    inscripcion.setCurso(cursos.get(0));
                } else {
                    System.out.println("Categoría no reconocida.");
                }
                inscripcionManager.agregarInscripcion(inscripcion);
                clientes.add(inscripcion.getCliente());
            }else{
                System.out.println("Documento ya registrado");
            }
        }
    }

    @FXML
    public void actualizarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty()  || telefono.isEmpty() || correo.isEmpty()) {
                System.out.println("Por favor complete todos los campos.");
            } else {
                clienteSeleccionado.setNombre(nombre);
                clienteSeleccionado.setApellido(apellido);
                clienteSeleccionado.setTelefono(telefono);
                clienteSeleccionado.setCorreo(correo);

                tablaClientes.refresh(); // Refresca la tabla para mostrar cambios
            }
        } else {
            System.out.println("Seleccione un cliente para actualizar.");
        }
    }


    @FXML
    public void eliminarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            clientes.remove(clienteSeleccionado);
            inscripcionManager.getInscripciones().removeIf(ins -> ins.getCliente().getCedula().equals(clienteSeleccionado.getCedula()));
        } else {
            System.out.println("Seleccione un cliente para eliminar.");
        }
    }

    @FXML
    public void verCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("VerClienteView.fxml"));
                Parent root = loader.load();
                
                VerClienteController controller = loader.getController();
                
                // Encontrar el curso del cliente
                String cursoCliente = "";
                for (Inscripcion ins : inscripcionManager.getInscripciones()) {
                    if (ins.getCliente().getCedula().equals(clienteSeleccionado.getCedula())) {
                        cursoCliente = ins.getCurso().getNombre();
                        break;
                    }
                }
                
                controller.setDatosCliente(clienteSeleccionado, cursoCliente);
                
                Stage stage = new Stage();
                stage.setTitle("Detalles del Cliente");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al cargar la ventana de detalles");
            }
        } else {
            System.out.println("Por favor seleccione un cliente para ver sus detalles");
        }
    }

    @FXML
    public void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtCedula.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        combxCurso.getSelectionModel().clearSelection();
    }

    public ArrayList<Curso> getCursos() {

        ArrayList<Curso> cursos = new ArrayList<>();

        Curso curso1 = new Curso(1, "Curso de Conducción Servicio Publico", "Incluye teoría y práctica", 30, 500000);
        Curso curso2 = new Curso(2, "Curso de Conducción Moto A1", "Para motos hasta 125cc, incluye clases prácticas", 20, 400000);
        Curso curso3 = new Curso(3, "Curso de Conducción Moto B1", "Para motos mayores a 125cc, clases avanzadas", 25, 450000);
        Curso curso4 = new Curso(4, "Curso de Conducción Carro A1", "Para automóviles particulares, incluye simulador", 35, 600000);
        Curso curso5 = new Curso(5, "Curso de Conducción Carro B1", "Vehículos de servicio público, clases intensivas", 40, 700000);

        cursos.add(curso1);
        cursos.add(curso2);
        cursos.add(curso3);
        cursos.add(curso4);
        cursos.add(curso5);

        return cursos;
    }
    public boolean validarDocumento(String documento) {

        List<Inscripcion> listaInscripciones = inscripcionManager.getInscripciones();
        Inscripcion in = new Inscripcion();

        for(Inscripcion inscripcion : listaInscripciones){
            in = inscripcion;
            if(in.getCliente().getCedula().equals(documento)){
                return true;
            }
        }
        return false;
    }
}