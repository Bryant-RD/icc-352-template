package blog.routes;

import blog.controllers.TagController;
import io.javalin.Javalin;

public class TagRoutes {
    public static void configure(Javalin app) {

        TagController tagController = new TagController();

        app.get("/tags", ctx -> tagController.getAllTags(ctx));
        app.get("/tag/articles/{tag}", ctx -> tagController.getArticlesByTag(ctx));
    }
}
