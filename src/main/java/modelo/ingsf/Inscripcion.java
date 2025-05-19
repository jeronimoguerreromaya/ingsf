package modelo.ingsf;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private LocalDate fechaInscripcion;
    private Cliente cliente;
    private Curso curso;
    private String estado;
    private Factura factura;  // Referencia a la factura generada


    public  Inscripcion(){

    }

    public Inscripcion( Cliente cliente, Curso curso) {
        this.id = Integer.parseInt(cliente.getCedula() + curso.getId());
        this.fechaInscripcion = LocalDate.now();
        this.cliente = cliente;
        this.curso = curso;
        this.estado = "Pendiente";  // Estado inicial
        this.factura = new Factura(id, cliente, curso);  // Generamos la factura al inscribir
    }

    public void mostrarDetalle() {
        System.out.println("Inscripción #" + id);
        System.out.println("Cliente: " + cliente.getNombreCompleto());
        System.out.println("Curso: " + curso.getNombre());
        System.out.println("Fecha de inscripción: " + fechaInscripcion);
        System.out.println("Estado: " + estado);
        factura.mostrarFactura();  // Mostrar también la factura
    }
    public void mostarFactura() {
        factura.mostrarFactura();
    }
    public Factura getFactura() {
        return factura;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public Curso getCurso() {
        return curso;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    // Métodos adicionales para cambiar estado, cancelar inscripción, etc.
}