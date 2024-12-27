package DAO;
import java.sql.*;

public class DBConnection {
	private static final String url="jdbc:mysql://localhost:3306/gestiondeconges";
	private static final String user="root";
	private static final String password="YES";
	static Connection connection=null;
	
	public static Connection getConnection() throws SQLException {
		
		if(connection==null|| connection.isClosed()) {
			
			try {
				
                Class.forName("com.mysql.cj.jdbc.Driver"); 
				connection=DriverManager.getConnection(url, user, password);
                System.out.println("Connexion reussie !");

			}catch(ClassNotFoundException |SQLException e) {
				e.printStackTrace();
                throw new RuntimeException("Erreur lors de la connexion a la base de donnees !");
			}
		
		}
		return connection;

	}
	
	/*public static void closeConnection() {
	
		if(connection!=null) {
			try {
				
				connection.close();
				System.out.println("Connexion ferme.");
			
			}catch(SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Erreur lors de la fermeture de la connexion!");
			}
		}
	}*/
}
