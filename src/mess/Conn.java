 package mess;
import java.sql.*;
public class Conn {
Statement s;

Connection co;
	public Conn() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		co=DriverManager.getConnection("jdbc:mysql://localhost:3306/mess","root","admin");        
		s=co.createStatement();	
	}
	catch (Exception e) {
		System.out.println(e);
	}
}
	
}