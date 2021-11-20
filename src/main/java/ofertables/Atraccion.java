package ofertables;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import dao.DAOAtraccionImpl;
import usuarios.Usuario;

public class Atraccion extends DAOAtraccionImpl {
	private int id;
	private String nombre;
	private String[] atraccion = new String[1];
	private double costo;
	private double duracion;
	private int cupo;

	public Atraccion() {

	}

	public Atraccion(int id, String nombre, double costo, double duracion, int cupo) {
		this.id = id;
		this.nombre = nombre;
		this.costo = this.verificaPositivo(costo);
		this.duracion = this.verificaPositivo(duracion);
		this.cupo = cupo;
		this.atraccion[0] = this.nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public double getCosto() {
		return costo;
	}

	public double getDuracion() {
		return duracion;
	}

	public int getCupo() {
		return cupo;
	}

	public void restarCupo() {
		cupo--;
	}

	public String toString() {
		return "\nAtraccion \nNombre: " + nombre + " Costo: " + costo + " Duracion: " + duracion + "\n";
	}

	public int compareTo(Atraccion at) {
		return Double.valueOf(costo).compareTo(at.getCosto());

	}

	public String[] getAtraccion() {
		return atraccion;
	}

	public boolean tieneCupo() {
		return (cupo > 0);
	}

	public LinkedList<Atraccion> atraccionesPosibles(LinkedList<Atraccion> listaAtracciones, Usuario usuario) {
		LinkedList<Atraccion> atraccionesPosibles = new LinkedList<Atraccion>();
		LinkedList<Atraccion> Aceptadas = new LinkedList<Atraccion>();

		for (Atraccion at : listaAtracciones) {
			if ((usuario.getPresupuesto() >= at.getCosto()) && (usuario.getTiempodisponible() >= at.getDuracion())
					&& (at.tieneCupo())) {
				atraccionesPosibles.add(at);
			}
		}

		Collections.sort(atraccionesPosibles, (a, b) -> Double.compare(b.getCosto(), a.getCosto()));
		Aceptadas.addAll(usuario.getAtraccionAceptada());
		atraccionesPosibles.removeAll(Aceptadas);
		return atraccionesPosibles;
	}

	@Override
	public void ofertarAtracciones(LinkedList<Atraccion> listaAtracciones, Usuario usuario) {
		String respuesta = "n";
		Scanner sc = new Scanner(System.in);
		int i = 0;
		LinkedList<Atraccion> listaAtraccionesPosibles = atraccionesPosibles(listaAtracciones, usuario);

		while ((respuesta.equalsIgnoreCase("n")) && (listaAtraccionesPosibles != null)
				&& (i < listaAtraccionesPosibles.size())) {

			System.out.println(listaAtraccionesPosibles.get(i));
			do {
				System.out.println("¿Acepta la sugerencia? Ingrese: s (Si) - n (No) - x (Salir)");
				respuesta = sc.nextLine();
			} while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")&& !respuesta.equalsIgnoreCase("x"));

			if (respuesta.contentEquals("s")) {
				usuario.aceptarAtraccion(listaAtraccionesPosibles.get(i));
				listaAtraccionesPosibles = atraccionesPosibles(listaAtraccionesPosibles, usuario);
				i = 0;
			} else if (respuesta.contentEquals("n")) {
				i++;
			} else if (respuesta.contentEquals("x")) {
				listaAtraccionesPosibles = null;
			}
		}
		usuario.imprimirTicket();
	}

	public int getId() {
		return id;
	}
	
	

}
