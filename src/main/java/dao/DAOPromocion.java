package dao;

import java.sql.*;
import java.util.LinkedList;
import ofertables.*;

import usuarios.Usuario;

public interface DAOPromocion extends GenericDAO<Promocion>{
	
	public LinkedList<Promocion> leerPromociones() throws SQLException;
	
	public LinkedList<Atraccion> leerAtraccionesIncluidas(int id_promo);
	
	public double calcularCosto(LinkedList<Atraccion> atracciones, String tipo, int esp);
	
	public double calcularDuracion(LinkedList<Atraccion> atracciones);
	
	public boolean tieneCupo();
	
	public LinkedList<Promocion> promosPosibles(LinkedList<Promocion> promos, Usuario usuario);
		
	public String toString();
	
	public int compareTo(Promocion pr);

	public void ofertarPromos(LinkedList<Promocion> promos);
	
	public void ofertarPromos(LinkedList<Promocion> promos, Usuario usuario);
	
	public double verificaPositivo (double valor);

	
		

}
