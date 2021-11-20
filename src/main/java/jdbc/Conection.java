package jdbc;

import java.sql.*;

public class Conection {
	
		protected static String url = "jdbc:sqlite:tierramedia.db";
		protected static Connection conexion;
		
		public static Connection getConnection() throws SQLException{
			if (conexion == null) {
				conexion = DriverManager.getConnection(url);
			}
			return conexion;
		}
		
		public static boolean closeConnection(Statement st, ResultSet rs) throws SQLException {
			if (conexion!= null) {
				st.close();
				rs.close();
				return true;
			} else return false;
		}

		public static boolean closeConnection(Statement st) throws SQLException {
			if (conexion!= null) {
				st.close();
				return true;
			} else return false;
		}

}
