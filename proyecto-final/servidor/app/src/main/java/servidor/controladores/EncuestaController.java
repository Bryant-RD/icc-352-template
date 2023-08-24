package servidor.controladores;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

import org.jetbrains.annotations.NotNull;

import servidor.entidades.Encuesta;
import servidor.servicios.EncuestaService;
import servidor.servicios.UsuarioService;

public class EncuestaController {
    

    public static void getAllEncuestas(Context ctx) {

       
        List<Encuesta> encuestas = EncuestaService.getInstancia().listarEncuestas();

        Gson gson = new Gson();
        String json = gson.toJson(encuestas);
        
        ctx.contentType("application/json").result(json);
    }

    public static void SincronizarEncuestas(Context ctx) {

        String jsonString = ctx.body();

        Gson gson = new Gson();

        ArrayList<Encuesta> encuestas = gson.fromJson(jsonString, new TypeToken<ArrayList<Encuesta>>(){}.getType());
        

        int correct = 0;

        for (int i = 0; i < encuestas.size(); i++) {
            
            Encuesta aux = EncuestaService.getInstancia().crearEncuesta(encuestas.get(i));
            if (aux != null) {
                correct++;
            } else {
                System.out.println("Error en el registro numero: " + i );
                ctx.contentType("application/json").result("{status: error, mesagge: error al guardar un registro}");
                
            }
        }

        if (encuestas.size() == correct) {
            System.out.println("Total de registros guardados correctamente: " + correct);
            ctx.contentType("application/json").result("{status: success, mesagge: Todos los registros guardados correctamente}");
        } else {
            ctx.contentType("application/json").result("{status: error, mesagge: error al guardar un registro}");
        }
    }

    public static void getRegistroById(Context ctx) {

        String id = ctx.pathParam("id");
        ctx.contentType("application/json").json(EncuestaService.getInstancia().getEncuestaPorId(id));

    }

}
