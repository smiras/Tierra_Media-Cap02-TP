package test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import dao.DAOUsuario;
import dao.DAOPromocionImpl;
import dao.DAOPromocion;
import ofertables.Atraccion;
import ofertables.Promocion;
import usuarios.Usuario;

public class LosTest {

	@Test
	public void testUsuarioBasico() {
		Usuario Cindriel = new Usuario("Cindriel", 200, 1.5);
	    assertEquals(200, Cindriel.getPresupuesto(), 0.0001);
	    assertEquals(1.5,Cindriel.getTiempodisponible(), 0.0001);
	    assertNotNull(Cindriel.getNombre());
	          }
	
	@Test
	public void testAtraccionBasico() {
	Atraccion Moria = new Atraccion(1, "Moria", 10, 2.0, 6);
	assertEquals(10, Moria.getCosto(), 0.001);
	assertEquals(2.0, Moria.getDuracion(), 0.001);
	assertEquals(6, Moria.getCupo(), 0.001);
	assertNotNull(Moria.getNombre());
		}
	
	@Test (expected = RuntimeException.class)
	public void verificaPositivo(){
		Atraccion Moria1 = new Atraccion(16, "Moria1", -10, -2.0, 6);	
	}
	
	@Test
	public void testPromocionBasico() {
		Atraccion MontañasNubladas = new Atraccion(11, "Montañas Nubladas", 10, 6.0, 9);
		Atraccion RioBruinen = new Atraccion(12, "Rio Bruinen", 2, 1.5, 30);
		LinkedList<Atraccion> atraccionesaceptadas = new LinkedList<Atraccion>();
		atraccionesaceptadas.add(0, MontañasNubladas);
		atraccionesaceptadas.add(1, RioBruinen);
		Promocion promo1 = new Promocion(9,"Pack Trecking","porcentual", 25, atraccionesaceptadas);
		assertEquals(9, promo1.getCosto(), 0.0001);
		assertEquals(7.5,promo1.getDuracion(),0.001);
		assertEquals(atraccionesaceptadas, promo1.getAtraccionesIncluidas());
		assertEquals(9,promo1.calcularCosto(atraccionesaceptadas, "porcentual", 25),0.001);
		assertEquals(7.5,promo1.calcularDuracion(atraccionesaceptadas),0.001);
		
		//Pruebas referidas a promos y atracciones aceptadas
		Usuario Frandalf = new Usuario ("Frandalf", 300, 10.0);
		Frandalf.aceptarPromo(promo1);
		assertEquals(8, MontañasNubladas.getCupo(), 0.001);
		assertEquals(29, RioBruinen.getCupo(), 0.001);
		assertEquals(291,Frandalf.getPresupuesto(),0.0001);
		assertEquals(2.5,Frandalf.getTiempodisponible(),0.001);
        assertTrue(MontañasNubladas.tieneCupo());
			}
	@Test
	public void testOfertador() {
		Usuario Frandalf = new Usuario ("Frandalf", 36, 23.0);
		Atraccion MontañasNubladas = new Atraccion(11, "Montañas Nubladas", 10, 6.0, 9);
		Atraccion RioBruinen = new Atraccion(12, "Rio Bruinen", 2, 1.5, 30);
		LinkedList<Atraccion> atraccionesaceptadas = new LinkedList<Atraccion>();
		atraccionesaceptadas.add(0, MontañasNubladas);
		atraccionesaceptadas.add(1, RioBruinen);
		Promocion promo1 = new Promocion(9,"Pack Trecking","porcentual", 25, atraccionesaceptadas);
		Atraccion LaComarca = new Atraccion(3,"La Comarca",3,6.5,150); 
		Atraccion Lothlorien = new Atraccion(6,"Lothlorien",35,1.0,30);
		Atraccion Erebor = new Atraccion(7,"Erebor",12,3.0,32);
		LinkedList<Atraccion> atraccionesaceptadas1 = new LinkedList<Atraccion>();
		atraccionesaceptadas1.add(0, LaComarca);
		atraccionesaceptadas1.add(1, Lothlorien);
		atraccionesaceptadas1.add(2, Erebor);
		Promocion promo2 = new Promocion(4,"Degustacion", "absoluta",36, atraccionesaceptadas1);
		Atraccion BosqueNegro = new Atraccion(8,"Bosque Negro",3,4.0,12);
		LinkedList<Promocion> promosaceptadas = new LinkedList<Promocion>();
		promosaceptadas.add(0,promo1);
		promosaceptadas.add(1,promo2);
		Frandalf.aceptarPromo(promo2);
		Frandalf.aceptarPromo(promo1);
					}
	}