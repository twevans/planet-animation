package animation;

import java.sql.*;

public class CreateStarDB

{
    public static void main(String[] args) throws SQLException

   {
   
    		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    		String dbName="jdbcAstronomyDB";
    		String connectionURL = "jdbc:derby:" + dbName + ";create=true";


      		StarData d = new StarData();

      		String[] stars = new String[182];

      		stars = d.getData();      
      	
      		Connection con = null;

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

            Statement stmt = null;
      
            con = DriverManager.getConnection(connectionURL);	

            stmt = con.createStatement();

            stmt.executeUpdate(createString);




            for(int i=1; i<stars.length; i++){

            	stmt.executeUpdate(

            			"insert into " + dbName +
            			".STARS " +
            			stars[i]);

            }



            String query =

            		"select STAR_ID, STAR_NAME, CONSTELLATION, RA, DN, MAGNITUDE " +
        	
	       			"from " + dbName + ".STARS";



	
            ResultSet rs = stmt.executeQuery(query);

            	while (rs.next()) {

            		int starID = rs.getInt("STAR_ID");
            		String starName = rs.getString("STAR_NAME");
            		String constellation = rs.getString("CONSTELLATION");
            		double ra = rs.getDouble("RA");
            		double dn = rs.getDouble("DN");
            		double magnitude = rs.getDouble("MAGNITUDE");

	   
            		System.out.println(starID + "\t" + starName + "\t" + constellation + "\t" + ra + "\t" + dn + "\t" + magnitude);

	   

            	}

	stmt.close();
              
   }           
}