import java.sql.*;

public class DBConnector{

    private String _dbUrl;
    private String _username;
    private String _password;


    DBConnector(String dbUrl, String username, String password)
    {
	this._dbUrl = dbUrl;
	this._username = username;
	this._password = password;
    }
    
    public Connection createConnection()
    {
	try{
	    Connection connection = DriverManager.getConnection(_dbUrl, _username, _password);
	    System.out.println("Successfully connected...");
	    return connection;
	}catch(Exception e)
	    {
		System.out.println("Failed to connect to database...");
		return null;
	    }
    }

    public void closeConnection(Connection connection)
    {
	try{
	    connection.close();    
	    System.out.println("Database disconnected");
	}catch(Exception e){
	    System.out.println("Failed to disconnect database connection...");
	}
    }
}
