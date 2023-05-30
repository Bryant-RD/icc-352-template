package blog.encapsulaciones;

public class Comment {
    

    private Long id;
    private String comentario;
    private User autor;
    private Article articulo;

    public Comment(Long id, String comentario, User autor, Article articulo) {
        id = this.id;
        comentario = this.comentario;
        autor = this.autor;
        articulo = this.articulo;
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
