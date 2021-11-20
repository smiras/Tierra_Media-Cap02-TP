package ofertables;

import java.util.*;

import dao.DAOPromocionImpl;
import usuarios.Usuario;

public class Promocion extends DAOPromocionImpl {

	private int id;
	private int esp;
	private String tipo;
	private String nombre;
	private LinkedList<Atraccion> atraccionesIncluidas;
	protected double costo = 0;
	protected double duracion = 0;

	public Promocion() {

	}

	public Promocion(int id, String nombre, String tipo, int esp, LinkedList<Atraccion> atracciones) {
		this.id = id;
		this.nombre = nombre;
		this.esp = esp;
		this.tipo = tipo;
		this.atraccionesIncluidas = atracciones;
		costo = this.verificaPositivo(calcularCosto(atraccionesIncluidas, tipo, esp));
		duracion = this.verificaPositivo(calcularDuracion(atraccionesIncluidas));

	}

	public boolean tieneCupo() {
		boolean tienecupo = true;
		for (Atraccion a : atraccionesIncluidas) {
			if (a.getCupo() == 0)
				tienecupo = false;
		}

		return tienecupo;
	}

	@Override
	public int compareTo(Promocion pr) {
		return Double.valueOf(costo).compareTo(pr.getCosto());
	}

	public double getCosto() {
		return costo;
	}

	public double getDuracion() {
		return duracion;
	}

	public LinkedList<Atraccion> getAtraccionesIncluidas() {
		return atraccionesIncluidas;
	}

	public LinkedList<Promocion> promosPosibles(LinkedList<Promocion> listaPromociones, Usuario usuario) {
		LinkedList<Promocion> promosPosibles = new LinkedList<Promocion>();

		for (Promocion p : listaPromociones) {

			if ((usuario.getPresupuesto() >= p.getCosto()) && (usuario.getTiempodisponible() >= p.getDuracion())
					&& (p.tieneCupo())) {
				promosPosibles.add(p);
			}
		}

		Collections.sort(promosPosibles, (a, b) -> Double.compare(b.getCosto(), a.getCosto()));
		promosPosibles.removeAll(usuario.getPromosAceptadas());

		return promosPosibles;
	}

	@Override
	public void ofertarPromos(LinkedList<Promocion> listaPromociones, Usuario usuario) {
		String respuesta = "n";
		Scanner sc = new Scanner(System.in);
		int i = 0;
		LinkedList<Promocion> listaPromosPosibles = promosPosibles(listaPromociones, usuario);

		while ((respuesta.equalsIgnoreCase("n")) && (listaPromosPosibles != null) && (i < listaPromosPosibles.size())) {

			System.out.println(listaPromosPosibles.get(i));
			do {
				System.out.println("¿Acepta la sugerencia? Ingrese: s (Si) - n (No) - x (Salir)\n");
				respuesta = sc.nextLine();
			} while (!respuesta.equalsIgnoreCase("s") && !respuesta.equalsIgnoreCase("n")&& !respuesta.equalsIgnoreCase("x"));

			if (respuesta.contentEquals("s")) {
				usuario.aceptarPromo(listaPromosPosibles.get(i));
				listaPromosPosibles = promosPosibles(listaPromosPosibles, usuario);
				i = 0;
			} else if (respuesta.contentEquals("n")) {
				i++;
			} else if (respuesta.contentEquals("x")) {
				listaPromosPosibles = null;
			}
		}

	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Promocion\nNombre: " + nombre + "  Costo: " + costo + " Duracion: " + duracion
				+ "\nAtracciones incluidas: " + atraccionesIncluidas;
	}
}
