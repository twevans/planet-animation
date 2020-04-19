package animation;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Star2 {
	
	List<Double> raList  = new ArrayList<Double>();	
    List<Double> decList = new ArrayList<Double>();	
    List<Double> lonList = new ArrayList<Double>();
    List<Double> latList = new ArrayList<Double>();
    List<Double> magList = new ArrayList<Double>();
	
     public Star2() throws SQLException, FileNotFoundException {
    	
    	//try { 
    	 
    	CreateStarDB2.main(null);
    	
    	//} catch (Exception Ex) {
    		
    	//}
		
    	String connectionURL = "jdbc:derby:" + CreateStarDB2.dbName + ";create=true";
    	
    	
    	Connection con = DriverManager.getConnection(connectionURL);
    	Statement stmt = con.createStatement();
		
		
		
		
		
		String query =

				"select RA, DN, MAG " +
        
				"from " + CreateStarDB2.dbName + ".STARS";
		
		ResultSet rs = stmt.executeQuery(query);
		
		while (rs.next()) {

			double ra = rs.getDouble("RA");
			double dn = rs.getDouble("DN");
			double mag = rs.getDouble("MAG");
			double raRadians = 15*ra/Planet.radeg;
			double dnRadians = dn/Planet.radeg;
			double radhour = 12/Math.PI;
			double obleclrad = Planet.oblecl/Planet.radeg;
			
			//System.out.println(ra);
			//System.out.println(dn);
	    
			raList.add(ra);
			decList.add(dn);
			magList.add(mag);
	    
			//compute ecliptic latitude
			double sinLat = Math.sin(dnRadians)*Math.cos(obleclrad) - Math.cos(dnRadians)*Math.sin(obleclrad)*Math.sin(raRadians);
			double latRadians = Math.asin(sinLat);
			double latDegrees = latRadians*Planet.radeg;
	    
			//compute ecliptic longitude
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
     
     public static void main(String[] args) throws SQLException, FileNotFoundException {
    	 Star2 teststar2 = new Star2();
     }
    
	/* public static void main(String[] args) throws SQLException, FileNotFoundException

	   {
		
		CreateStarDB2.main(null);
		
		String connectionURL = "jdbc:derby:" + CreateStarDB2.dbName;
    	
    	
    	Connection con = DriverManager.getConnection(connectionURL);
    	Statement stmt = con.createStatement();
		
		//String dbName = CreateStarDB2.dbName;
		
		
		
		String query =

				"select RA, DN, MAG " +
        
				"from " + CreateStarDB2.dbName + ".STARS";
		
		ResultSet rs = stmt.executeQuery(query);
		
		while (rs.next()) {

			double ra = rs.getDouble("RA");
			double dn = rs.getDouble("DN");
			double raRadians = 15*ra/Planet.radeg;
			double dnRadians = dn/Planet.radeg;
			double radhour = 12/Math.PI;
			double obleclrad = Planet.oblecl/Planet.radeg;
			double raGalRad = 3.36603;
			double dnGalRad = 0.47348;
			double lNCP = 2.14557;
	    
			raList.add(ra);
			decList.add(dn);
	    
			//compute ecliptic latitude
			double sinLat = Math.sin(dnRadians)*Math.cos(obleclrad) - Math.cos(dnRadians)*Math.sin(obleclrad)*Math.sin(raRadians);
			double latRadians = Math.asin(sinLat);
			double latDegrees = latRadians*Planet.radeg;
	    
			//compute ecliptic longitude
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
		
	
		
	   } */
     
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
 	
 	public List<Double> getMag(){
 		return magList;
 	}

}
