package blog.encapsulaciones;

public class User {
    private String userId;
    private String username;
    private String nombre;
    private String password;
    private Boolean administrator;
    private boolean autor;

    public User(String userId, String username, String nombre, String password, Boolean administrator, boolean autor) {
        this.userId = userId;
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.administrator = administrator;
        this.autor = autor;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(Boolean administrator) {
        this.administrator = administrator;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }

}
