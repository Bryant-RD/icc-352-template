package servidor.entidades;

public class Registro {
    
    private String id_registro;
    private String username;
    private String accion;
    private String fecha;

    public Registro() {}

    public Registro(String id_registro, String username, String accion, String fecha) {

        this.id_registro = id_registro;
        this.username = username;
        this.accion = accion;
        this.fecha = fecha;
    }

    public String getId_registro() {
        return id_registro;
    }
    public void setId_registro(String id_registro) {
        this.id_registro = id_registro;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccion() {
        return accion;
    }
    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
