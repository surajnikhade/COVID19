package DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection {


	/*
	 * public static void main(String args[]) { try {
	 * Class.forName("com.mysql.jdbc.Driver"); Connection con =
	 * DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root",
	 * "imsuraj18");
	 * 
	 * Statement stmt = con.createStatement(); ResultSet rs =
	 * stmt.executeQuery("select * from users"); while (rs.next())
	 * System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " +
	 * rs.getString(3)); con.close(); } catch (Exception e) { System.out.println(e);
	 * } }
	 */

	public static Connection getDBConnection() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coviddb", "root", "imsuraj18");
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return con;
	}
}
