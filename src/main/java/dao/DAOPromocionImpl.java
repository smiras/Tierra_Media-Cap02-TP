package dao;

import java.sql.*;
import java.util.*;
import jdbc.Conection;
import ofertables.*;
import usuarios.Usuario;

public class DAOPromocionImpl extends Conection implements DAOPromocion {

	@Override
	public List<Promocion> findAll() {
		return null;
	}

	@Override
	public int countAll() {
		return 0;
	}

	@Override
	public int insert(Promocion t) {
		return 0;
	}

	@Override
	public int update(Promocion t) {
		return 0;
	}

	@Override
	public int delete(Promocion t) {
		return 0;
	}

	@Override
	public LinkedList<Atraccion> leerAtraccionesIncluidas(int id_promo) {
		LinkedList<Atraccion> atraccionesincluidas = new LinkedList<Atraccion>();
		try {
			getConnection();
			Statement st = conexion.createStatement();
			String consulta = "SELECT Atracciones.atraccion_id, Atracciones.Nombre, Atracciones.Costo, Atracciones.Duracion, Atracciones.Cupo FROM Atracciones JOIN atrac_promo on Atracciones.atraccion_id = atrac_promo.atraccion_id WHERE atrac_promo.promocion_id = "
					+ id_promo;
			ResultSet result = st.executeQuery(consulta);
			while (result.next()) {
				Atraccion at = new Atraccion(result.getInt(1), result.getString(2), result.getDouble(3),
						result.getDouble(4), result.getInt(5));
				atraccionesincluidas.add(at);

			}
			closeConnection(st, result);
		} catch (Exception e) {

		}

		return atraccionesincluidas;
	}

	@Override
	public LinkedList<Promocion> leerPromociones() throws SQLException {
		LinkedList<Promocion> listapromociones = new LinkedList<Promocion>();
		try {
			getConnection();
			Statement st = conexion.createStatement();
			ResultSet result = st.executeQuery("SELECT * FROM Promociones");
			while (result.next()) {
				DAOPromocion p = new Promocion();
				LinkedList<Atraccion> atraccionesincluidas = p.leerAtraccionesIncluidas(result.getInt(1));
				Promocion promo = new Promocion(result.getInt("promocion_id"), result.getString("nombre"),
						result.getString("tipo"), result.getInt("dato_especial"), atraccionesincluidas);
				listapromociones.add(promo);
			}
			closeConnection(st, result);
		} catch (Exception e) {
			throw e;
		}
		return listapromociones;

	}

	@Override
	public int compareTo(Promocion pr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calcularCosto(LinkedList<Atraccion> atracciones, String tipo, int esp) {
		double c = 0;
		if (tipo.equals("porcentual")) {
			for (Atraccion at : atracciones) {
				c += at.getCosto();
			}
			c -= c * esp / 100;
		} else if (tipo.equals("absoluta")) {
			c = esp;
		} else if (tipo.equals("axb")) {
			for (int i = 1; i < esp; i++) {
				c += atracciones.get(i).getCosto();
			}
		}

		return c;

	}

	@Override
	public double calcularDuracion(LinkedList<Atraccion> atracciones) {
		double d = 0;

		for (Atraccion at : atracciones) {
			d += at.getDuracion();
		}
		return d;

	}

	@Override
	public boolean tieneCupo() {
		return false;
	}

	@Override
	public LinkedList<Promocion> promosPosibles(LinkedList<Promocion> promos, Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ofertarPromos(LinkedList<Promocion> promos) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void ofertarPromos(LinkedList<Promocion> promos, Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double verificaPositivo(double valor) {
		if (valor >= 0)
			return valor;
		else
			throw new RuntimeException("no se pueden cargar valores negativos");

	}

	

}
