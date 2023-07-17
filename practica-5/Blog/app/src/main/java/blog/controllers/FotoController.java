package blog.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import blog.DB.FotoServices;
import blog.encapsulaciones.Foto;
import io.javalin.http.Context;

public class FotoController {

    private FotoServices fotoServices = FotoServices.getInstancia();

    public void getAllFotos(Context ctx) {
        List<Foto> fotos = fotoServices.findAll();

        ctx.result();
    }

    public void uploadFoto(Context context) {
        context.uploadedFiles("foto").forEach(uploadedFile -> {
            try {
                byte[] bytes = uploadedFile.content().readAllBytes();
                String encodedString = Base64.getEncoder().encodeToString(bytes);
                Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString);
                fotoServices.crear(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // context.redirect("/fotos/listar");
        });
    }

    public void getFotoById(Context context) {
        try {
            Foto foto = fotoServices.find(context.pathParamAsClass("id", Long.class).get());
            if(foto==null){
                context.redirect("/fotos/listar");
                return;
            }
            // Map<String, Object> modelo = new HashMap<>();
            // modelo.put("foto", foto);
            //             //
            // context.render("/templates/visualizar.html", modelo);
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
            // context.redirect("/fotos/listar");+
        }
    }

    public void createFoto(Context context) {
        context.uploadedFiles("foto").forEach(uploadedFile -> {
            try {
                byte[] bytes = uploadedFile.content().readAllBytes();
                String encodedString = Base64.getEncoder().encodeToString(bytes);
                Foto foto = new Foto(uploadedFile.filename(), uploadedFile.contentType(), encodedString);
                fotoServices.crear(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            context.redirect("/fotos/listar");
        });
    }

    public void deleteFoto(Context context) {
        try {
            Foto foto = fotoServices.find(context.pathParamAsClass("id", Long.class).get());
            if(foto!=null){
                fotoServices.eliminar(foto.getId());
            }
        }catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        // context.redirect("/fotos/listar");
    }
    
}
