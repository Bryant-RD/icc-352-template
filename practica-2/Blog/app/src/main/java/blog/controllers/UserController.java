package blog.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import blog.DB.DB;
import blog.encapsulaciones.Message;
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

        String username = context.formParam("username");
        String name = context.formParam("name");
        String password = context.formParam("password");

        String id = "1";

        User newUser = new User(id, username, name, password, false, true);
        
        if (DB.initDB().createUser(newUser) != null) {
            Gson gson = new Gson();
            String json = gson.toJson(newUser);

            System.out.println("registrado");

            context.contentType("application/json").result(json);
            context.redirect("/homeLogOut.html");

        } else {
            Message msg = new Message("Error", "Error al regitrar usuario");
            Gson gson = new Gson();
            String json = gson.toJson(msg);

            context.contentType("application/json").result(json);
        } 

    }
    
    public static void updateUser(Context context) {
        // Lógica para actualizar un usuario
        // ...
    }
    
    public static void deleteUser(Context context) {
        // Lógica para eliminar un usuario
        // ...
    }

}
