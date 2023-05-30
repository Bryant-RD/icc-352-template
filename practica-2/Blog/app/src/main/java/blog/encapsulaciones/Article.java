package blog.encapsulaciones;

import java.util.ArrayList;
import java.util.Date;

public class Article {

    private Long id;
    private String titulo;
    private String cuerpo;
    private User autor;
    private Date fecha;
    private ArrayList<Comment> comentarios;
    private ArrayList<Tag> etiquetas;

    public Article (Long id, String titulo, String cuerpo, User autor, Date fecha, ArrayList<Tag> etiquetas) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
        comentarios = new ArrayList<Comment>();
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

    public ArrayList<Tag> getEtiquetas() {
        return etiquetas;
    }
    
    public void setEtiquetas(ArrayList<Tag> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public ArrayList<Comment> getComentarios() {
        return comentarios;
    }
    
    public void setComentarios(ArrayList<Comment> comentarios) {
        this.comentarios = comentarios;
    }

     
}
