package blog.controllers;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import blog.DB.DB;
import blog.DB.GestionDb;
import blog.DTO.ArticleDTO;
import blog.encapsulaciones.Article;
import blog.encapsulaciones.Comment;
import blog.encapsulaciones.Message;
import blog.encapsulaciones.Tag;
import io.javalin.http.Context;

import jakarta.persistence.*;
import java.util.List;


public class ArticleController {

private GestionDb<Article> gestionDb = GestionDb.getInstance(Article.class);

public void getAllArticles(Context context, int pageNumber) {
    EntityManager entityManager = gestionDb.getEntityManager();
    int pageSize = 5;
    
    try {
        TypedQuery<Article> query = entityManager.createQuery("SELECT a FROM Article a", Article.class);
        
        // Calcula el índice del primer resultado
        int firstResult = (pageNumber - 1) * pageSize;
        
        // Establece el índice del primer resultado y el número máximo de resultados
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        
        List<Article> articles = query.getResultList();
        
        List<ArticleDTO> dtos = new ArrayList<ArticleDTO>();

        for(Article item : articles) {
            item.setEtiquetas(null);
            List<Tag> tags = getTagsByArticle(item.getId());
            List<Comment> comments = getCommentsByArticle(item.getId());

            dtos.add(new ArticleDTO(item, tags, comments));
        }

       
        
         ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dtos);
        
        context.contentType("application/json").result(json);
    } catch (Exception e) {
        // Manejo de excepciones
        System.out.println("Error al obtener los artículos: " + e.getMessage());
        Message msg = new Message("Error", "Error al obtener los artículos");
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        context.contentType("application/json").result(json);
    } finally {
        entityManager.close();
    }
}


    public void getArticleById(Context context) {
        // Article article = DB.initDB().getJustArticleByid(Long.parseLong(context.pathParam("id")));

        Long id = Long.parseLong(context.pathParam("articleId"));

       try {
         EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("select a from Article a where a.id = :id");
        query.setParameter("id", id);
        List<Article> lista = query.getResultList();

		List<Article> articles = query.getResultList();
        
        List<ArticleDTO> dtos = new ArrayList<ArticleDTO>();

        for(Article item : articles) {
            item.setEtiquetas(null);
            List<Tag> tags = getTagsByArticle(item.getId());
            List<Comment> comments = getCommentsByArticle(item.getId());

            dtos.add(new ArticleDTO(item, tags, comments));
        }

       ArticleDTO art = dtos.get(0);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(art);

        context.contentType("application/json").result(json);
       } catch (Exception e) {
        // TODO: handle exception
       }
    }

    public void createArticle(Context context) {
    Gson gson = new Gson();

    Article article = gson.fromJson(context.body(), Article.class);

    EntityManager entityManager = gestionDb.getEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    try {
        transaction.begin();
        List<Tag> tags = article.getEtiquetas();

        for (Tag tag : tags) {
            String etiqueta = tag.getEtiqueta();

            Query query = entityManager.createQuery("SELECT t FROM Tag t WHERE t.etiqueta = :etiqueta", Tag.class);
            query.setParameter("etiqueta", etiqueta);
            List<Tag> existingTags = query.getResultList();

            if (existingTags.isEmpty()) {
                tag.setArticulo(article); // Establecer la relación con el artículo
                entityManager.persist(tag);
            } else {
                // Si la etiqueta ya existe, asignarla directamente al artículo
                Tag existingTag = existingTags.get(0);
                tag.setArticulo(article);
                tag.setId(existingTag.getId());
            }
        }

        entityManager.persist(article);
        transaction.commit();

        Message msg = new Message("Success", "Artículo registrado correctamente");
        String json = gson.toJson(msg);
        context.contentType("application/json").result(json);
    } catch (Exception e) {
        if (transaction.isActive()) {
            transaction.rollback();
            System.out.println(e.toString());
        }

        Message msg = new Message("Error", "Error al registrar el artículo");
        String json = gson.toJson(msg);
        context.contentType("application/json").result(json);
    } finally {
        entityManager.close();
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


    public void getAutorArticles(Context context) {

        String userId = context.pathParam("userId");

        try {
        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("select a from Article a where a.autor.userId like :userId");
        query.setParameter("userId", userId+"%");
        
        List<Article> articles = query.getResultList();
        
        List<ArticleDTO> dtos = new ArrayList<ArticleDTO>();

        for(Article item : articles) {
            item.setEtiquetas(null);
            List<Tag> tags = getTagsByArticle(item.getId());
            List<Comment> comments = getCommentsByArticle(item.getId());

            dtos.add(new ArticleDTO(item, tags, comments));
        }


        
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dtos);
        
        context.contentType("application/json").result(json);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void updateArticle(Context context) {
        Gson gson = new Gson();
        Article newArticle = gson.fromJson(context.body(), Article.class);

        // System.out.println(gson.toJson(newArticle));

        EntityManager entityManager = gestionDb.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Article existingArticle = entityManager.find(Article.class, newArticle.getId());
            if (existingArticle != null) {
                existingArticle.setTitulo(newArticle.getTitulo());
                existingArticle.setCuerpo(newArticle.getCuerpo());
                existingArticle.setAutor(newArticle.getAutor());
                // Actualizar otras propiedades según corresponda

                System.out.println("ENTRO");

                entityManager.merge(existingArticle);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    public void deleteArticle(Context context) {
        Long id = Long.parseLong(context.pathParam("id"));

        EntityManager entityManager = gestionDb.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Article article = entityManager.find(Article.class, id);
            if (article != null) {
                entityManager.remove(article);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            entityManager.close();
        }
    }

    
    
}
