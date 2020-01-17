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
    List<Double> galLonList = new ArrayList<Double>();
    List<Double> galLatList = new ArrayList<Double>();
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
        	
		//"from " + dbName + ".STARS" + " where CONSTELLATION = 'Scorpio'";
		
		//"from " + dbName + ".STARS" + " where CONSTELLATION = 'Aquila' or CONSTELLATION = 'Cygnus' or CONSTELLATION = 'Gemini' or CONSTELLATION = 'Auriga' or CONSTELLATION = 'Perseus' or CONSTELLATION = 'Andromeda' or CONSTELLATION = 'Pegasus' or CONSTELLATION = 'Orion'";
        
        //"from " + dbName + ".STARS" + " where CONSTELLATION = 'Scorpio' or CONSTELLATION = 'Sagittarius' or CONSTELLATION = 'Aquila'";
        
		//"from " + dbName + ".STARS" + " where STAR_NAME = 'Altair'";
        
		
        
        

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
	    double raGalRad = 3.36603;
	    double dnGalRad = 0.47348;
	    double lNCP = 2.14557;
	    
	    //double magnitude = rs.getDouble("MAGNITUDE");

	    raList.add(ra);
	    decList.add(dn);
	    //mList.add(magnitude);
	    
	    //compute ecliptic latitude
	    double sinLat = Math.sin(dnRadians)*Math.cos(obleclrad) - Math.cos(dnRadians)*Math.sin(obleclrad)*Math.sin(raRadians);
	    double latRadians = Math.asin(sinLat);
	    double latDegrees = latRadians*Planet.radeg;
	    
	    //compute ecliptic longitude
	    double cosLon = Math.cos(raRadians)*Math.cos(dnRadians)/Math.cos(latRadians);
	    double lonRadians = Math.acos(cosLon);
	    double lonHours = lonRadians*radhour;
	    
	    //compute galactic latitude
	    double sinGalLat = Math.sin(dnRadians)*Math.sin(dnGalRad) + Math.cos(dnRadians)*Math.cos(dnGalRad)*Math.cos(raRadians - raGalRad);
	    //double sinGalLat = Math.sin(dnRadians)*Math.sin(dnGalRad) + Math.cos(dnRadians)*Math.cos(dnGalRad)*Math.sin(raRadians - raGalRad);
	    double galLatRadians = Math.asin(sinGalLat);
	    double galLatDegrees = galLatRadians*Planet.radeg;
	    
	    //compute galactic longitude
	    double galLonRadians = lNCP - Math.asin(Math.cos(dnRadians)*Math.sin(raRadians - raGalRad)/Math.cos(galLatRadians));
	    
	    double galLonHours = galLonRadians*radhour;
	    double galLonDegrees = galLonRadians*Planet.radeg;
	    
	    if(ra > 12) {
	    	lonHours = 24 - lonHours;
	    	
	    }
	    
	    if((ra < (17+45/60)) && (ra > 5+46/60)) {
	    	galLonHours = 24 - galLonHours;
	    }
	    
	    lonList.add(lonHours);
	    latList.add(latDegrees);
	    
	    galLonList.add(galLonHours);
	    galLatList.add(galLatDegrees);
	    
	    //System.out.println("sin(b) = " + sinGalLat);
	    System.out.println("galactic longitude (degrees) = " + galLonDegrees);
	    System.out.println("galactic latitude (degrees)  = " + galLatDegrees);
	    System.out.println("");
	    
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
	
	public List<Double> getGalLon(){
		return galLonList;
	}
	
	public List<Double> getGalLat(){
		return galLatList;
	}
	
}
