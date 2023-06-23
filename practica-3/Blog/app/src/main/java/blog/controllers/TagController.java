package blog.controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.*;

import com.google.gson.Gson;

import blog.DB.GestionDb;
import blog.DTO.ArticleDTO;
import blog.encapsulaciones.Article;
import blog.encapsulaciones.Comment;
import blog.encapsulaciones.Tag;
import io.javalin.http.Context;

public class TagController {

    private GestionDb<Tag> gestionDb = GestionDb.getInstance(Tag.class);

    public void getAllTags(Context context) {
        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT t.etiqueta FROM Tag t", String.class);
        List<String> etiquetas = query.getResultList();

        Gson gson = new Gson();
        String json = gson.toJson(etiquetas);

        context.contentType("application/json").result(json);
    }

    public void getArticlesByTag(Context context) {

        String tag = context.pathParam("tag");
        System.out.println("ENTRE:"+ tag);

       try {
            EntityManager em = gestionDb.getEntityManager();
            Query query = em.createQuery("SELECT a FROM Article a JOIN a.etiquetas t WHERE t.etiqueta like :tag");
            query.setParameter("tag", tag+"%");
            List<Article> lista = query.getResultList();

            System.out.println("resultados crudos: " + lista.size());

            List<ArticleDTO> dtos = new ArrayList<ArticleDTO>();

            for(Article item : lista) {
                item.setEtiquetas(null);
                List<Tag> tags = getTagsByArticle(item.getId());
                List<Comment> comments = getCommentsByArticle(item.getId());

                dtos.add(new ArticleDTO(item, tags, comments));
                System.out.println("+1");
            }
            
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dtos);

            context.contentType("application/json").result(json);
       } catch (Exception e) {
        System.out.println(e);
       }
    }
    
        public List<Tag> getTagsByArticle(Long id) {
        EntityManager em = gestionDb.getEntityManager();
        TypedQuery<Tag> query = em.createQuery("SELECT t FROM Tag t WHERE t.articulo.id = :id", Tag.class);
        query.setParameter("id", id);
        List<Tag> lista = query.getResultList();
        em.close();
        return lista;
    }


    public List<Comment> getCommentsByArticle(Long id) {
        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("SELECT c FROM Comment c WHERE c.articulo.id = :id", Comment.class);
        query.setParameter("id", id);
        List<Comment> lista = query.getResultList();

        return lista;
    }
}
