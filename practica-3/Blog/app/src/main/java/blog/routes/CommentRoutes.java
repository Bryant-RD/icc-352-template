package blog.routes;


import blog.controllers.CommentController;
import io.javalin.Javalin;

public class CommentRoutes {
        public static void configure(Javalin app) {

            CommentController commentController = new CommentController();
        // app.get("/commets", CommentController::getAllCommets);

        // app.get("/commet/{idArticle}", CommentController::getCommetsByArticle);
        app.post("/create-comment", ctx -> commentController.createComment(ctx));
        // app.put("/update-comment", CommentController::updateComment);
        // app.delete("/delete-comment/{id}", CommentController::deleteComment);

        // app.get("/user-articles/{userId}", CommentController::getAutorArticles);
    }
}
