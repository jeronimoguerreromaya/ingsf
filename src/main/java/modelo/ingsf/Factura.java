package modelo.ingsf;

import java.time.LocalDate;

public class Factura {
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

    public void mostrarFactura() {
        System.out.println("Factura #" + id);
        System.out.println("Cliente: " + cliente.getNombreCompleto());
        System.out.println("Curso: " + curso.getNombre());
        System.out.println("Precio: $" + precio);
        System.out.println("Fecha: " + fecha);
        System.out.println("Estado: " + estado);
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