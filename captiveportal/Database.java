package captiveportal;

import java.sql.*;

public class Database {
	private Connection conn = null;
	  private Statement stmt = null;
	  private ResultSet resultSet = null;
	  private int count = 0;
	  
	  //#######Database details#######
	  private String dbHost = "localhost"; //The ip or hostname of the database host e.g: localhost or 127.0.0.1 etc.
	  private String dbPort = "3306"; //The port the database is listening to
	  private String dbName = "CaptivePortal"; //The name of the scheme
	  private String dbUser = "CaptivePortal"; //The username of the account that can 'Update and Select' on the scheme
	  private String dbPass = "raspberry"; //The password of the account that can 'Update and Select' on the scheme
	  //##############################
	  
	//Create method for checking if ticket/lastname combination exists (On SUCCES => true else => false)
	public boolean Select(String ticket, String lastName) throws Exception {
	    try {
	      //Load the MySQL driver
	      Class.forName("com.mysql.jdbc.Driver");
	      //Setup the connection with the database
	      conn = DriverManager.getConnection("jdbc:mysql://" +dbHost+ ":" +dbPort+ "/" +dbName, dbUser, dbPass);
	      //Create statement to execute query
	      stmt = conn.createStatement();
	      //Execute query
	      resultSet = stmt.executeQuery("select lastname, ticket from users where ticket='" +ticket+ "'and lastname='" +lastName+ "'");
	      //Count the results that are returned (affected rows) this should be 1 (unique ticket/lastname combination)
	      while(resultSet.next())
	      {
	    	  //increment count by 1
	    	  count++;
	      }
	      if (count == 1)
	         {
	    	  	//If there is 1 row with the ticket/lastname combination => return true
	    		return true;
	         }
	    	else
	    	{
	    		return false;
	    	}
	     //If there is an error, throw it
	    } catch (Exception e) {
	      throw e;
	    //Finally close the connections
	    } finally {
	    	if (stmt != null) stmt.close();
	           if (conn != null) conn.close();
	    }

	
	
	}
	//Create method for registering internet usage per user (On SUCCES => true else => false)
	public boolean Update(String ticket, String lastName) throws Exception {
		try {
		//Load the MySQL driver
	    Class.forName("com.mysql.jdbc.Driver");
	    //Setup the connection with the database
	    conn = DriverManager.getConnection("jdbc:mysql://" +dbHost+ ":" +dbPort+ "/" +dbName, dbUser, dbPass);
	    //Create statement to execute query
	    stmt = conn.createStatement();
	    //Execute query
    	int result = stmt.executeUpdate("update users set internet = '1' where ticket='" +ticket+ "' and lastname='" +lastName+ "'");
    	//Result is already an int so, if good => true else => false (executeUpdate returns the number of rows updated, so it should be 1)
    	if(result == 1) {
    		return true;
    	}
    	else {
    		return false;
    	}
    	  //If there is an error, throw it
	    } catch (Exception e) {
	      throw e;
	    //Finally close the connections
	    } finally {
	    	if (stmt != null) stmt.close();
	           if (conn != null) conn.close();
	    }
	}
	//Create method for resetting user in database (set internet to 0) (On SUCCES => true else => false)
	public boolean Reset(String ticket, String lastName) throws Exception {
		try {
		//Load the MySQL driver
	    Class.forName("com.mysql.jdbc.Driver");
	    //Setup the connection with the database
	    conn = DriverManager.getConnection("jdbc:mysql://" +dbHost+ ":" +dbPort+ "/" +dbName, dbUser, dbPass);
	    //Create statement to execute query
	    stmt = conn.createStatement();
	    //Execute query
    	int result = stmt.executeUpdate("update users set internet = '0' where ticket='" +ticket+ "' and lastname='" +lastName+ "'");
    	//Result is already an int so, if good => true else => false (executeUpdate returns the number of rows updated, so it should be 1)
    	if(result == 1) {
    		return true;
    	}
    	else {
    		return false;
    	}
    	  //If there is an error, throw it
	    } catch (Exception e) {
	      throw e;
	    //Finally close the connections
	    } finally {
	    	if (stmt != null) stmt.close();
	           if (conn != null) conn.close();
	    }
    }
}