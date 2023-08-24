package servidor.servicios;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;

import servidor.entidades.Coordenadas;
import servidor.entidades.Encuesta;

import java.util.ArrayList;
import java.util.List;

public class EncuestaService {
    

    private static EncuestaService instancia;
    private MongoDbConexion mongoDbConexion;



    /**
     * Constructor privado.
     */
    private EncuestaService(){
        //
        mongoDbConexion = MongoDbConexion.getInstance();
        mongoDbConexion.getBaseDatos();

    }

    public static EncuestaService getInstancia(){
        if(instancia==null){
            instancia = new EncuestaService();
        }
        return instancia;
    }



    public List<Encuesta> listarEncuestas(){
        List<Encuesta> lista = new ArrayList<>();

        MongoCollection<Document> encuestas = mongoDbConexion.getBaseDatos().getCollection("encuestas");

        //Consultando todos los elementos.
        MongoCursor<Document> iterator = encuestas.find().iterator();
        while (iterator.hasNext()){

            //obteniendo el documento
            Document next = iterator.next();

            //Encapsulando la información
            String id_encuesta = next.getString("id_encuesta");
            String id_encuestador = next.getString("id_encuestador");
            String nombre = next.getString("nombre");
            String sector = next.getString("sector");
            String nivel = next.getString("nivel");
            Document coordenadasDoc = next.get("coordenadas", Document.class);
            String latitud = coordenadasDoc.getString("latitud");
            String longitud = coordenadasDoc.getString("longitud");
            String foto = next.getString("foto");

            // Crear objeto Coordenadas
            Coordenadas coordenadas = new Coordenadas(latitud, longitud);

            // Crear objeto Encuesta
            Encuesta encuesta = new Encuesta(id_encuesta, id_encuestador, nombre, sector, nivel, coordenadas, foto);

            // Agregar a la lista
            lista.add(encuesta);

        }
        //retornando...
        return lista;
    }

    public Encuesta getEncuestaPorId(String id_encuesta){
        Encuesta encuesta = null;
        //Conexion a Mongo.
        MongoCollection<Document> encuestas = mongoDbConexion.getBaseDatos().getCollection("encuestas");

        //
        Document filtro = new Document("id_encuesta", id_encuesta);
        Document result = encuestas.find(filtro).first();

        //si no fue encontrado retorna null.
        if(result!=null){
            String id_encuestador = result.getString("id_encuestador");
            String nombre = result.getString("nombre");
            String sector = result.getString("sector");
            String nivel = result.getString("nivel");
            Document coordenadasDoc = result.get("coordenadas", Document.class);
            String latitud = coordenadasDoc.getString("latitud");
            String longitud = coordenadasDoc.getString("longitud");
            String foto = result.getString("foto");

            // Crear objeto Coordenadas
            Coordenadas coordenadas = new Coordenadas(latitud, longitud);

            // Crear objeto Encuesta
            encuesta = new Encuesta(id_encuesta, id_encuestador, nombre, sector, nivel, coordenadas, foto);
        }

        //retornando.
        return encuesta;
    }

    public Encuesta crearEncuesta(@NotNull Encuesta encuesta){
        if(getEncuestaPorId(encuesta.getIdEncuesta()) != null){
            System.out.println("Estudiante registrado...");
            return null; //generar una excepcion...
        }

        System.out.println("Registrando encuesta");

        //
        Document document = new Document("id_encuesta", encuesta.getIdEncuesta())
            .append("id_encuestador", encuesta.getIdEncuestador())
            .append("nombre", encuesta.getNombre())
            .append("sector", encuesta.getSector())
            .append("nivel", encuesta.getNivel())
            .append("coordenadas", new Document("latitud", encuesta.getCoordenadas().getLatitud())
                .append("longitud", encuesta.getCoordenadas().getLongitud()))
            .append("foto", encuesta.getFoto());

        //
        MongoCollection<Document> encuestas = mongoDbConexion.getBaseDatos().getCollection("encuestas");
        //
        InsertOneResult insertOneResult = encuestas.insertOne(document);
        //
        System.out.println("Insertar: "+insertOneResult.getInsertedId()+", Acknowledged:"+insertOneResult.wasAcknowledged());

        return encuesta;
    }

    // public Estudiante actualizarEstudiante(@NotNull Estudiante estudiante){
    //     Estudiante tmp = getEstudiantePorMatricula(estudiante.getMatricula());

    //     if(tmp == null){//no existe, no puede se actualizado
    //         throw new NoExisteEstudianteException("No Existe el estudiante: "+estudiante.getMatricula());
    //     }

    //     //Actualizando Documento.
    //     MongoCollection<Document> estudiantes = mongoDbConexion.getBaseDatos().getCollection(TablasMongo.ESTUDIANTES.getValor());

    //     //
    //     Document filtro = new Document("_id", new ObjectId(estudiante.getId()));
    //     //
    //     //
    //     Document document = new Document("matricula", estudiante.getMatricula())
    //             .append("nombre", estudiante.getNombre())
    //             .append("carrera", estudiante.getCarrera())
    //             .append("_id", new ObjectId(estudiante.getId()));
    //     //
    //     estudiantes.findOneAndUpdate(filtro, new Document("$set", document));

    //     return estudiante;
    // }

    public boolean eliminandoEncuesta(String id){
        //
        Encuesta encuestaById = getEncuestaPorId(id);
        //Actualizando Documento.
        MongoCollection<Document> encuestas = mongoDbConexion.getBaseDatos().getCollection("encuestas");
        //
        Document filtro = new Document("id_encuesta", encuestaById.getIdEncuesta());
        //
        return encuestas.findOneAndDelete(filtro) !=null;
    }


}
