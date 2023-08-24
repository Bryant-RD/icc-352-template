package servidor.rutas;

import io.javalin.Javalin;

import servidor.controladores.EncuestaController;

public class RutaEncuesta {
    public static void configure(Javalin app) {

        app.get("/registros", ctx -> EncuestaController.getAllEncuestas(ctx));
        app.get("/registro/{id}", ctx -> EncuestaController.getRegistroById(ctx));
        app.post("/sincronizar", ctx -> EncuestaController.SincronizarEncuestas(ctx));

    }
}
