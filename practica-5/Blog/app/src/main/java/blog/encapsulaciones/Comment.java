package blog.encapsulaciones;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {
    
    @Id
    private Long id;
    @Column
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User autor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private  Article articulo;

    public Comment () {
    
    }

    public Comment(Long id, String comentario, User autor, Article articulo) {
        this.id = id;
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
    }


    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getcomentario() {
        return comentario;
    }
    
    public void setcomentario(String comentario) {
        this.comentario = comentario;
    }

    public User getAutor() {
        return autor;
    }
    
    public void setAutor(User autor) {
        this.autor = autor;
    }

    public Article getArticulo() {
        return articulo;
    }
    
    public void setTitulo(Article articulo) {
        this.articulo = articulo;
    }

}
