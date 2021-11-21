package dao;

import java.sql.*;
import java.util.*;

import jdbc.Conection;
import ofertables.Atraccion;
import usuarios.Usuario;

public class DAOAtraccionImpl extends Conection implements DAOAtraccion{
	
	@Override
	public List<Atraccion> findAll() {
		return null;
	}

	@Override
	public int countAll() {
		return 0;
	}

	@Override
	public int insert(Atraccion t) {
		return 0;
	}

	@Override
	public int update(Atraccion t) {
		return 0;
	}

	@Override
	public int delete(Atraccion t) {
		return 0;
	}

	@Override
	public LinkedList<Atraccion> leerAtracciones() throws SQLException {
		LinkedList<Atraccion> listaatracciones = new LinkedList<Atraccion>();
		try {
			getConnection();
			Statement st = conexion.createStatement();
			ResultSet result = st.executeQuery("SELECT * FROM Atracciones");
			while (result.next()) {
				Atraccion atraccion = new Atraccion(result.getInt(1), result.getString(2), result.getDouble(3), result.getDouble(4), result.getInt(5));
				listaatracciones.add(atraccion);
			}
			closeConnection(st, result);
		} catch (Exception e) {
			throw e;
		}
		return listaatracciones;

	}

	@Override
	public void ofertarAtracciones(LinkedList<Atraccion> atracciones, Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double verificaPositivo(double valor) {
		if (valor>=0)
			return valor;
		else throw new RuntimeException("no se pueden cargar valores negativos");
		
	}

	
}
