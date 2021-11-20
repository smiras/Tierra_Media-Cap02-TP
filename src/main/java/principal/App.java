package principal;

import java.sql.*;
import java.util.LinkedList;
import ofertables.*;
import usuarios.*;

public class App {

	public static void main(String[] args) throws SQLException {
		
		Parque parque = new Parque();
		parque.oferta();
		
		
	}

}
