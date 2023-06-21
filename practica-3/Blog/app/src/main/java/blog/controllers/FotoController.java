package blog.controllers;

import java.util.List;

import blog.DB.FotoServices;
import blog.encapsulaciones.Foto;
import io.javalin.http.Context;

public class FotoController {

    private FotoServices fotoServices = FotoServices.getInstancia();

    public void getAllFotos(Context ctx) {
        List<Foto> fotos = fotoServices.findAll();

        ctx.result();
    }
    
}
