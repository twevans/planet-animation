package animation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Star {
	
	List<Double> raList = new ArrayList<Double>();	
    List<Double> decList = new ArrayList<Double>();	
    //List<Double> mList  = new ArrayList<Double>();

	public Star() throws SQLException {
	
	String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String dbName="jdbcAstronomyDB";
    String connectionURL = "jdbc:derby:" + dbName + ";create=true";
	Connection con = null;
	Statement stmt = null;
  	con = DriverManager.getConnection(connectionURL);	
    stmt = con.createStatement();
    
    String query =

        "select STAR_ID, STAR_NAME, CONSTELLATION, RA, DN, MAGNITUDE " +
        	
        "from " + dbName + ".STARS";

	ResultSet rs = stmt.executeQuery(query);
		
	while (rs.next()) {

	    //int starID = rs.getInt("STAR_ID");
        //String starName = rs.getString("STAR_NAME");
	    //String constellation = rs.getString("CONSTELLATION");
	    double ra = rs.getDouble("RA");
	    double dn = rs.getDouble("DN");
	    //double magnitude = rs.getDouble("MAGNITUDE");

	    raList.add(ra);
	    decList.add(dn);
	    //mList.add(magnitude);
	
	}

	stmt.close();
	}
	
	public List<Double> getRA(){
		return raList;
	}
	
	public List<Double> getDec(){
		return decList;
	}
	
}
