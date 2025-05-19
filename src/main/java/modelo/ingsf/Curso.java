package modelo.ingsf;

import java.io.Serializable;
import java.util.List;

public class Curso  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nombre;
    private String descripcion;
    private int duracion; // en horas
    private double precio;

    public Curso(int id, String nombre, String descripcion, int duracion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.precio = precio;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDuracion() {
        return duracion;
    }

    public double getPrecio() {
        return precio;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public static Inscripcion Pago(Inscripcion inscripcion,String cedula, String pass){
            inscripcion.setEstado("SUCCES");
            inscripcion.getFactura().setEstado("PAGADO");
            return inscripcion;
    }
    public static Inscripcion BuscarInscripcion(List<Inscripcion> inscripcion, String cedula){
        for(Inscripcion inscripcion1: inscripcion){
            if(inscripcion1.getCliente().getCedula().equals(cedula)){
                return inscripcion1;
            }
        }
        return null;
    }
}