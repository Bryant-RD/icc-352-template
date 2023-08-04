package blog.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import jakarta.persistence.*;
import jakarta.servlet.http.Cookie;

import org.jasypt.util.text.BasicTextEncryptor;

import blog.DB.DB;
import blog.DB.FotoServices;
import blog.DB.GestionDb;
import blog.encapsulaciones.Article;
import blog.encapsulaciones.Foto;
import blog.encapsulaciones.LogUser;
import blog.encapsulaciones.Message;
import blog.encapsulaciones.User;
import io.javalin.http.Context;


public class UserController {

    private GestionDb<User> gestionDb = GestionDb.getInstance(User.class);
    private FotoServices fotoServices = FotoServices.getInstancia();

    public void loginUser(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");
        boolean rememberMe = context.formParam("rememberMe") != null;

        if (username.isEmpty() || password.isEmpty()) {
            context.redirect("/login.html");
            return;
        }

        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);

        List<User> users = query.getResultList();


        if (users.isEmpty()) {
            context.redirect("/login.html");
            return;            
        }

        User user = users.get(0);

        context.sessionAttribute("userId", user.getUserId()); // Establecer un atributo de sesión


        if (rememberMe) {
            System.out.println("no hay cookie");
            BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
            textEncryptor.setPassword("clave_encriptacion");

            String encryptedUsername = textEncryptor.encrypt(user.getUserId().toString());

            Cookie cookie = new Cookie("rememberMe", encryptedUsername);
            cookie.setMaxAge(7 * 24 * 60 * 60); // Establece el tiempo de vida de la cookie en segundos (opcional)
            context.res().addCookie(cookie);
        } else {
            System.out.println("hay cookie");
            Cookie cookie = new Cookie("rememberMe", "");
            cookie.setMaxAge(0); 
        }

        context.redirect("/home.html");

    }


    public void logOutUser(Context context) {
        context.sessionAttribute("userId", null);
        
        Cookie rememberMeCookie = new Cookie("rememberMe", "");
        rememberMeCookie.setMaxAge(0);
        context.res().addCookie(rememberMeCookie);
        context.redirect("/homeLogOut.html");
        
    }
    
    public void getAllUsers(Context context) {
        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("SELECT u FROM User u", User.class);

        List<User> users = query.getResultList();


        Gson gson = new Gson();
        String json = gson.toJson(users);
        
        context.contentType("application/json").result(json);
    }
    
    public void getUserById(Context context) {

        String userId = context.pathParam("userId");

        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("select u from User u where u.userId like:userId");
        query.setParameter("userId", userId+"%");
        List<User> lista = query.getResultList();

		Gson gson = new Gson();
        System.out.println(lista);
        String json = gson.toJson(lista.get(0));

        context.contentType("application/json").result(json);
    }
    
    public void createUser(Context context) {

        Gson gson = new Gson();
        User newUser = gson.fromJson(context.body(), User.class);


        EntityManager em = gestionDb.getEntityManager();
        Query query = em.createQuery("select u from User u where u.username like: username");
        query.setParameter("username", newUser.getUsername()+"%");
        List<User> aux = query.getResultList();

        
        if (aux.size() == 0) {


            UUID uuid = UUID.randomUUID();

            String id = uuid.toString();

            newUser.setUserId(id);

            EntityManager entityManager = gestionDb.getEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();
            
            entityManager.persist(newUser);
        
            transaction.commit();

            String json = gson.toJson(newUser);
            System.out.println(json);

            System.out.println("registrado");

            context.contentType("application/json").result(json);

        } else {
            Message msg = new Message("Error", "Error al regitrar usuario");
            String json = gson.toJson(msg);

            context.contentType("application/json").result(json);
        } 

    }
    
    public void updateUser(Context context) {
        Gson gson = new Gson();
        User newUser = gson.fromJson(context.body(), User.class);

        EntityManager entityManager = gestionDb.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            User existinUser = entityManager.find(User.class, newUser.getUserId());
            if (existinUser != null) {
                existinUser.setNombre(newUser.getNombre());
                existinUser.setUsername(newUser.getUsername());
                existinUser.setFoto(newUser.getFoto());
                existinUser.setPassword(newUser.getPassword());
                

                // Actualizar otras propiedades según corresponda

                entityManager.merge(existinUser);
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

    public void deleteUser(Context context) {
        String userId = context.pathParam("id");

        EntityManager entityManager = gestionDb.getEntityManager();

        User user = entityManager.find(User.class, userId);

        if (user != null) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.remove(user);

            transaction.commit();
        }
    }




}
