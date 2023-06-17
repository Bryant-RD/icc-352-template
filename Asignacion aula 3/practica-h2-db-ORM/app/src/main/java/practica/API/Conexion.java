package practica.API;

import java.sql.*;

public class Conexion {


    public static Connection getConexion() {

        String url = "jdbc:h2:file:C:/Users/Bryant Reynoso/Desktop/Programacion web - Javalin/bases de datos/MyH2DB";
        String usuario = "sa";
        String password = "123456";
    
        try {
            Connection conexion = DriverManager.getConnection(usuario, url, password);
            System.out.println("CONEXION!!!!");
            return conexion;
        } catch (Exception e) {
            System.out.println("Error al conectarse a H2: " + e.getMessage());
            return null;
        }
        
    }

    public static void close(ResultSet rs) throws SQLException {
		rs.close();
	}
	
	public static void close(Statement smtm) throws SQLException {
		((Connection) smtm).close();
	}
	public static void close(PreparedStatement smtm) throws SQLException {
		smtm.close();
	}

	public static void close(Connection conn) throws SQLException {
		conn.close();
	}

}
