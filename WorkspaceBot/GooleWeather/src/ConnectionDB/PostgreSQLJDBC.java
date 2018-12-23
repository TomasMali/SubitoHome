package ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLJDBC {

	public static Connection getConnectionDb() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
					"jdbc:postgresql://ec2-54-247-119-167.eu-west-1.compute.amazonaws.com:5432/d1aarn4nc2lbg2",
					"bkyrtnvsyyjvrf", "065264072dc3c861035d75162e45666f115586e1a427109dc9c59b552768d3e6");
			// c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "..Tomas92");

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		return c;
	}
}