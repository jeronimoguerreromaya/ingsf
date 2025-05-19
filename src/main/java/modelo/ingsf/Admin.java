package modelo.ingsf;

public class Admin {

    private String nombre;
    private String apellido;
    private String cedula;
    private String password;

    public Admin(){
        this.cedula="123";
        this.password="123";
    }

    public String getCedula() {
        return cedula;
    }
    public String getPassword() {
        return password;
    }
}
