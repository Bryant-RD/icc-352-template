package blog.routes;

import blog.controllers.FotoController;
import io.javalin.Javalin;

public class FotoRoutes {
        public static void configure(Javalin app) {

        FotoController fotoController = new FotoController();

        app.get("/fotos", ctx -> fotoController.getAllFotos(ctx));
        app.get("/foto/{id}", ctx -> fotoController.getFotoById(ctx));
        app.post("/procesarFoto", ctx -> fotoController.createFoto(ctx));
        app.get("/delete-foto", ctx -> fotoController.deleteFoto(ctx));
      
    }
}
