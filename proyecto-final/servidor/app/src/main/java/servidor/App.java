package servidor;

import io.javalin.Javalin;
import servidor.rutas.RutaEncuesta;
import servidor.rutas.RutaUsuario;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Javalin app = Javalin.create(config ->{
            //configurando los documentos estaticos.
            config.staticFiles.add(staticFileConfig -> {
                staticFileConfig.hostedPath = "/";
                staticFileConfig.directory = "/public";
                // staticFileConfig.location = Location.CLASSPATH;
                staticFileConfig.precompress=false;
                staticFileConfig.aliasCheck=null;
            });

            //Habilitando el CORS. Ver: https://javalin.io/plugins/cors#getting-started para más opciones.
            config.plugins.enableCors(corsContainer -> {
                corsContainer.add(corsPluginConfig -> {
                    corsPluginConfig.anyHost();
                });
            });
            config.plugins.enableRouteOverview("/");
        });

        app.start(8080);

        app.routes(() -> {
            RutaEncuesta.configure(app);
            RutaUsuario.configure(app);

            app.before("/", ctx -> {
                ctx.redirect("./index.html");
            });


        });

    }
}
