package servidor.servicios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;

import servidor.entidades.Registro;
import servidor.entidades.Usuario;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private static UsuarioService instancia;
    private MongoDbConexion mongoDbConexion;



    /**
     * Constructor privado.
     */
    private UsuarioService(){
        //
        mongoDbConexion = MongoDbConexion.getInstance();
        mongoDbConexion.getBaseDatos();

    }

    public static UsuarioService getInstancia(){
        if(instancia==null){
            instancia = new UsuarioService();
        }
        return instancia;
    }



    public List<Usuario> listarUsuarios(){
        List<Usuario> lista = new ArrayList<>();

        MongoCollection<Document> usuarios = mongoDbConexion.getBaseDatos().getCollection("usuarios");

        //Consultando todos los elementos.
        MongoCursor<Document> iterator = usuarios.find().iterator();
        while (iterator.hasNext()){

            //obteniendo el documento
            Document next = iterator.next();

            //Encapsulando la información
            String id_user = next.getString("id_user");
            String username = next.getString("username");
            String nombre = next.getString("nombre");
            String password = next.getString("password");
            String rol = next.getString("rol");


            // Crear objeto Usuario
            Usuario usuario = new Usuario(id_user, username, nombre, password, rol);

            // Agregar a la lista
            lista.add(usuario);

        }
        //retornando...
        return lista;
    }

    public Usuario getUsuarioPorId(String id){
        Usuario usuario = null;
        //Conexion a Mongo.
        MongoCollection<Document> usuarios = mongoDbConexion.getBaseDatos().getCollection("usuarios");

        //
        Document filtro = new Document("id_user", id);
        Document result = usuarios.find(filtro).first();

        //si no fue encontrado retorna null.
        if(result!=null){
            String id_user = result.getString("id_user");
            String username = result.getString("username");
            String nombre = result.getString("nombre");
            String password = result.getString("password");
            String rol = result.getString("rol");

            // Crear objeto Usuario
            usuario = new Usuario(id_user, username, nombre, password, rol);
        }

        //retornando.
        return usuario;
    }

    public Usuario getUsuarioPorUsername(String username) {
        Usuario usuario = null;
        // Conexión a Mongo.
        MongoCollection<Document> usuarios = mongoDbConexion.getBaseDatos().getCollection("usuarios");
    
        // Crear un filtro para buscar por el campo "username".
        Document filtro = new Document("username", username);
        Document result = usuarios.find(filtro).first();
    
        // Si se encuentra un resultado, crear un objeto Usuario.
        if (result != null) {
            String id_user = result.getString("id_user");
            String nombre = result.getString("nombre");
            String password = result.getString("password");
            String rol = result.getString("rol");
    
            // Crear objeto Usuario
            usuario = new Usuario(id_user, username, nombre, password, rol);
        }
    
        // Retornar el usuario encontrado o null si no se encontró.
        return usuario;
    }
    

    public Usuario crearUsuario(@NotNull Usuario usuario){
        if(getUsuarioPorId(usuario.getId_user()) != null){
            System.out.println("Estudiante registrado...");
            return null; //generar una excepcion...
        }

        System.out.println("Registrando usuario");
        System.out.println(usuario.getNombre());

        //
        Document document = new Document("id_user", usuario.getId_user())
            .append("username", usuario.getUserName())
            .append("nombre", usuario.getNombre())
            .append("password", usuario.getPassword())
            .append("rol", usuario.getRol());

        //
        MongoCollection<Document> usuarios = mongoDbConexion.getBaseDatos().getCollection("usuarios");
        //
        InsertOneResult insertOneResult = usuarios.insertOne(document);
        //
        System.out.println("Insertar: "+insertOneResult.getInsertedId()+", Acknowledged:"+insertOneResult.wasAcknowledged());

        return usuario;
    }

        public Registro crearRegistro(@NotNull Registro registro){
            System.out.println("\n\n" + registro.getUsername() + "\n\n" );
        //
        Document document = new Document("id_registro", registro.getId_registro())
            .append("username", registro.getUsername())
            .append("accion", registro.getAccion())
            .append("fecha", registro.getFecha());

        //
        MongoCollection<Document> registros = mongoDbConexion.getBaseDatos().getCollection("logs");
        //
        InsertOneResult insertOneResult = registros.insertOne(document);
        //
        System.out.println("Insertar: "+insertOneResult.getInsertedId()+", Acknowledged:"+insertOneResult.wasAcknowledged());

        return registro;
    }

    public Usuario actualizarUsuario(@NotNull Usuario usuario){
        Usuario tmp = getUsuarioPorId(usuario.getId_user());

        if(tmp == null){//no existe, no puede se actualizado
            System.out.println("El usuario no existe");
            return  null;
        }

        //Actualizando Documento.
        MongoCollection<Document> usuarios = mongoDbConexion.getBaseDatos().getCollection("usuarios");

        //
        Document filtro = new Document("id_user", usuario.getId_user());
        //
        //
        Document document = new Document("id_user", usuario.getId_user())
            .append("username", usuario.getUserName())
            .append("nombre", usuario.getNombre())
            .append("password", usuario.getPassword())
            .append("rol", usuario.getRol());
        //
        usuarios.findOneAndUpdate(filtro, new Document("$set", document));

        return usuario;
    }

    public boolean eliminandoUsuario(String id){
        //
        Usuario usuarioById = getUsuarioPorId(id);
        //Actualizando Documento.
        MongoCollection<Document> usuarios = mongoDbConexion.getBaseDatos().getCollection("usuarios");
        //
        Document filtro = new Document("id_user", usuarioById.getId_user());
        //
        return usuarios.findOneAndDelete(filtro) !=null;
    }

    
}
