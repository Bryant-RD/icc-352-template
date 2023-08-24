package servidor.entidades;

public class Usuario {

    private String id_user;
    private String userName;
    private String nombre;
    private String password;
    private String rol;

    public Usuario() { }

    public Usuario(String id_user, String userName, String nombre, String password, String rol) {

        this.id_user = id_user;
        this.userName = userName;
        this.nombre = nombre;
        this. password = password;
        this.rol = rol;

    }

    public String getId_user() {
        return id_user;
    }
    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    

}
