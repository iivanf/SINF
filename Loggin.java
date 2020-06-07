

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Loggin {

	static Scanner teclado;
	static Statement stmt;
	static Connection connection;
	// DATOS PARA O ACCESO A BASE DE DATOS
	private static final String usuario = "ivan";
	private static final String contrasinal = "ivan";
	private static final String url = "jdbc:mysql://" + "localhost" + "/" + "proyecto25" + "?serverTimezone=UTC";

	public static void main(String args[]) throws SQLException {
		// CONECTAMOS COA BASE DE DATOS
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, usuario, contrasinal);
			stmt = connection.createStatement();
		} catch (ClassNotFoundException ex) {
			System.out.println("Error con el driver: " + ex);
		} catch (Exception e) {
			System.out.println("\nLa conexión no ha sido posible,¿Contraseña incorrecta?\n\n");
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Escriba su DNI");
		teclado = new Scanner(System.in);
		String dni = teclado.nextLine();
		String tipo_usuario = login(dni);

		if (tipo_usuario.equals("Administrador"))
			Admin.menuAdmin();
		else
			Cliente.menuCliente(dni,tipo_usuario);
	}

	public static String login(String dni) {
		String tipo_usuario = "";

		String query = "select nombreCompleto , tipo_usuario from cliente where cliente.DNI='" + dni+"'";
		try {
			ResultSet consulta = stmt.executeQuery(query);
			if (consulta.next()) {
				System.out.println("Bienvenido " + consulta.getString("nombreCompleto") + ". " 
						+ "Eres un usuario de tipo " + consulta.getString("tipo_usuario"));
				tipo_usuario = consulta.getString("tipo_usuario").trim();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			System.out.println("DNI incorrecto.");
			System.exit(0);
		}
		if(tipo_usuario.equals("")) {
			System.out.println("DNI incorrecto");
			System.exit(0);
		};
		return tipo_usuario;
	}

}