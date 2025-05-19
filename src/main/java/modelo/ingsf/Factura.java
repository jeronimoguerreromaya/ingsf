package modelo.ingsf;

import javafx.scene.control.Alert;

import java.io.Serializable;
import java.time.LocalDate;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Factura  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Cliente cliente;
    private Curso curso;
    private double precio;
    private LocalDate fecha;
    private String estado;

    public Factura(int id, Cliente cliente, Curso curso) {
        this.id = id;
        this.cliente = cliente;
        this.curso = curso;
        this.precio = curso.getPrecio();  // El precio del curso
        this.fecha = LocalDate.now();     // Fecha actual
        this.estado = "Pendiente";        // Inicialmente la factura está pendiente
    }

    // Método para mostrar la alerta con el mensaje
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método mostrarFactura modificado para mostrar la info en alerta
    public void mostrarFactura() {
        String facturaTexto = "Factura #" + cliente.getCedula() + "\n" +
                "Cliente: " + cliente.getNombreCompleto() + "\n" +
                "Curso: " + curso.getNombre() + "\n" +
                "Precio: $" + precio + "\n" +
                "Fecha: " + fecha + "\n" +
                "Estado: " + estado;

        mostrarAlerta("Detalle Factura", facturaTexto);
    }
    public void imprimirFactura() {
        String nombreArchivo = "Factura_" + cliente.getCedula() + ".txt";
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write("********** FACTURA **********\n");
            writer.write("Factura #" + id + "\n");
            writer.write("Cliente: " + cliente.getNombreCompleto() + "\n");
            writer.write("Curso: " + curso.getNombre() + "\n");
            writer.write("Precio: $" + precio + "\n");
            writer.write("Fecha: " + fecha.format(formatoFecha) + "\n");
            writer.write("Estado: " + estado + "\n");
            writer.write("******************************\n");

            System.out.println("Factura guardada en archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al generar el archivo de factura: " + e.getMessage());
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Curso getCurso() {
        return curso;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}