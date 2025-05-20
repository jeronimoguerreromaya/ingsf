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
import java.util.Optional;


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

        inscripcionManager =  InscripcionManager.getInstancia();
        inscripcionManager.cargarInscripciones();

        System.out.println(inscripcionManager.getInscripciones().size() + " inscripciones encontradas.");
        List<Inscripcion> listaInscripciones = inscripcionManager.getInscripciones();

        for (Inscripcion inscripcion : listaInscripciones) {
            clientes.add(inscripcion.getCliente());
        }

        comboEstado.getItems().addAll("Inscripto", "Pendiente");


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



        combxCurso.getItems().addAll("Moto A1","Moto A2","Carro B1","Carro B2","Moto y carro");
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

        if (nombre.isEmpty() || apellido.isEmpty() || cedula.isEmpty() || telefono.isEmpty() || correo.isEmpty() || seleccion == null || seleccion.trim().isEmpty() ) {
            System.out.println("Campos Vacios");
            mostrarAlerta("Todos los campos son obligatorios.");
        }else{
            if(!validarDocumento(cedula)){
                Cliente cliente = new Cliente(nombre, apellido, cedula, telefono, correo);
                Inscripcion inscripcion = new Inscripcion( cliente, cursos.get(0));
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
                inscripcionManager.guardarInscripciones();
                clientes.add(inscripcion.getCliente());
                mostrarAlerta("Registro exitoso");
            }else{
                System.out.println("Documento ya registrado");
                mostrarAlerta("Documento ya Registrado.");
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

            boolean cambiosRealizados = false;

            if (!nombre.isEmpty() && !nombre.equals(clienteSeleccionado.getNombre())) {
                clienteSeleccionado.setNombre(nombre);
                cambiosRealizados = true;
            }
            if (!apellido.isEmpty() && !apellido.equals(clienteSeleccionado.getApellido())) {
                clienteSeleccionado.setApellido(apellido);
                cambiosRealizados = true;
            }
            if (!telefono.isEmpty() && !telefono.equals(clienteSeleccionado.getTelefono())) {
                clienteSeleccionado.setTelefono(telefono);
                cambiosRealizados = true;
            }
            if (!correo.isEmpty() && !correo.equals(clienteSeleccionado.getCorreo())) {
                clienteSeleccionado.setCorreo(correo);
                cambiosRealizados = true;
            }

            if (cambiosRealizados) {
                // Actualizar en el manager
                for (Inscripcion inscripcion : inscripcionManager.getInscripciones()) {
                    if (inscripcion.getCliente().getCedula().equals(clienteSeleccionado.getCedula())) {
                        if (!nombre.isEmpty()) inscripcion.getCliente().setNombre(nombre);
                        if (!apellido.isEmpty()) inscripcion.getCliente().setApellido(apellido);
                        if (!telefono.isEmpty()) inscripcion.getCliente().setTelefono(telefono);
                        if (!correo.isEmpty()) inscripcion.getCliente().setCorreo(correo);
                    }
                }

                inscripcionManager.guardarInscripciones();
                tablaClientes.refresh();
                mostrarAlerta("Cliente actualizado correctamente.");
            } else {
                mostrarAlerta("No se realizaron cambios. Todos los campos estaban vacíos o iguales.");
            }
        } else {
            mostrarAlerta("Seleccione un cliente para actualizar.");
        }
    }



    @FXML
    public void eliminarCliente() {
        Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado != null) {
            // Confirmación de eliminación (opcional)
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmación");
            confirmacion.setHeaderText("¿Está seguro de que desea eliminar este cliente?");
            confirmacion.setContentText("Cliente: " + clienteSeleccionado.getNombre());

            Optional<ButtonType> resultado = confirmacion.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                // Eliminar el cliente de la lista observable
                clientes.remove(clienteSeleccionado);

                // Eliminar inscripciones asociadas al cliente por cédula
                inscripcionManager.getInscripciones().removeIf(ins ->
                        ins.getCliente().getCedula().equals(clienteSeleccionado.getCedula())
                );
                inscripcionManager.guardarInscripciones();
                mostrarAlerta("Cliente y sus inscripciones eliminados correctamente.");
            }
        } else {
            System.out.println("Seleccione un cliente para eliminar.");
            mostrarAlerta("Seleccione un cliente para eliminar.");
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

                Inscripcion inscripcionCliente = null;
                for (Inscripcion ins : inscripcionManager.getInscripciones()) {
                    if (ins.getCliente().getCedula().equals(clienteSeleccionado.getCedula())) {
                        inscripcionCliente = ins;
                        break;
                    }
                }
                controller.setDatosCliente(clienteSeleccionado, cursoCliente, inscripcionCliente, inscripcionManager);

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
            mostrarAlerta("Por favor seleccione un cliente para ver sus detalles");
        }
    }
    @FXML
    public void Buscar() {
        String nombreBusqueda = txtNombre.getText().trim().toLowerCase();
        String apellidoBusqueda = txtApellido.getText().trim().toLowerCase();
        String cedulaBusqueda = txtCedula.getText().trim();
        String telefonoBusqueda = txtTelefono.getText().trim();
        String correoBusqueda = txtCorreo.getText().trim().toLowerCase();
        String estadoBusqueda = comboEstado.getValue();

        // Crear una lista temporal para almacenar los resultados filtrados
        ObservableList<Cliente> clientesFiltrados = FXCollections.observableArrayList();

        // Iterar sobre las inscripciones para obtener clientes con sus estados
        for (Inscripcion inscripcion : inscripcionManager.getInscripciones()) {
            Cliente cliente = inscripcion.getCliente();
            boolean coincide = true;

            // Verificar cada campo solo si se ha ingresado un valor de búsqueda
            if (!nombreBusqueda.isEmpty() && !cliente.getNombre().toLowerCase().contains(nombreBusqueda)) {
                coincide = false;
            }
            if (!apellidoBusqueda.isEmpty() && !cliente.getApellido().toLowerCase().contains(apellidoBusqueda)) {
                coincide = false;
            }
            if (!cedulaBusqueda.isEmpty() && !cliente.getCedula().contains(cedulaBusqueda)) {
                coincide = false;
            }
            if (!telefonoBusqueda.isEmpty() && !cliente.getTelefono().contains(telefonoBusqueda)) {
                coincide = false;
            }
            if (!correoBusqueda.isEmpty() && !cliente.getCorreo().toLowerCase().contains(correoBusqueda)) {
                coincide = false;
            }
            if (estadoBusqueda != null && !estadoBusqueda.isEmpty() &&
                    !inscripcion.getEstado().equals(estadoBusqueda)) {
                coincide = false;
            }

            // Si todos los criterios coinciden, agregar el cliente a la lista filtrada
            if (coincide && !clientesFiltrados.contains(cliente)) {
                clientesFiltrados.add(cliente);
            }
        }

        // Actualizar la tabla con los resultados filtrados
        tablaClientes.setItems(clientesFiltrados);
    }
    @FXML
    public void verCursos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ingsf/cursos-view.fxml"));
            Parent root = loader.load();

            // Obtener el controlador para pasar la lista de cursos
            VerCursosController controladorCursos = loader.getController();

            // Aquí obtienes la lista real de cursos o una simulada
            List<Curso> listaDeCursos = obtenerCursos();  // <- Implementa este método o usa tu lista

            // Pasar la lista al controlador
            controladorCursos.setCursos(listaDeCursos);

            // Crear y mostrar la ventana
            Stage stage = new Stage();
            stage.setTitle("Gestión de Cursos");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al cargar la ventana de cursos: " + e.getMessage());
            alert.showAndWait();
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
        comboEstado.getSelectionModel().clearSelection(); // Agregar esta línea
    }


    public ArrayList<Curso> getCursos() {

        ArrayList<Curso> cursos = new ArrayList<>();

        Curso curso1 = new Curso(1, "Curso de Moto y Carro", "Incluye teoría y práctica", 60, 800000);
        Curso curso2 = new Curso(2, "Curso de Conducción Moto A1", "Para motos hasta 125cc, incluye clases prácticas", 40, 400000);
        Curso curso3 = new Curso(3, "Curso de Conducción Moto B1", "Para motos mayores a 125cc, clases avanzadas", 45, 450000);
        Curso curso4 = new Curso(4, "Curso de Conducción Carro A1", "Para automóviles particulares, incluye simulador", 45, 600000);
        Curso curso5 = new Curso(5, "Curso de Conducción Carro B1", "Vehículos de servicio público, clases intensivas", 40, 700000);

        cursos.add(curso1);
        cursos.add(curso2);
        cursos.add(curso3);
        cursos.add(curso4);
        cursos.add(curso5);

        return cursos;
    }
    private List<Curso> obtenerCursos() {
        List<Curso> cursos = new ArrayList<>();


        Curso curso1 = new Curso(1, "Curso de Moto y Carro", "Incluye teoría y práctica", 60, 800000);
        Curso curso2 = new Curso(2, "Curso de Conducción Moto A1", "Para motos hasta 125cc, incluye clases prácticas", 40, 400000);
        Curso curso3 = new Curso(3, "Curso de Conducción Moto B1", "Para motos mayores a 125cc, clases avanzadas", 45, 450000);
        Curso curso4 = new Curso(4, "Curso de Conducción Carro A1", "Para automóviles particulares, incluye simulador", 45, 600000);
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
    public static void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Alerta");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}