package modelo.ingsf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionManager  implements Serializable {
    private static final long serialVersionUID = 1L;
    private static InscripcionManager instancia;
    private List<Inscripcion> inscripciones;

    public InscripcionManager() {
        inscripciones = new ArrayList<>();
    }

    public static InscripcionManager getInstancia() {
        if (instancia == null) {
            instancia = new InscripcionManager();
        }
        return instancia;
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    public void mostrarInscripciones() {
        for (Inscripcion inscripcion : inscripciones) {
            inscripcion.mostrarDetalle();
        }
    }

    // Si quieres ver todas las facturas generadas
    public void mostrarFacturas() {
        for (Inscripcion inscripcion : inscripciones) {
            inscripcion.getFactura().mostrarFactura();
            inscripcion.getFactura().imprimirFactura();
        }
    }
    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    // ----- PERSISTENCIA -----

    public void guardarInscripciones() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ARCHIVO_DATOS"))) {
            oos.writeObject(inscripciones);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarInscripciones() {
        File archivo = new File("ARCHIVO_DATOS");
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                inscripciones = (List<Inscripcion>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            inscripciones = new ArrayList<>();
        }
    }
}