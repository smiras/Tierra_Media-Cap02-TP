package dao;

import java.sql.SQLException;
import java.util.LinkedList;

import ofertables.Atraccion;
import usuarios.Usuario;

public interface DAOAtraccion extends GenericDAO<Atraccion>{
	
	public LinkedList<Atraccion> leerAtracciones() throws SQLException;
	
	public void ofertarAtracciones(LinkedList<Atraccion> atracciones, Usuario usuario);
	
	public double verificaPositivo (double valor);

	


}
