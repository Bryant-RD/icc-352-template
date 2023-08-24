package servidor.rutas;

import io.javalin.Javalin;
import servidor.controladores.UsuarioController;


public class RutaUsuario {

    public static void configure(Javalin app) {

        app.get("/users", ctx -> UsuarioController.getAllUsuarios(ctx));
        app.get("/user/{id_user}", ctx -> UsuarioController.getUserById(ctx));
        app.post("/login", ctx -> UsuarioController.login(ctx));
        app.get("/logout", ctx -> UsuarioController.logout(ctx));
        app.post("/create-user", ctx -> UsuarioController.createUser(ctx));  
        app.put("/update-user/{id_user}", ctx -> UsuarioController.updateUser(ctx));
        app.delete("/delete-user/{id_user}", ctx -> UsuarioController.deleteUser(ctx));

    }    
}
