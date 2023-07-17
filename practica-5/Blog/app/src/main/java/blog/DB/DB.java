package blog.DB;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;


public class DB {


    private static DB db = null;
    // Mapa para almacenar las salas y sus sesiones WebSocket
    private static Map<String, Map<String, Session>> salas;
     // Mapa para almacenar los mensajes de cada sala
     private static Map<String, StringBuilder> mensajesSalas;




    public DB() {
       salas = new ConcurrentHashMap<>();
	   mensajesSalas = new ConcurrentHashMap<>();


    }

    public static DB initDB() {
        if(db == null) {
			db = new DB();
		}
		return db;
    }

	public Map<String, Map<String, Session>> getSalas() {
		return salas;
	}
	public void setSalas(Map<String, Map<String, Session>> salas) {
		DB.salas = salas;
	}

	public Map<String, StringBuilder> getMensajesSalas() {
		return mensajesSalas;
	}
	public void setMensajesSalas(Map<String, StringBuilder> mensajesSalas) {
		DB.mensajesSalas = mensajesSalas;
	}

}
