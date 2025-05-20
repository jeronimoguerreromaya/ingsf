package modelo.ingsf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {

        InscripcionManager manager = InscripcionManager.getInstancia();
        manager.cargarInscripciones();
        int x = manager.getInscripciones().size();
        System.out.println(manager.getInscripciones().size() + " inscripciones encontradas.");
        System.out.println(manager.getInscripciones().get(0).getCliente().getNombre());








    }
    public void cargarInscripciones() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("inscripciones.dat"))) {
            List<Inscripcion> inscripciones = (List<Inscripcion>) ois.readObject();
            System.out.println("Inscripciones cargadas correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar las inscripciones: " + e.getMessage());
        }
    }


    public static  InscripcionManager prueba(){
        // Crear clientes
        Cliente cliente1 = new Cliente("Laura", "Gómez", "12345678", "3111234567", "laura@email.com");
        Cliente cliente2 = new Cliente("Jero", "Gómez", "108", "3111234567", "jero@email.com");
        Cliente cliente3 = new Cliente("Andrés", "Ramírez", "207", "3127654321", "andres@email.com");
        Cliente cliente4 = new Cliente("Sofía", "Martínez", "309", "3101112233", "sofia@email.com");
        Cliente cliente5 = new Cliente("Mateo", "Ríos", "410", "3149876543", "mateo@email.com");

        Curso curso1 = new Curso(1, "Curso de Conducción Servicio Publico", "Incluye teoría y práctica", 30, 500000);
        Curso curso2 = new Curso(2, "Curso de Conducción Moto A1", "Para motos hasta 125cc, incluye clases prácticas", 20, 400000);
        Curso curso3 = new Curso(3, "Curso de Conducción Moto B1", "Para motos mayores a 125cc, clases avanzadas", 25, 450000);
        Curso curso4 = new Curso(4, "Curso de Conducción Carro A1", "Para automóviles particulares, incluye simulador", 35, 600000);
        Curso curso5 = new Curso(5, "Curso de Conducción Carro B1", "Vehículos de servicio público, clases intensivas", 40, 700000);

        // Crear una inscripción y agregarla al manager de inscripciones
        // Crear inscripciones
        Inscripcion ins1 = new Inscripcion(cliente1, curso1);
        Inscripcion ins2 = new Inscripcion(cliente2, curso3);
        Inscripcion ins3 = new Inscripcion(cliente3, curso2);
        Inscripcion ins4 = new Inscripcion(cliente4, curso5);
        Inscripcion ins5 = new Inscripcion(cliente5, curso4);

        // Agregar al manager
        InscripcionManager manager = InscripcionManager.getInstancia();
        manager.agregarInscripcion(ins1);
        manager.agregarInscripcion(ins2);
        manager.agregarInscripcion(ins3);
        manager.agregarInscripcion(ins4);
        manager.agregarInscripcion(ins5);

        manager.guardarInscripciones();
        System.out.println("hecho");
        return manager;
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
