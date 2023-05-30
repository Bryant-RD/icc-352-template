package blog.controllers;

import java.net.ConnectException;
import java.util.Date;
import java.util.ArrayList;

import com.google.gson.Gson;

import blog.DB.DB;
import blog.encapsulaciones.Article;
import blog.encapsulaciones.Message;
import blog.encapsulaciones.Tag;
import io.javalin.http.Context;

public class ArticleController {

    public static void getAllArticles(Context context) {
        ArrayList<Article> articles = DB.initDB().getArticles();

        Gson gson = new Gson();
        String json = gson.toJson(articles);
        
        context.contentType("application/json").result(json);
    }

    public static void getArticleById(Context context) {
        Article article = DB.initDB().getJustArticleByid(Long.parseLong(context.pathParam("id")));
		Gson gson = new Gson();
        String json = gson.toJson(article);

        context.contentType("application/json").result(json);
    }

    public static void createArticle(Context context) {
        
        Gson gson = new Gson();

        Article article = gson.fromJson(context.body(), Article.class);

        DB.initDB().getArticles().add(article);

        Message msg = new Message("Success", "Artitulo registrado correctamente");

        String json = gson.toJson(msg);

        context.contentType("application/json").result(json);

    }

    public static void getAutorArticles(Context context) {

        String userId = context.pathParam("userId");

        ArrayList<Article> articles = DB.initDB().getArticlesByAutorId(userId);
        
        Gson gson = new Gson();
        String json = gson.toJson(articles);
        
        context.contentType("application/json").result(json);

    }

    public static void updateArticle(Context context) {
        
        Gson gson = new Gson();
        Article newArticle = gson.fromJson(context.body(), Article.class);

        Article oldArticle = DB.initDB().getJustArticleByid(newArticle.getId());
        ArrayList<Tag> filtredTags = DB.initDB().removeDuplicateTags(oldArticle.getEtiquetas(), newArticle.getEtiquetas());


        oldArticle.setTitulo(newArticle.getTitulo());
        oldArticle.setCuerpo(newArticle.getCuerpo());
        oldArticle.setEtiquetas(filtredTags);
        

    }
    
    
}
