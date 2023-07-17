package blog.encapsulaciones;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id_user")
    private String userId;
    @Column(name = "user_name")
    private String username;

    private String nombre;

    private String password;
  
    private String rol;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id")
    private Foto foto;

    public User() {

    }

    public User(String userId, String username, String nombre, String password, String rol) {
        this.userId = userId;
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

}
