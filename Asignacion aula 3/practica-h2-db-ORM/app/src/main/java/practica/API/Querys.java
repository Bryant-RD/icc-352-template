package practica.API;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import practica.entidades.Estudiante;


public class Querys {

    public static ArrayList<Estudiante> getAllEstudiantes() {
		
		ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
		
		String selectAll = "SELECT * FROM Estudiante";
		Connection conexion = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conexion = Conexion.getConexion();
			ps = conexion.prepareStatement(selectAll);
			rs = ps.executeQuery();
			
			while (rs.next()) {
			
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
                Date fechaNacimiento = rs.getDate("fechaNacimiento");
				int edad = rs.getInt("edad");

			
				// Estudiante aux = new Estudiante(id, nombre, fechaNacimiento, edad);
				
				// estudiantes.add(aux);
				
				
			}
			
		} catch (SQLException e) {
			System.out.println(e.toString());
		} finally {
			try {
				Conexion.close(rs);
				Conexion.close(ps);
				Conexion.close(conexion);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(System.out);
			}
		}
		
		return estudiantes;
	}



    public static Estudiante getEstudiante(String code) {
	    Estudiante estudiante = null;
	    String select = "SELECT * FROM Estudiante where id_transaccion = ? ";
	    Connection conexion = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conexion = Conexion.getConexion();
	        ps = conexion.prepareStatement(select);
	        ps.setString(1, code);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            int id = rs.getInt("id");
	            String nombre = rs.getString("nombre");
	            Date fechaNacimiento = rs.getDate("fechaNacimiento");
                int edad = rs.getInt("edad");

	            // estudiante = new Estudiante(id, nombre, fechaNacimiento, edad);

	        }

	    } catch (SQLException e) {
	        System.out.println(e.toString());
	    } finally {
	        try {
	            Conexion.close(rs);
	            Conexion.close(ps);
	            Conexion.close(conexion);
	        } catch (SQLException e) {
	            e.printStackTrace(System.out);
	        }
	    }

	    return estudiante;
	}


    // public static void newEstudiante(Estudiante estudiante) {
		
	// 	String insert = "INSERT INTO Estudiante(id, nombre, fechaNacimiento, edad) VALUES(?,?,?,?)";
		
	// 	Connection conexion = null;
	// 	PreparedStatement ps = null;
	// 	int registros = 0;
		
	// 	try {
	// 		conexion = Conexion.getConexion();
	// 		ps = conexion.prepareStatement(insert);
	// 		ps.setInt(1, estudiante.getId());
	// 		ps.setString(2, estudiante.getNombre());
	// 		ps.setDate(3, estudiante.getFechaNacimiento());
	// 		ps.setInt(4, estudiante.getEdad());
			
	// 		registros = ps.executeUpdate();

	// 	} catch (SQLException e) {
	// 		System.out.println(e.toString());
	// 	} finally {
	// 		try {
	// 			Conexion.close(ps);
	// 			Conexion.close(conexion);
	// 		} catch (SQLException e) {
	// 			// TODO Auto-generated catch block
	// 			e.printStackTrace(System.out);
	// 		}
	// 	}
	// }


    
}
