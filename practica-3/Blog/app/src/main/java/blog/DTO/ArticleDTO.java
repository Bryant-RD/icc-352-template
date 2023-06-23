package blog.DTO;

import java.util.ArrayList;
import java.util.List;

import blog.encapsulaciones.Article;
import blog.encapsulaciones.Comment;
import blog.encapsulaciones.Tag;

public class ArticleDTO {

    private Article article;
    private List<Tag> etiquetas;
    private List<Comment> comentarios;


    public ArticleDTO(Article article, List<Tag> tags, List<Comment> comments ) {

        this.article = article;
        this.etiquetas = tags;
        this.comentarios = comments;
    }






    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
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
