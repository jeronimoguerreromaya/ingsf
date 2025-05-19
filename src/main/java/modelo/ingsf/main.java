package modelo.ingsf;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {

        InscripcionManager inscripcionManager = InscripcionManager.getInstancia();
        inscripcionManager = prueba();


        //inscripcionManager.mostrarInscripciones();
        List<Inscripcion> i = inscripcionManager.getInscripciones();
        System.out.println(i.size());
        Inscripcion in = i.get(0);
        Cliente cliente = in.getCliente();
        System.out.printf(cliente.getNombreCompleto());

        for(Inscripcion inscripcion : i){
             in = inscripcion;
            if(in.getCliente().getCedula().equals("Aca va la ceudla")){

            }
        }
    }

    public static  InscripcionManager prueba(){
        Cliente cliente1 = new Cliente( "Laura", "Gómez", "12345678", "3111234567", "laura@email.com");
        Curso curso1 = new Curso(1, "Curso Básico de Conducción", "Incluye teoría y práctica", 30, 500000);

        // Crear una inscripción y agregarla al manager de inscripciones
        Inscripcion inscripcion1 = new Inscripcion(1, cliente1, curso1);
        Inscripcion inscripcion2 = new Inscripcion(2, cliente1, curso1);
        Inscripcion inscripcion3 = new Inscripcion(3, cliente1, curso1);
        Inscripcion inscripcion4 = new Inscripcion(4, cliente1, curso1);

        InscripcionManager inscripcionManager = InscripcionManager.getInstancia();
        inscripcionManager.agregarInscripcion(inscripcion1);
        inscripcionManager.agregarInscripcion(inscripcion2);
        inscripcionManager.agregarInscripcion(inscripcion3);
        inscripcionManager.agregarInscripcion(inscripcion4);

        return inscripcionManager;
    }
}
