
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Admin {
	static Scanner teclado2= new Scanner(System.in);

	public static void menuAdmin() throws SQLException {
		int seleccion = 0;
		
		do {
			System.out.println("Opciones disponibles:");
			System.out.println("1: Crear usuario");
			System.out.println("2: Eliminar usuario");
			System.out.println("3: Crear evento ");
			System.out.println("4: Añadir gradas y sus precios a eventos\n");
			
			seleccion = teclado2.nextInt();

			switch (seleccion) {
			case 1:
				crearCliente();
				break;
			
			case 2:
				eliminarCliente();
				break;
			case 3:
				CrearEvento();
					break;
			case 4:
				definirGradaEvento();
				break;
			case 0:
				System.out.println("Buen Dia");
				break;
			default:
				System.out.println("Opcion invalida");
				break;
			}

		} while (seleccion != 0);
	}

	public static void crearCliente() {
		
		System.out.println("Introduzca el DNI:\n");
		String DNI =teclado2.nextLine();
		 DNI =teclado2.nextLine();
				
		System.out.println("Introduzca el nombre completo:");
		String NombreCompleto =teclado2.nextLine();
		System.out.println("Introduzca el numero de la tarjeta de crédito:");
		String tarjeta = teclado2.nextLine();
		System.out.println("Introduzca el tipo de usuario:");
		String tipo = teclado2.nextLine();

		String update = "insert into cliente values('" + DNI + "','" + NombreCompleto + "','" + tarjeta + "','" + tipo + "')";

		try {
			Loggin.stmt.executeUpdate(update);
			System.out.println("Cliente insertado");
		} catch (SQLException e) {
			System.out.println("Error al introducir el usuario");
		}

	}

	

	

	public static void CrearEvento() {
		boolean continuar =true;
String query = "Select espectaculo.nombre,espectaculo.id_espectaculo from  espectaculo";
		

		try {
			System.out.println(
					"Estos son los espectaculos disponibles en el sistema\n");
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Espectaculo con id: " + rs.getInt("id_espectaculo") + "\tNombre: " + rs.getString("nombre")
						 );

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		System.out.println("Introduzca el ID del espectaculo:");
		String id_espectaculo = teclado2.next();
query = "Select recinto.nombre,recinto.id_recinto from recinto";
		

		try {
			System.out.println(
					"Estos son los recintos disponibles en el sistema\n");
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Recinto con id: " + rs.getInt("id_recinto") + "\tNombre: " + rs.getString("nombre")
						 );

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("Introduzca el ID del recinto:");
		String id_recinto = teclado2.next();
		String fecha = dameFecha(true);
		System.out.println("Introduzca t1:");
		String t1 = teclado2.next();
		System.out.println("Introduzca t2:");
		String t2 = teclado2.next();
		System.out.println("Introduzca t3:");
		String t3 = teclado2.next();
		System.out.println("El evento permite Jubilados? (1=Si, 0=No):");
		String entradas_jubilado = teclado2.next();
		System.out.println("El evento permite Adultos? (1=Si, 0=No):");
		String entradas_adulto = teclado2.next();
		System.out.println("El evento permite Parados? (1=Si, 0=No):");
		String entradas_parado = teclado2.next();
		System.out.println("El evento permite Infantes? (1=Si, 0=No):");
		String entradas_infantil = teclado2.next();
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try{Date date= simpleDateFormat.parse(fecha);
		if(date.before(new Date())) {System.out.println("ERROR:No puede introducir un evento en una fecha pasada");continuar=false; }
		}catch(Exception e) {e.printStackTrace();}		
		

		
		
		if(Integer.parseInt(t1)>Integer.parseInt(t2)) {
			System.out.println("Error: El parámetro t2 debe ser mayor al parámetro t1 ");
			
		}else if (continuar==true){
			try {
			
			CallableStatement cstmt = Loggin.connection.prepareCall("call crear_evento(?,?,?,?,?,?,?,?,?,?)");
			cstmt.setString(1, id_espectaculo);
			cstmt.setString(2, id_recinto);
			cstmt.setString(3, fecha);
			cstmt.setString(4, t1);
			cstmt.setString(5, t2);
			cstmt.setString(6, t3);
			cstmt.setString(7, entradas_infantil);
			cstmt.setString(8, entradas_adulto);
			cstmt.setString(9, entradas_parado);
			cstmt.setString(10, entradas_jubilado);
			
			
			 query = "select * from espectaculo where ID_espectaculo='" + id_espectaculo + "'";
			ResultSet rs = Loggin.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("No existe el espectaculo");
				return;
			}
			query = "select * from recinto where ID_recinto='" + id_recinto + "'";
			rs = Loggin.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe el recinto");
				return;
			}
			cstmt.executeQuery();
			
			System.out.println("Evento insertado");
			}catch(SQLException e) {e.printStackTrace();System.out.print("ERROR: algun parametro ha sido introducido incorrectamente");};} 	
	}
	public static void definirGradaEvento() {
String query = "Select evento.id_evento,  espectaculo.nombre from evento, espectaculo where evento.estado='abierto' and evento.id_espectaculo=espectaculo.id_espectaculo";
		

		try {
			System.out.println(
					"Estos son los eventos disponibles en el sistema\n");
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Evento con id: " + rs.getInt("id_evento") + "\tNombre: " + rs.getString("nombre")
						 );

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

		System.out.println("Introduzca el Id del evento:");
		String id_evento = teclado2.next();
		
		 query ="Select grada.id_grada, nombre_grada from grada,evento where evento.id_recinto=grada.id_recinto and evento.id_evento="+id_evento;
		try {
		ResultSet rs = Loggin.stmt.executeQuery(query);
		System.out.print("Selecione el id de la grada perteneciente al recinto del evento\n");
	
		while (rs.next()) {
			System.out.println("\nGrada: " + rs.getString("id_grada") + "  Nombre: "
					+ rs.getString("nombre_grada"));

		}
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
		
		System.out.println("\nIntroduzca el ID de la grada:");
		
		

		String id_grada = teclado2.next();
		System.out.println("Introduzca el precio para jubilados:");
		String precio_jubilado = teclado2.next();
		System.out.println("Introduzca el precio para parados:");
		String precio_parado = teclado2.next();
		System.out.println("Introduzca el precio para las entradas infantiles:");
		String precio_infantil = teclado2.next();
		System.out.println("Introduzca el precio para las entradas de adultos:");
		String precio_adulto = teclado2.next();
		

		try {
			CallableStatement cstmt = Loggin.connection.prepareCall("call Definir_Precios_Evento_Grada(?,?,?,?,?,?)");
			
			cstmt.setString(1, id_evento);
			cstmt.setString(2, id_grada);
			cstmt.setString(3, precio_jubilado);
			cstmt.setString(4, precio_adulto);
			cstmt.setString(5, precio_parado);
			cstmt.setString(6, precio_infantil);
			cstmt.executeQuery();
			System.out.println("Operacion realizada");
		} catch (Exception e) {
			System.out.println(e.getMessage()+"\n");
			
			
		}

	}

	public static void eliminarCliente() {
		System.out.println("Introduzca el DNI:");
		String DNI = teclado2.nextLine();
		DNI = teclado2.nextLine();

		String update = "DELETE FROM cliente WHERE DNI='" + DNI + "'";

		try {
			String query = "select 1 from reserva where DNI='" + DNI + "'";
			ResultSet rs = Loggin.stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("No se puede eliminar el usuario porque tiene reservas pendientes");
				return;
			}
			query = "select 1 from cliente where DNI='" + DNI + "'";
			rs = Loggin.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("No existe el cliente con el DNI: " + DNI);
				return;
			}
			Loggin.stmt.executeUpdate(update);
			System.out.println("Cliente eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}	
	

	public static String dameFecha(boolean a) {
		String hora="00";
		String minuto="00";
		String segundo="00";
		System.out.println("Introduzca el anio:");
		String anho = teclado2.next();
		System.out.println("Introduzca mes:");
		String mes = teclado2.next();
		System.out.println("Introduzca dia:");
		String dia = teclado2.next();
		if(a) {
		System.out.println("Introduzca hora:");
		 hora = teclado2.next();
		System.out.println("Introduzca minuto:");
		 minuto = teclado2.next();
		System.out.println("Introduzca segundo:");
		 segundo = teclado2.next();
		}
		String fecha = anho + "-" + mes + "-" + dia + " " + hora + ":" + minuto + ":" + segundo;
		return fecha;
	}
}