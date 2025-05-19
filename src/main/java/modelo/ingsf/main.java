package modelo.ingsf;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {

        InscripcionManager inscripcionManager = InscripcionManager.getInstancia();
        inscripcionManager = prueba();


        //inscripcionManager.mostrarInscripciones();
        List<Inscripcion> i = inscripcionManager.getInscripciones();




        Inscripcion inscripcion = BuscarInscripcion(i, "108");
        inscripcion.mostrarDetalle();
        System.out.println();System.out.println();
        inscripcion = Pago(inscripcion, "123", "123");
        inscripcion.mostrarDetalle();


    }

    public static  InscripcionManager prueba(){
        Cliente cliente2 = new Cliente( "Jero", "Gómez", "108", "3111234567", "laura@email.com");
        Cliente cliente1 = new Cliente( "Laura", "Gómez", "12345678", "3111234567", "laura@email.com");
        Curso curso1 = new Curso(1, "Curso Básico de Conducción", "Incluye teoría y práctica", 30, 500000);

        // Crear una inscripción y agregarla al manager de inscripciones
        Inscripcion inscripcion1 = new Inscripcion(1, cliente2, curso1);
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
    public static Inscripcion Pago(Inscripcion inscripcion,String cedula, String pass){

        Admin admin = new Admin();

        if(cedula.equals(admin.getCedula()) && pass.equals(admin.getPassword())){
            inscripcion.setEstado("SUCCES");
            inscripcion.getFactura().setEstado("PAGADO");
            return inscripcion;
        }else{
            return inscripcion;
        }

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
