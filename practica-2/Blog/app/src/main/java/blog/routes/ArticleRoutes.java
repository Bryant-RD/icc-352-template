package blog.routes;

import blog.controllers.ArticleController;
import io.javalin.Javalin;

public class ArticleRoutes {
    public static void configure(Javalin app) {
        app.get("/articles", ArticleController::getAllArticles);
        app.get("/article/{id}", ArticleController::getArticleById);
        app.post("/create-article", ArticleController::createArticle);
        app.put("/update-article/{id}", ArticleController::updateArticle);
        app.delete("/delete-article/{id}", ArticleController::deleteArticle);
        app.get("/user-articles/{userId}", ArticleController::getAutorArticles);
    }
}
        

