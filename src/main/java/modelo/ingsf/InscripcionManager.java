package modelo.ingsf;

import java.util.ArrayList;
import java.util.List;

public class InscripcionManager {
    private static InscripcionManager instancia;
    private List<Inscripcion> inscripciones;

    private InscripcionManager() {
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
        }
    }
}