package blog.routes;

import blog.controllers.ArticleController;
import io.javalin.Javalin;

public class ArticleRoutes {
    public static void configure(Javalin app) {

        ArticleController articleController = new ArticleController();
        app.get("/articles/page/{page}", ctx -> {
            int pageNumber = Integer.parseInt(ctx.pathParam("page"));
        
            // Llama al método getAllArticles() pasando el objeto Context, el número de página y el tamaño de la página
            articleController.getAllArticles(ctx, pageNumber);
        });
        app.get("/article/{articleId}", ctx -> articleController.getArticleById(ctx));
        app.post("/create-article", ctx -> articleController.createArticle(ctx));
        app.put("/update-article/{id}", ctx -> articleController.updateArticle(ctx));
        app.delete("/delete-article/{id}", ctx -> articleController.deleteArticle(ctx));
        app.get("/user-articles/{userId}", ctx -> articleController.getAutorArticles(ctx));
    }
}
        

