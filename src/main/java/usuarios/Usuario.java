package usuarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Scanner;

import dao.DAOPromocion;
import dao.DAOUsuarioImpl;
import ofertables.*;

public class Usuario extends DAOUsuarioImpl {
	@Override
	public String toString() {
		return "Usuario/n Nombre: " + nombre + " Presupuesto: " + presupuesto + " Tiempo disponible: "
				+ tiempodisponible;
	}

	private String nombre;
	private double presupuesto;
	private double tiempodisponible;
	private LinkedList<Promocion> promosAceptadas = new LinkedList<Promocion>();
	private LinkedList<Atraccion> atraccionesAceptadas = new LinkedList<Atraccion>();
	private double dineroGastado;
	private double tiempoNecesario;

	public double getDineroGastado() {
		return dineroGastado;
	}

	public double getTiempoNecesario() {
		return tiempoNecesario;
	}

	public Usuario(String nombre, double presupuesto, double tiempodisponible) {
		this.nombre = nombre;
		this.presupuesto = presupuesto;
		this.tiempodisponible = tiempodisponible;
	}

	public Usuario() {

	}

	public String getNombre() {
		return nombre;
	}

	public double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public double getTiempodisponible() {
		return tiempodisponible;
	}

	public void setTiempodisponible(double tiempodisponible) {
		this.tiempodisponible = tiempodisponible;
	}

	public LinkedList<Promocion> getPromosAceptadas() {
		return promosAceptadas;
	}

	public LinkedList<Atraccion> getAtraccionAceptada() {
		return atraccionesAceptadas;
	}

	public double calcularDinerogastado() {
		dineroGastado = 0;
		promosAceptadas.forEach(promo -> dineroGastado += promo.getCosto());
		atraccionesAceptadas.forEach(atraccion -> dineroGastado += atraccion.getCosto());
		return dineroGastado;
	}

	public double calcularTiempoNecesario() {
		tiempoNecesario = 0;
		promosAceptadas.forEach(promo -> tiempoNecesario += promo.getDuracion());
		atraccionesAceptadas.forEach(atraccion -> tiempoNecesario += atraccion.getDuracion());
		return tiempoNecesario;
	}

	public Usuario buscarUsuario(LinkedList<Usuario> listaUsuarios, String nombre) {

		for (Usuario user : listaUsuarios) {
			if (user.getNombre().equals(nombre))
				return user;
		}
		return null;
	}

	public Usuario leerUsuario(LinkedList<Usuario> listaUsuarios) {
		String nombre;
		Usuario usuario = new Usuario();
		boolean encontrado = false;
		Scanner sc = new Scanner(System.in);
		while (encontrado == false) {
			System.out.println("Ingrese su nombre de usuario: \n");
			nombre = sc.nextLine();
			usuario = usuario.buscarUsuario(listaUsuarios, nombre);
			if (usuario != null)
				encontrado = true;
			else
				usuario = new Usuario();

		}

		return usuario;
	}

	public void aceptarPromo(Promocion promocionAceptada) {

		this.setPresupuesto(presupuesto - promocionAceptada.getCosto());
		this.setTiempodisponible(tiempodisponible - promocionAceptada.getDuracion());
		promocionAceptada.getAtraccionesIncluidas().forEach(at -> at.restarCupo());
		promosAceptadas.add(promocionAceptada);

	}

	public void aceptarAtraccion(Atraccion atraccion) {

		presupuesto -= atraccion.getCosto();
		tiempodisponible -= atraccion.getDuracion();
		atraccion.restarCupo();
		atraccionesAceptadas.add(atraccion);
	}

	public void imprimirTicket() {
		System.out.println("\n-------------------------------------------------------------------\n");
		System.out.printf("Tu ticket de compra: %s \n", nombre);
		System.out.printf("Sus atracciones elegidas son: ");
		promosAceptadas.forEach(p -> System.out.println(p));
		atraccionesAceptadas.forEach(a -> System.out.println(a));
		System.out.printf("\n--------------------------------------------------------------------");
		System.out.printf("\nGasto Final: " + calcularDinerogastado());
		System.out.printf("\nTiempo Necesario: " + calcularTiempoNecesario());
		System.out.printf("\n--------------------------------------------------------------------");
		System.out.printf("\n¡Gracias por tu compra! ¡Te esperamos en la Tierra Media!");
		System.out.printf("\n--------------------------------------------------------------------\n\n\n\n");

	}

	@Override
	public void guardarDatos(Usuario usuario, LinkedList<Atraccion> listaAtracciones,
			LinkedList<Promocion> listaPromociones) throws SQLException {
		usuario.calcularDinerogastado();
		usuario.calcularTiempoNecesario();
		try {
			getConnection();
			Statement st = conexion.createStatement();
			String consulta;
			ResultSet rs;
			consulta = "SELECT * FROM resultados_usuarios WHERE nombre='" + usuario.getNombre() + "'";
			rs = st.executeQuery(consulta);
			if (!rs.next()) {
				consulta = "INSERT INTO resultados_usuarios VALUES ('" + usuario.getNombre() + "',"
						+ usuario.getDineroGastado() + "," + usuario.getTiempoNecesario() + ");";
				st.executeUpdate(consulta);
			} else {
				consulta = "UPDATE resultados_usuarios SET gasto=" + usuario.getDineroGastado() + ", tiempo="
						+ usuario.getTiempoNecesario() + " WHERE nombre='" + usuario.getNombre() + "'";
				st.executeUpdate(consulta);
			}
			closeConnection(st, rs);
		} catch (Exception e) {
			System.out.println(e);
		}
		guardarAtracciones(usuario, listaAtracciones);
		guardarPromociones(usuario, listaPromociones);
		

	}

	public void guardarAtracciones(Usuario usuario, LinkedList<Atraccion> listaAtracciones) throws SQLException {

		try {
			getConnection();
			ResultSet rs;
			Statement st = conexion.createStatement();
			String consulta;
			LinkedList<Atraccion> atracionesGuardadas = new LinkedList<Atraccion>();
			consulta = "SELECT * FROM usuario_atracciones";
			rs = st.executeQuery(consulta);
			while (rs.next()) {
				int idAt = rs.getInt(2);
				for (Atraccion at : listaAtracciones) {
					if (at.getId() == idAt) {
						atracionesGuardadas.add(at);
					}
				}
			}

			atraccionesAceptadas.removeAll(atracionesGuardadas);

			for (Atraccion at : atraccionesAceptadas) {
				consulta = "INSERT INTO usuario_atracciones VALUES ('" + usuario.getNombre() + "'," + at.getId() + ")";
				st.executeUpdate(consulta);
			}
			closeConnection(st, rs);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void guardarPromociones(Usuario usuario, LinkedList<Promocion> listaPromociones) throws SQLException {
		try {
			getConnection();
			ResultSet rs;
			Statement st = conexion.createStatement();
			String consulta = "SELECT * FROM usuario_promociones";
			rs = st.executeQuery(consulta);
			LinkedList<Promocion> promocionesGuardadas = new LinkedList<Promocion>();

			while (rs.next()) {
				int idPr = rs.getInt(2);
				for (Promocion pr : listaPromociones) {
					if (pr.getId() == idPr) {
						promocionesGuardadas.add(pr);
					}
				}
			}
			promosAceptadas.removeAll(promocionesGuardadas);

			for (Promocion pr : promosAceptadas) {
				consulta = "INSERT INTO usuario_promociones VALUES ('" + usuario.getNombre() + "'," + pr.getId() + ")";
				st.executeUpdate(consulta);
			}
			closeConnection(st, rs);
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
