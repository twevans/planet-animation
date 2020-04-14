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
    
		// database creation: start
    
		try {
    
			StarData d = new StarData();
			String[] stars = new String[182];
			stars = d.getData();      

    
    
    
			//Connection con = null;

			String createString = 

					"create table " + dbName +
					".STARS " +
					"(STAR_ID integer NOT NULL, " +
					"STAR_NAME varchar(40) NOT NULL, " +
					"CONSTELLATION varchar(40) NOT NULL, " +
					"RA double NOT NULL, " +
					"DN double NOT NULL, " +
					"MAGNITUDE double NOT NULL, " +        	
					"PRIMARY KEY (STAR_ID))";

			//Statement stmt = null;
    
			con = DriverManager.getConnection(connectionURL);	
			stmt = con.createStatement();

			stmt.executeUpdate(createString);

			//for(int i=1; i<182; i++){

			for(int i=1; i<stars.length; i++){

				stmt.executeUpdate(

						"insert into " + dbName +
						".STARS " +
						stars[i]);

			}

		} catch (Exception Ex) {
	
		}
	
    // database creation: end
    
		String query =

				"select STAR_ID, STAR_NAME, CONSTELLATION, RA, DN, MAGNITUDE " +
        
				"from " + dbName + ".STARS";
        	
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
	    
			//compute galactic latitude
			double sinGalLat = Math.sin(dnRadians)*Math.sin(dnGalRad) + Math.cos(dnRadians)*Math.cos(dnGalRad)*Math.cos(raRadians - raGalRad);
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
