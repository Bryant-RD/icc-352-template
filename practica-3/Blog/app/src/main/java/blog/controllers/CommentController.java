package blog.controllers;

import java.util.List;

import com.google.gson.Gson;
import jakarta.persistence.*;

import blog.DB.GestionDb;
import blog.encapsulaciones.Article;
import blog.encapsulaciones.Comment;
import blog.encapsulaciones.Message;
import io.javalin.http.Context;

public class CommentController {

    private GestionDb<Comment> gestionDb = GestionDb.getInstance(Comment.class);

    public void getCommetsByArticle(Context context) {
        Long idArticle = Long.parseLong(context.pathParam("idArticle"));

        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("select c from comments a where a.id_articulo like :idArticle");
        query.setParameter("userId", idArticle+"%");
        List<Comment> lista = query.getResultList();
        

        
        Gson gson = new Gson();
        String json = gson.toJson(lista);
        
        context.contentType("application/json").result(json);
    }

    public void createComment(Context context) {
        Gson gson = new Gson();

        Comment comment = gson.fromJson(context.body(), Comment.class);

        EntityManager entityManager = gestionDb.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(comment);

        transaction.commit();

        Message msg = new Message("Success", "comentario agregado correctamente");

        String json = gson.toJson(msg);

        context.contentType("application/json").result(json);
    }

    public void updateComment(Context context) {
        Gson gson = new Gson();
        Comment newComment = gson.fromJson(context.body(), Comment.class);

        EntityManager entityManager = gestionDb.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(newComment);

        transaction.commit();
    }

    public void deleteComment(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("select c from Comment c where c.id_comment = :id");
        query.setParameter("id", id);
        List<Comment> lista = query.getResultList();

        if (!lista.isEmpty()) {
            Comment aux = lista.get(0);

            EntityManager entityManager = gestionDb.getEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.remove(aux);

            transaction.commit();
        }
    }


    
}
