package servidor.servicios;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbConexion {

    private static MongoDbConexion instance;
    private MongoClient mongoClient;
    private String DB_NOMBRE;

    private MongoDbConexion(){

    }

    public static MongoDbConexion getInstance(){
        if(instance == null){
            instance = new MongoDbConexion();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public MongoDatabase getBaseDatos(){

        if(mongoClient==null) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            String URL_MONGODB = "mongodb+srv://bryant:1atUfwr66FizQJp7@cluster0.cld5o7i.mongodb.net/?retryWrites=true&w=majority";
            DB_NOMBRE = "final_proyect_DB";
            mongoClient = MongoClients.create(URL_MONGODB);
        }

        //Retomando la conexión
        MongoDatabase database = mongoClient.getDatabase(DB_NOMBRE);
        database.runCommand(new Document("ping", 1));
        System.out.println("Pinged your deployment. You successfully connected to MongoDB!");

        //
        return database;
    }

    /**
     *
     */
    public void cerrar(){
        mongoClient.close();
        mongoClient = null;
    }

}
