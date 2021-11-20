package dao;

import java.sql.*;
import java.util.*;

import ofertables.Atraccion;
import ofertables.Promocion;
import usuarios.Usuario;

public interface DAOUsuario extends GenericDAO<Usuario> {
	
	public LinkedList<Usuario> leerusUarios() throws SQLException;
	
	public void guardarDatos(Usuario usuario, LinkedList<Atraccion> listaAtracciones, LinkedList<Promocion> listaPromociones) throws SQLException;
	
	public LinkedList<Promocion> getPromosAceptadas();
	
	public LinkedList<Atraccion> getAtraccionAceptada();

	public double calcularDinerogastado();

	public double calcularTiempoNecesario();
	
	public Usuario buscarUsuario(LinkedList<Usuario> listaUsuarios, String nombre);
	
	public Usuario leerUsuario(LinkedList<Usuario> listaUsuarios);

	public void aceptarPromo(Promocion promocionAceptada);
	
	public void aceptarAtraccion(Atraccion atraccion);

	public void imprimirTicket();
}

