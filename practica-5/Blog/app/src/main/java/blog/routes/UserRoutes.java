package blog.routes;

import blog.controllers.UserController;
import io.javalin.Javalin;

public class UserRoutes {
    public static void configure(Javalin app) {

        UserController userController = new UserController();

        app.get("/users", ctx -> userController.getAllUsers(ctx));
        app.get("/user/{userId}", ctx -> userController.getUserById(ctx));
        app.post("/login", ctx -> userController.loginUser(ctx));
        app.get("/logout", ctx -> userController.logOutUser(ctx));
        app.post("/create-user", ctx -> userController.createUser(ctx));  
        app.put("/update-user/{id}", ctx -> userController.updateUser(ctx));
        app.delete("/delete-user/{id}", ctx -> userController.deleteUser(ctx));
    }
}
