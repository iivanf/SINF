
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Cliente {

	static CallableStatement cstmt;

	public static void menuCliente(String DNI,String tipoCliente) {
		int seleccion = 0;
		boolean principio = false;
		do {
			
			principio = true;
			System.out.println("\n\nElija la opcion que desee:");
			System.out.println("1: Listar precios de un evento");
			System.out.println("2: Ver Participantes de un evento");
			System.out.println("3: Consultar eventos");
			
			System.out.println("4: Reservar Localidad");
			System.out.println("5: Anular Reserva");
			System.out.println("6: Confirmar Pre-Reserva");
			
			System.out.println("0: Salir");
			seleccion = Loggin.teclado.nextInt();

			switch (seleccion) {
			case 1:
				PrecioEvento();
				break;
			case 2:
				VerParticipantesEvento();
				break;
			case 3:
				consultarEventos();
				break;
			case 4:
				ReservarLocalidad(DNI,tipoCliente);
				
				break;
			case 5:
				AnularReserva(DNI);
				break;
			case 6:
				Confirmar_Reserva(DNI);
				
				
				break;
			
			case 0:
				System.out.println("Nos vemos.");
				break;
			default:
				System.out.println("Opcion invalida");
				break;
			}
		} while (seleccion != 0);
	}

	public static void ListarPreciosEventos() {
		
		String query = "Select evento.id_evento, evento.fecha, espectaculo.nombre, espectaculo.descripcion from evento, espectaculo where evento.estado='abierto' and evento.id_espectaculo=espectaculo.id_espectaculo";
		

		try {
			System.out.println(
					"Estos son los eventos disponibles en el sistema, escoja el id del que quiera ver los precios\n");
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Evento con id: " + rs.getInt("id_evento") + "\tNombre: " + rs.getString("nombre")
						+ "\tDescripcion: " + rs.getString("Descripcion") + "\tFecha: " + rs.getTimestamp("fecha") + "\n");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("Escoja el id del evento del que quiera consultar los precios");

		int eleccion = Loggin.teclado.nextInt();
		try {
			query = "Select define.id_grada, define.P_Jubilado,P_Adulto,P_Parado,P_Infantil,grada.nombre_grada from define,grada where define.id_evento="
					+ eleccion + " and grada.id_grada=define.id_grada";
			ResultSet rs = Loggin.stmt.executeQuery(query);
			System.out.println("ss");
			if(!rs.next()) {System.out.println("No hay precios definidos para esa grada");}
			else {
			while (rs.next()) {
				String precioAdulto=rs.getString("P_Adulto");
				String precioJubilado=rs.getString("P_Jubilado");
				String precioInfantil=rs.getString("P_Infantil");
				String precioParado=rs.getString("P_Parado");
				if(precioAdulto.equals("-1")){precioAdulto="Evento no disponible para este usuario";}
				if(precioJubilado.equals("-1")){precioJubilado="Evento no disponible para este usuario";}
				if(precioInfantil.equals("-1")){precioInfantil="Evento no disponible para este usuario";}
				if(precioParado.equals("-1")){precioParado="Evento no disponible para este usuario";}
						
						
				System.out.println( "id_grada:"+rs.getInt("id_grada") +"\t Nombre de la grada: "+  rs.getString("nombre_grada")+"\tPrecio Adulto: "+precioAdulto+" Euros\tPrecio Jubilado: "+precioJubilado+" Euros\tPrecio Infantil: "+precioInfantil+" Euros\tPrecio Parado: "+precioParado+" Euros") ;

			}
		}} catch (Exception e) {
			System.out.println("sss");
			e.printStackTrace();
		}

	}

	public static void VerParticipantesEvento() {
	
		String query = "Select evento.id_evento, evento.fecha, espectaculo.nombre, espectaculo.descripcion from evento, espectaculo where evento.estado='abierto' and evento.id_espectaculo=espectaculo.id_espectaculo";

		try {
			System.out.println(
					"Estos son los eventos disponibles en el sistema, escoja el id del que quiera ver los participantes");
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Evento con id: " + rs.getInt("id_evento") + "\tNombre: " + rs.getString("nombre")
						+ "\tDescripcion: " + rs.getString("Descripcion") + "\tFecha: " + rs.getTimestamp("fecha") + "\n");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("Escoja el id del evento del que quiera ver los parcicipantes");

		String eleccion = Loggin.teclado.nextLine();
		eleccion = Loggin.teclado.nextLine();

		try {
			CallableStatement cstmt = Loggin.connection.prepareCall("call Participante_Evento(?)");
			cstmt.setString(1, eleccion);
			query = "select * from evento where id_evento=" + eleccion;
			ResultSet rs = Loggin.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("No existe el evento");
				return;
			} else {
				rs = cstmt.executeQuery();
				while (rs.next()) {

					System.out.println("Participante: " + rs.getString("nombre_participante"));

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void consultarEventos() {
		System.out.println("Escoja que tipo de consulta quiere hacer:");
		System.out.println("1-Buscar eventos por recinto, fecha y estado.");
		System.out.println("2-Buscar eventos por precio y tipo de espectador.");
		System.out.println("3-Buscar eventos por precio, tipo espectador, recinto, fecha y estado");

		int eleccion = Loggin.teclado.nextInt();
		switch (eleccion) {
		case 1:
			try {
				String query = "Select id_recinto, nombre from recinto";

				ResultSet rs = Loggin.stmt.executeQuery(query);
				System.out.print("Selecione el id del recinto que quiera consultar\n");
				while (rs.next()) {
					System.out.println("\nRecinto con id: " + rs.getString("id_recinto") + "  Nombre: "
							+ rs.getString("nombre"));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			eleccion = Loggin.teclado.nextInt();
			String fecha = Admin.dameFecha(true);
			System.out.println("Introduzca el estado del evento");
			
			String estado_evento = Loggin.teclado.next();
			try {
					System.out.println(estado_evento);
				CallableStatement cstmt = Loggin.connection.prepareCall("call Obtener_Eventos(?,?,?,?,?,?)");
				cstmt.setInt(1, eleccion);
				cstmt.setString(2, fecha);
				cstmt.setString(3, estado_evento);
				cstmt.setString(4, "1");
				cstmt.setString(5, "2");
				cstmt.setInt(6, 0);

				ResultSet rs = cstmt.executeQuery();
				if (!rs.next()) {
					System.out.println("No existen eventos con los parámetros descritos");
					return;
				} else {
					rs = cstmt.executeQuery();
					while (rs.next()) {

						System.out.println("Id_Evento:" + rs.getString("id_evento") + " Nombre espectaculo:"
								+ rs.getString("nombre"));

					}

				}
			} catch (SQLException e) {
			}
			break;
		case 2:

			System.out.println("Introduzca el precio maximo que estaría dispuesto a pagar");

			int precio = Loggin.teclado.nextInt();

			System.out.println("Introzuca el tipo de cliente del que quiera consultar los precios ");
			System.out.println("1- Jubilado ");
			System.out.println("2- Adulto ");
			System.out.println("3- Infantil ");
			System.out.println("4- Parado");

			int tipoCliente = Loggin.teclado.nextInt();

			try {
				CallableStatement cstmt = Loggin.connection.prepareCall("call Obtener_Eventos(?,?,?,?,?,?)");
				cstmt.setInt(1, 1);
				cstmt.setString(2, "2020-12-2");
				cstmt.setString(3, "s");
				cstmt.setInt(4, precio);
				cstmt.setInt(5, tipoCliente);
				cstmt.setInt(6, 1);
				String cliente = "";
				if (tipoCliente == 1)
					cliente = "P_Jubilado";
				if (tipoCliente == 2)
					cliente = "P_Adulto";

				if (tipoCliente == 3)
					cliente = "P_Infantil";

				if (tipoCliente == 4)
					cliente = "P_Parado";

				ResultSet rs = cstmt.executeQuery();
				if (!rs.next()) {
					System.out.println("No existen eventos con los parámetros descritos");
					return;
				} else {
					rs = cstmt.executeQuery();
					while (rs.next()) {

						System.out.println("Id_Evento:" + rs.getString("id_evento") + " Nombre espectaculo:"
								+ rs.getString("nombre") + " Grada : " + rs.getString("nombre_grada") + " Precio : "
								+ rs.getString(cliente) + " Euros");

					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case 3:
			try {
				String query = "Select id_recinto, nombre from recinto";

				ResultSet rs = Loggin.stmt.executeQuery(query);
				System.out.print("selecione uno de los siguiente recintos");
				while (rs.next()) {
					System.out.println(
							"Recinto con id" + rs.getString("id_recinto") + "  Nombre: " + rs.getString("nombre"));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			eleccion = Loggin.teclado.nextInt();
			fecha = Admin.dameFecha(true);
			System.out.println("Introduzca el estado del evento");
			
			estado_evento = Loggin.teclado.next();
			System.out.println("Introduzca el precio maximo que estaría dispuesto a pagar");

			precio = Loggin.teclado.nextInt();

			System.out.println("Introzuca el tipo de cliente del que quiera consultar los precios ");
			System.out.println("1- Jubilado ");
			System.out.println("2- Adulto ");
			System.out.println("3- Infantil ");
			System.out.println("4- Parado");

			tipoCliente = Loggin.teclado.nextInt();

			try {
				CallableStatement cstmt = Loggin.connection.prepareCall("call Obtener_Eventos(?,?,?,?,?,?)");
				cstmt.setInt(1, eleccion);
				cstmt.setString(2, fecha);
				cstmt.setString(3, estado_evento);
				cstmt.setInt(4, precio);
				cstmt.setInt(5, tipoCliente);
				cstmt.setInt(6, 2);

				String cliente = "";
				if (tipoCliente == 1)
					cliente = "P_Jubilado";
				if (tipoCliente == 2)
					cliente = "P_Adulto";

				if (tipoCliente == 3)
					cliente = "P_Infantil";

				if (tipoCliente == 4)
					cliente = "P_Parado";

				ResultSet rs = cstmt.executeQuery();
				if (!rs.next()) {
					System.out.println("No existen eventos con los parámetros descritos");
					return;
				} else {
					rs = cstmt.executeQuery();
					while (rs.next()) {

						System.out.println("Id_Evento:" + rs.getString("id_evento") + " Nombre espectaculo:"
								+ rs.getString("nombre") + " Grada : " + rs.getString("nombre_grada") + " Precio : "
								+ rs.getString(cliente) + " Euros");

					}

				}
			}

			catch (SQLException e) {
			}

		}

	}

	public static void PrecioEvento() {
		String query = "Select evento.id_evento, evento.fecha, espectaculo.nombre, espectaculo.descripcion from evento, espectaculo where evento.estado='abierto' and evento.id_espectaculo=espectaculo.id_espectaculo";
		try {
			System.out.println(
					"Estos son los eventos disponibles en el sistema, Elcoja el id del que quiera ver los precios");
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Evento con id:" + rs.getInt("id_evento") + " Nombre: " + rs.getString("nombre")
						+ " Descripcion: " + rs.getString("Descripcion") + "  Fecha: " + rs.getDate("fecha") + "");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("Escoja el id del evento del que quiera consultar los precios");

		int eleccion = Loggin.teclado.nextInt();
		System.out.println("Escoja el id_grada de la grada la cual quiera consultar los precios");
		query = "Select nombre_grada,define.id_grada from define, grada where grada.id_grada=define.id_grada and define.id_evento="
				+ eleccion;
		try {
			ResultSet rs = Loggin.stmt.executeQuery(query);
			
			while (rs.next()) {

				System.out.println(
						"id_grada:" + rs.getInt("id_grada") + " Nombre_Grada: " + rs.getString("nombre_grada"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int eleccion_grada = Loggin.teclado.nextInt();

		System.out.println("Introzuca el tipo de cliente del que quiera consultar los precios ");
		System.out.println("1- Jubilado ");
		System.out.println("2- Adulto ");
		System.out.println("3- Infantil ");
		System.out.println("4- Parado");

		int tipoCliente = Loggin.teclado.nextInt();

		try {
			CallableStatement cstmt = Loggin.connection.prepareCall("call consultar_precio_grada_evento(?,?,?)");
			cstmt.setInt(1, eleccion);
			cstmt.setInt(2, eleccion_grada);
			cstmt.setInt(3, tipoCliente);

			String cliente = "";
			if (tipoCliente == 1)
				cliente = "P_Jubilado";
			if (tipoCliente == 2)
				cliente = "P_Adulto";

			if (tipoCliente == 3)
				cliente = "P_Infantil";

			if (tipoCliente == 4)
				cliente = "P_Parado";

			ResultSet rs = cstmt.executeQuery();
			if (!rs.next()) {
				System.out.println("No existen eventos con los parámetros descritos");
				return;
			} else {
				rs = cstmt.executeQuery();
				while (rs.next()) {
					if (rs.getInt(cliente) == -1) {System.out.println("El tipo de usuario no esta adminitido en este evento");}
					else {
						

					System.out.println(" Nombre espectaculo:" + rs.getString("nombre") + " Grada : "
							+ rs.getString("nombre_grada") + " Precio : " + rs.getString(cliente) + " Euros");

				}}

			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void ReservarLocalidad(String DNI, String tipo_usuario) {
		String query = "Select evento.id_evento, evento.fecha, espectaculo.nombre, espectaculo.descripcion from evento, espectaculo where evento.estado='abierto' and evento.id_espectaculo=espectaculo.id_espectaculo";
		try {
			System.out.println(
					"Estos son los eventos disponibles en el sistema, Elcoja el id del que quiera ver los precios");
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Evento con id:" + rs.getInt("id_evento") + " Nombre: " + rs.getString("nombre")
						+ " Descripcion: " + rs.getString("Descripcion") + "  Fecha: " + rs.getDate("fecha") + "");

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		System.out.println("Escoja el id del evento del que quiera consultar los precios");

		int eleccion_evento = Loggin.teclado.nextInt();
		System.out.println("Escoja el id_grada de la grada la cual quiera consultar los precios");
		query = "Select nombre_grada,define.id_grada from define, grada where grada.id_grada=define.id_grada and define.id_evento="
				+ eleccion_evento;
		try {
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println(
						"id_grada:" + rs.getInt("id_grada") + " Nombre_Grada: " + rs.getString("nombre_grada"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int eleccion_grada = Loggin.teclado.nextInt();

		boolean a=true;
		
		try {
			CallableStatement cstmt = Loggin.connection.prepareCall("call Listar_Localidades(?,?)");
			cstmt.setInt(1, eleccion_evento);
			cstmt.setInt(2, eleccion_grada);
			
			ResultSet rs = cstmt.executeQuery();

			System.out.println("Escoja la/las localidad de la grada que quiera reservar separadas por guiones ");
				rs = cstmt.executeQuery();
				if(!rs.next()) {
					System.out.println("No queda ninguna Localidad libre en esa grada");
					
					a=false;
				}
				else {
				while (rs.next()) {
					

					System.out.println("ID:Localidad: "+ rs.getString("id_localidad"));
				}
				}

			
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
			
		if(a) {
			String localidades=Loggin.teclado.next();
			String[] individual=localidades.split("-");
			
			
		
		
		System.out.println("Desea:\n 1)Reservar \n 2)Pre-reservar\n");
		int eleccion = Loggin.teclado.nextInt();
		for (int i =0;i<individual.length;i++) { 
		try {
			cstmt = Loggin.connection.prepareCall("call Pre_Reserva(?,?,?,?,?,?)");
			cstmt.setString(1, DNI);
			cstmt.setInt(2, eleccion_evento);
		
			cstmt.setInt(3, eleccion_grada);
			cstmt.setInt(4, Integer.parseInt(individual[i]));
			cstmt.setString(6, tipo_usuario);
			if (eleccion == 1)
				cstmt.setString(5, "reservado");
			else if (eleccion == 2)
				cstmt.setString(5, "pre-reservado");
			else {
				System.out.println("Opcion invalida");
				return;
			}
			cstmt.executeQuery();
			System.out.println("Localidad reservada/pre-reservada");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			
		}

	}}}
	
public static void AnularReserva(String DNI) {
			
		System.out.println("Estos son los id_reserva que tienes");
		
		String query = "Select id_reserva,FechaRealizacion,estado from reserva where reserva.DNI='"+DNI+"'";
		try {
			
			ResultSet rs = Loggin.stmt.executeQuery(query);

			while (rs.next()) {

				System.out.println("Reserva con ID: " +rs.getString("id_reserva")+" Fecha Transacción: "+rs.getTimestamp("FechaRealizacion")+" Estado: "+rs.getString("estado"));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		
		

		System.out.println("Introduzca o ID da reserva:");
		String id_reserva = Loggin.teclado.next();
		

		try {
			cstmt = Loggin.connection.prepareCall("call Anulacion(?,?)");
			cstmt.setString(1, id_reserva);
			cstmt.setString(2, DNI);
			
			

			ResultSet rs = cstmt.executeQuery();
			System.out.println("Reserva/pre-reserva anulada");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
public static void Confirmar_Reserva(String DNI) {
	
	System.out.println("Estos son los id_reserva de tipo pre-reserva que tienes");
	
	String query = "Select id_reserva,FechaRealizacion,estado from reserva where reserva.DNI='"+DNI+"' and estado='pre-reservado'" ;
	try {
		// System.out.println(
		// 		"Estos son los eventos disponibles en el sistema, Elcoja el id del que quiera ver los precios");
		ResultSet rs = Loggin.stmt.executeQuery(query);

		while (rs.next()) {

			System.out.println("Reserva con ID: " +rs.getString("id_reserva")+" Fecha Transacción: "+rs.getTimestamp("FechaRealizacion"));

		}

	} catch (SQLException e) {
		e.printStackTrace();

	}
	
	

	System.out.println("Introduzca o ID da reserva:");
	String id_reserva = Loggin.teclado.next();
	

	try {
		cstmt = Loggin.connection.prepareCall("call Confirmar_Reserva(?,?)");
		cstmt.setString(1, id_reserva);
		cstmt.setString(2, DNI);
		
		

		ResultSet rs = cstmt.executeQuery();
		System.out.println("Pre-reserva confirmada");
	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
	
}

	
	
	
	
}
