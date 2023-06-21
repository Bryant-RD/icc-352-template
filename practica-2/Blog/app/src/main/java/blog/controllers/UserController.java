package blog.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.gson.Gson;

import blog.DB.DB;
import blog.encapsulaciones.Article;
import blog.encapsulaciones.Message;
import blog.encapsulaciones.Tag;
import blog.encapsulaciones.User;
import io.javalin.http.Context;


public class UserController {

    public static void loginUser(Context context) {
        String username = context.formParam("username");
        String password = context.formParam("password");

        if (username.isEmpty() || password.isEmpty()) {
            context.redirect("/login.html");
            return;
        }
        User user = DB.initDB().validatUser(username, password);
        if (user == null) {
            context.redirect("/login.html");
            return;            
        }
        context.sessionAttribute("userId", user.getUserId()); // Establecer un atributo de sesión
        context.redirect("/home.html");
    }

    public static void logOutUser(Context context) {
        context.sessionAttribute("userId", null); // Establecer un atributo de sesión
        context.redirect("/homeLogOut.html");
    }
    
    public static void getAllUsers(Context context) {
        ArrayList<User> users = DB.initDB().getUsers();


        Gson gson = new Gson();
        String json = gson.toJson(users);
        
        context.contentType("application/json").result(json);
    }
    
    public static void getUserById(Context context) {

        String userId = context.pathParam("userId");

        User user = DB.initDB().getUserById(userId);

		Gson gson = new Gson();
        String json = gson.toJson(user);

        context.contentType("application/json").result(json);
    }
    
    public static void createUser(Context context) {

        Gson gson = new Gson();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        User newUser = gson.fromJson(context.body(), User.class);
        newUser.setUserId(id);
        
        if (DB.initDB().createUser(newUser) != null) {

            String json = gson.toJson(newUser);

            System.out.println(json);

            context.contentType("application/json").result(json);
            context.sessionAttribute("userId", newUser.getUserId()); // Establecer un atributo de sesión
            

        } else {
            Message msg = new Message("Error", "Error al regitrar usuario");
            String json = gson.toJson(msg);

            context.contentType("application/json").result(json);
        } 

    }
    
    public static void updateUser(Context context) {

        Gson gson = new Gson();
        User newUser = gson.fromJson(context.body(), User.class);

       DB.initDB().updateUser(newUser);

        Message msg = new Message("SUCCESS", "Usuario modificado correctamente");
        String json = gson.toJson(msg);

        context.contentType("application/json").result(json);

    }
    
    public static void deleteUser(Context context) {
         Gson gson = new Gson();
        User user = gson.fromJson(context.body(), User.class);

       DB.initDB().delteUser(user);

        Message msg = new Message("SUCCESS", "Usuario eliminado correctamente");
        String json = gson.toJson(msg);

        context.contentType("application/json").result(json);
    }

}
