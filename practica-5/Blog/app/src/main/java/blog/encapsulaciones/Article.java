package blog.encapsulaciones;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Article {

    @Id
    @Column(name = "id_article")
    private Long id;
    @Column
    private String titulo;
    @Column
    private String cuerpo;
    
    @OneToOne
    @JoinColumn(name = "id_user")
    private User autor;
    @Column
    private Date fecha;
    @OneToMany(mappedBy = "articulo")
    @JsonIgnore
    private List<Comment> comentarios;
    @OneToMany(mappedBy = "articulo")
    private List<Tag> etiquetas;

    public Article() {}

    public Article (Long id, String titulo, String cuerpo, User autor, Date fecha, List<Tag> etiquetas, List<Comment> comentarios) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
        this.comentarios = comentarios;
        this.etiquetas = etiquetas;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getCuerpo() {
        return cuerpo;
    }
    
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public User getAutor() {
        return autor;
    }
    
    public void setAutor(User autor) {
        this.autor = autor;
    }

    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Tag> getEtiquetas() {
        return etiquetas;
    }
    
    public void setEtiquetas(List<Tag> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public List<Comment> getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(List<Comment> comentarios) {
        this.comentarios = comentarios;
    }
     
}
