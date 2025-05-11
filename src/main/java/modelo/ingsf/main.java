package modelo.ingsf;

public class main {
    public static void main(String[] args) {
        // Crear clientes y cursos
        Cliente cliente1 = new Cliente(1, "Laura", "Gómez", "12345678", "3111234567", "laura@email.com");
        Curso curso1 = new Curso(1, "Curso Básico de Conducción", "Incluye teoría y práctica", 30, 500000);

        // Crear una inscripción y agregarla al manager de inscripciones
        Inscripcion inscripcion1 = new Inscripcion(1, cliente1, curso1);

        // Obtener la instancia del InscripcionManager (Singleton) y agregar la inscripción
        InscripcionManager inscripcionManager = InscripcionManager.getInstancia();
        inscripcionManager.agregarInscripcion(inscripcion1);

        // Mostrar todas las inscripciones y sus facturas
        inscripcionManager.mostrarInscripciones();
        inscripcionManager.mostrarFacturas();
    }
}
