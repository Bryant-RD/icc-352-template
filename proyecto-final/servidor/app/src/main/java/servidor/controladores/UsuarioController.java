package servidor.controladores;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.javalin.http.Context;
import jakarta.servlet.http.Cookie;
import servidor.entidades.Registro;
import servidor.entidades.Usuario;
import servidor.servicios.UsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

public class UsuarioController {

    public static void getAllUsuarios(Context ctx) {

       
        List<Usuario> usuarios = UsuarioService.getInstancia().listarUsuarios();

        Gson gson = new Gson();
        String json = gson.toJson(usuarios);
        
        ctx.contentType("application/json").result(json);
    }

    public static void getUserById(Context ctx) {

        String id = ctx.pathParam("id_user");
        ctx.contentType("application/json").json(UsuarioService.getInstancia().getUsuarioPorId(id));
    }

    public static void createUser(Context ctx) {

        Gson gson = new Gson();
        Usuario newUser = gson.fromJson(ctx.body(), Usuario.class);

        ctx.contentType("application/json").json(UsuarioService.getInstancia().crearUsuario(newUser));

    }

    public static void updateUser(Context ctx) {

        Gson gson = new Gson();
        Usuario newUser = gson.fromJson(ctx.body(), Usuario.class);
        ctx.contentType("application/json").json(UsuarioService.getInstancia().actualizarUsuario(newUser));



    }

    public static void deleteUser(Context ctx) {

        String id = ctx.pathParam("id_user");
        ctx.contentType("application/json").json(UsuarioService.getInstancia().eliminandoUsuario(id));

    }

    public static void login(Context ctx) {

        String username = ctx.formParam("username");
        String password = ctx.formParam("password");

        if (username.isEmpty() || password.isEmpty()) {
            ctx.status(400).result("Faltan credenciales");
            return;
        }

        Usuario user = UsuarioService.getInstancia().getUsuarioPorUsername(username);

        if (user == null) {
            ctx.status(404).result("Usuario no encontrado");
        } else {
            if (user.getUserName().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)) {
                ctx.sessionAttribute("userId", user.getId_user()); // Establecer un atributo de sesión
                // Generar un token JWT
                String jwt = generarTokenJWT(user);

                Cookie cookie = new Cookie("jwt", jwt);

                // Almacenar el token JWT en una cookie o en el cuerpo de la respuesta, según lo desees
                ctx.res().addCookie(cookie);

                ctx.status(200).result(jwt);
            } else {
                ctx.result("false");
            }
        }
        
        
    }

    public static void logout(Context ctx) {

        ctx.sessionAttribute("userId", null);
        // ctx.cookie("jwt", null);
        ctx.status(200).result("Sesion cerrada correctamente");

    }


    private static String generarTokenJWT(Usuario usuario) {
        // Definir una clave secreta para firmar el token (debes protegerla adecuadamente en un entorno de producción)
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
        // Configurar el contenido del token
        String jwt = Jwts.builder()
                .setSubject(usuario.getId_user())
                .claim("username", usuario.getUserName())
                .claim("rol", usuario.getRol())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Caduca en 1 hora
                .signWith(key)
                .compact();
    
        return jwt;
    }


    public static void SincronizarLogins(Context ctx) {

        String jsonString = ctx.body();

        Gson gson = new Gson();

        ArrayList<Registro> registros = gson.fromJson(jsonString, new TypeToken<ArrayList<Registro>>(){}.getType());
        

        int correct = 0;

        for (int i = 0; i < registros.size(); i++) {
            
            Registro aux = UsuarioService.getInstancia().crearRegistro(registros.get(i));
            if (aux != null) {
                System.out.println("\n\n CORRECTO \n\n");
                correct++;
            } else {
                System.out.println("Error en el registro numero: " + i );
                ctx.contentType("application/json").result("{status: error, mesagge: error al guardar un registro}");
                
            }
        }

        if (registros.size() == correct) {
            System.out.println("Total de registros guardados correctamente: " + correct);
            ctx.contentType("application/json").result("{status: success, mesagge: Todos los registros guardados correctamente}");
        } else {
            ctx.contentType("application/json").result("{status: error, mesagge: error al guardar un registro}");
        }
    }

}
