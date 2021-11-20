package dao;

import java.sql.*;
import java.util.*;

import jdbc.Conection;
import ofertables.Atraccion;
import ofertables.Promocion;
import usuarios.Usuario;

public class DAOUsuarioImpl extends Conection implements DAOUsuario {

	@Override
	public List<Usuario> findAll() {
		return null;
	}

	@Override
	public int countAll() {
		return 0;
	}

	@Override
	public int insert(Usuario t) {
		return 0;
	}

	@Override
	public int update(Usuario t) {
		return 0;
	}

	@Override
	public int delete(Usuario t) {
		return 0;
	}

	@Override
	public LinkedList<Usuario> leerusUarios() throws SQLException {
		LinkedList<Usuario> listausuarios = new LinkedList<Usuario>();
		try {
			getConnection();
			Statement st = conexion.createStatement();
			ResultSet result = st.executeQuery("SELECT * FROM Usuarios");
			while (result.next()) {
				Usuario user = new Usuario(result.getString(1), result.getDouble(2), result.getDouble(3));
				listausuarios.add(user);
			}
			closeConnection(st, result);
		} catch (Exception e) {
			throw e;
		}
		return listausuarios;

	}

	

	@Override
	public LinkedList<Promocion> getPromosAceptadas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Atraccion> getAtraccionAceptada() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double calcularDinerogastado() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double calcularTiempoNecesario() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Usuario buscarUsuario(LinkedList<Usuario> listaUsuarios, String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario leerUsuario(LinkedList<Usuario> listaUsuarios) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aceptarPromo(Promocion promocionAceptada) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void aceptarAtraccion(Atraccion atraccion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void imprimirTicket() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardarDatos(Usuario usuario, LinkedList<Atraccion> listaAtracciones, LinkedList<Promocion> listaPromociones) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
