package animation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Star {
	
	List<Double> raList  = new ArrayList<Double>();	
    List<Double> decList = new ArrayList<Double>();	
    List<Double> lonList = new ArrayList<Double>();
    List<Double> latList = new ArrayList<Double>();
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
        	
        //"from " + dbName + ".STARS" + " where CONSTELLATION = 'Bootes'";
        
		
        
        

	ResultSet rs = stmt.executeQuery(query);
		
	while (rs.next()) {

	    //int starID = rs.getInt("STAR_ID");
        //String starName = rs.getString("STAR_NAME");
	    //String constellation = rs.getString("CONSTELLATION");
	    double ra = rs.getDouble("RA");
	    double dn = rs.getDouble("DN");
	    double raRadians = 15*ra/Planet.radeg;
	    double dnRadians = dn/Planet.radeg;
	    double radhour = 12/Math.PI;
	    double obleclrad = Planet.oblecl/Planet.radeg;
	    //double magnitude = rs.getDouble("MAGNITUDE");

	    raList.add(ra);
	    decList.add(dn);
	    //mList.add(magnitude);
	     
	    double sinLat = Math.sin(dnRadians)*Math.cos(obleclrad) - Math.cos(dnRadians)*Math.sin(obleclrad)*Math.sin(raRadians);
	    double latRadians = Math.asin(sinLat);
	    double latDegrees = latRadians*Planet.radeg;
	    
	    double cosLon = Math.cos(raRadians)*Math.cos(dnRadians)/Math.cos(latRadians);
	    double lonRadians = Math.acos(cosLon);
	    double lonHours = lonRadians*radhour;
	    
	    
	    if(ra > 12) {
	    	lonHours = 24 - lonHours;
	    }
	    
	    lonList.add(lonHours);
	    latList.add(latDegrees);
	}

	stmt.close();
	}
	
	public List<Double> getRA(){
		return raList;
	}
	
	public List<Double> getDec(){
		return decList;
	}
	
	public List<Double> getLon(){
		return lonList;
	}
	
	public List<Double> getLat(){
		return latList;
	}
	
	
}
