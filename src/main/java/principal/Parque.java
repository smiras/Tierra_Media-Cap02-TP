package principal;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

import ofertables.*;
import usuarios.Usuario;

public class Parque {
	private Usuario user = new Usuario();
	private Atraccion atraccion = new Atraccion();
	private Promocion promo = new Promocion();
	private LinkedList<Usuario> listaUsuarios;
	private LinkedList<Atraccion> listaAtracciones;
	private LinkedList<Promocion> listaPromociones;

	public Parque() throws SQLException {

		listaUsuarios = user.leerusUarios();
		listaAtracciones = atraccion.leerAtracciones();
		listaPromociones = promo.leerPromociones();
	}

	public void mostrarUsuarios() {
		listaUsuarios.forEach(user -> System.out.println(user));
	}

	public void mostrarAtracciones() {
		listaAtracciones.forEach(at -> System.out.println(at));
	}

	public void mostrarPromociones() {
		listaPromociones.forEach(pr -> System.out.println(pr));
	}

	

	public void oferta() throws SQLException {
		String seguir = "s";
		Scanner sc = new Scanner(System.in);
		System.out.println("¡Bienvenid@ a la Tierra Media!");
		System.out.println("-------------------------------------------------------------------");
		
		do {

		Usuario usuario = new Usuario();
		usuario = usuario.leerUsuario(listaUsuarios);
		promo.ofertarPromos(listaPromociones, usuario);
		atraccion.ofertarAtracciones(listaAtracciones, usuario);
		usuario.guardarDatos(usuario, listaAtracciones, listaPromociones);
			do {
				System.out.println("Desea consultar otro usuario? (s) o (n)");
				seguir = sc.nextLine();
			}while(!seguir.equalsIgnoreCase("s")&&!seguir.equalsIgnoreCase("n"));
		} while ((seguir.equalsIgnoreCase("s")));
		
	}

}
