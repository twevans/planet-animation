package animation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class CreateStarDB2

{
	
	public static String dbName="jdbcAstronomyDB2";
	//public static Connection con;
	//public static Statement stmt;
	
    public static void main(String[] args) throws SQLException, FileNotFoundException

   {
   
    	String connectionURL = "jdbc:derby:" + dbName + ";create=true";
    	
    	String csvFilePath = "hygdata_v3_6.csv";
    	
    	
    	int batchSize = 20;
    	
    	Connection con = DriverManager.getConnection(connectionURL);
    	Statement stmt = con.createStatement();
        
    	try {
    		
    		String createString = 

            		"create table " + dbName +
            		".STARS " +
            		"(ID integer NOT NULL, " +
            		"RA double NOT NULL, " +
            		"DN double NOT NULL, " +
            		"MAG double NOT NULL, " +
            		"CNS char(3) NOT NULL, " +
            		"PRIMARY KEY (ID))";

    		stmt.executeUpdate(createString);
    		
    		String sql = "INSERT INTO " + dbName + ".STARS" + "(id, ra, dn, mag, cns) VALUES (?, ?, ?, ?, ?)";
    		
    		PreparedStatement statement = con.prepareStatement(sql);
    		
    		//statement.executeUpdate(createString);
    		
            
            
        	
        	con.setAutoCommit(false);
    		
    		
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
            
            int count = 0;
            
            lineReader.readLine();
            
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                String id = data[0];
                String ra = data[1];
                String dn = data[2];
                String mag = data[3];
                String cns = data[4];
 
                int sqlId = Integer.valueOf(id);
                double sqlRa = Double.valueOf(ra);
                double sqlDn = Double.valueOf(dn);
                double sqlMag = Double.valueOf(mag);
                
                //System.out.println(cns);
                               
                statement.setInt(1, sqlId);
                statement.setDouble(2, sqlRa);
                statement.setDouble(3, sqlDn);
                statement.setDouble(4, sqlMag);
                statement.setString(5, cns);
 
                statement.addBatch();
 
                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }
            
            lineReader.close();
            

    	
        //} catch (SQLException ex) {
          //  ex.printStackTrace();
        //} catch (IOException ex) {
          //  System.err.println(ex);
        } catch (Exception ex) {
        	
        }

    	con.commit();
    	stmt.close();
    	
       
              
   } 
    
    
}