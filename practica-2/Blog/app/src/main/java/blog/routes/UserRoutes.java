package blog.routes;

import blog.controllers.UserController;
import io.javalin.Javalin;

public class UserRoutes {
    public static void configure(Javalin app) {
        app.get("/users", UserController::getAllUsers);
        app.get("/user/{userId}", UserController::getUserById);
        app.post("/login", UserController::loginUser);
        app.get("/logout", UserController::logOutUser);
        app.post("/create-user", UserController::createUser);
        app.put("/update-user/{id}", UserController::updateUser);
        app.delete("/delete-user/{id}", UserController::deleteUser);
    }
}
