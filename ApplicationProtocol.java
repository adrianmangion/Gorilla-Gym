import java.sql.*;
import java.util.*;


class ApplicationProtocol{

    public int addBooking(Booking booking){

	int count = 0;
		
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    //Create a statement object
	    Statement myStatement = myConnection.createStatement();
			
	    //Create query based on user data
	    String query = "INSERT INTO Bookings (PersonalTrainerID, SpecialismName, ClientID, Date, StartTime, Endtime) " + 
		" VALUES ("+ booking.getPTID() + ","+"'"+booking.getSpecialism()+"'"+","+ booking.getClientID() +"," +"'"+ booking.getDate() +"'"+
		"," +"'"+ booking.getStartTime() +"'"+ "," +"'"+ booking.getEndTime() +"'"+ ");";
	    
	    //Execute Query
	    count = myStatement.executeUpdate(query);
	}catch(Exception e){
	    System.out.println(e.getMessage());
	}
	finally
	    {
		return count;
	    }
    }
    
    public List<Booking> listAllBookings()
    {
	int count = 0;
	List<Booking> bookings = new ArrayList<Booking>();
	
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    String query = "SELECT * FROM Bookings;";
	    Statement myStatement = myConnection.createStatement();
	    ResultSet rs = myStatement.executeQuery(query);
	    
	    while(rs.next()){
		Booking booking = new Booking(rs.getInt("BookingID"),rs.getInt("PersonalTrainerID"),rs.getString("SpecialismName"), rs.getInt("ClientID"), rs.getString("Date"), rs.getString("StartTime"), rs.getString("EndTime"));
	        bookings.add(booking);
		count++;
	    }
	    
	    rs.close();
	    System.out.println("Selected "+count+" bookings.");
		
	}catch(Exception e){
	    System.out.println(e);
	}
        finally{
	    return bookings;
	}

    }
    public List<Booking> listClientBookings(String ID)
    {
	int count = 0;
	List<Booking> bookings = new ArrayList<Booking>();
	
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    String query ="SELECT * FROM Bookings WHERE ClientID = "+Integer.parseInt(ID)+";";
	    Statement myStatement = myConnection.createStatement();
	    ResultSet rs = myStatement.executeQuery(query);
	    while(rs.next()){
		Booking booking = new Booking(rs.getInt("BookingID"), rs.getInt("PersonalTrainerID"), rs.getString("SpecialismName"), rs.getInt("ClientID"), rs.getString("Date"), rs.getString("StartTime"), rs.getString("EndTime"));
	        bookings.add(booking);
		count++;
	    }

	    System.out.println("ID "+ ID + "has " + bookings.size() + "bookings");
			
	}catch(Exception e)
	    {
		System.out.println(e);
	    }
	finally{
	    return bookings;
	}
    }

    public List<Booking> listPTBookings(String ID){
	int count = 0;
	List<Booking> bookings = new ArrayList<Booking>();
	
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    Statement myStatement = myConnection.createStatement();
	    String query ="SELECT * FROM Bookings WHERE PersonalTrainerID = "+ID+";";
	    ResultSet rs = myStatement.executeQuery(query);
	    while(rs.next()){
		Booking booking = new Booking(rs.getInt("BookingID"), rs.getInt("PersonalTrainerID"), rs.getString("SpecialismName"), rs.getInt("ClientID"), rs.getString("Date"), rs.getString("StartTime"), rs.getString("EndTime"));
	        bookings.add(booking);
		count++;
	    }	    
			
	}catch(Exception e)
	    {
		System.out.println(e);
	    }
	finally{
	    System.out.println("ID "+ ID + " has " + bookings.size() + " bookings");
	    return bookings;
	}
    }

    public List<Booking> listDateBookings(String date){
	int count = 0;
	List<Booking> bookings = new ArrayList<Booking>();
	
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    Statement myStatement = myConnection.createStatement();
	    String query ="SELECT * FROM Bookings WHERE Date = "+"'"+date+"'"+";";
	    ResultSet rs = myStatement.executeQuery(query);
	    while(rs.next()){
		Booking booking = new Booking(rs.getInt("BookingID"),rs.getInt("PersonalTrainerID"), rs.getString("SpecialismName"), rs.getInt("ClientID"), rs.getString("Date"), rs.getString("StartTime"), rs.getString("EndTime"));
	        bookings.add(booking);
		count++;
	    }
			
	}catch(Exception e)
	    {
		System.out.println(e);
	    }
	finally{
	    System.out.println("Date "+ date + " has " + bookings.size() + " bookings");
	    return bookings;
	}
    }

    public int deleteBooking(Booking booking)
    {
	int count = 0;
	
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    Statement myStatement = myConnection.createStatement();
	    String query = "DELETE FROM Bookings WHERE BookingID = "+booking.getBookingID()+";";
		       
	    count = myStatement.executeUpdate(query);

	}catch(Exception e)
	    {
		count = 0;
		System.out.println("Failed to delete booking "+ booking.getBookingID());
	    }
	finally{
	    return count;
	}
    }
    public int updateBooking(Booking booking){
	int count = 0;
	
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    Statement myStatement = myConnection.createStatement();
	    String query = "UPDATE Bookings "+
		"SET PersonalTrainerID ="+booking.getPTID()+", SpecialismName ="+"'"+booking.getSpecialism()+"'"+", ClientID ="+booking.getClientID()+", Date ="+"'"+booking.getDate()+"'"
		+", StartTime="+"'"+booking.getStartTime()+"'"+", EndTime="+"'"+booking.getEndTime()+"'"
		+ " WHERE BookingID ="+booking.getBookingID()+";";
		       
	    count = myStatement.executeUpdate(query);
	    System.out.println("Protocol updated :"+count+" bookings" + "from booking "+booking.getBookingID());
	}catch(Exception e)
	    {
		count = 0;
		System.out.println("Failed to update booking "+ booking.getBookingID());
		e.printStackTrace();
	    }
	finally{
	    return count;
	}
    }
    public List<TrainerSpecialism> listPTSpecialisms(){
	int count = 0;
	List<TrainerSpecialism> ptSList = new ArrayList<TrainerSpecialism>();
	
	try{
	    DBConnector db = new DBConnector("jdbc:mysql://localhost:3306/Gym","root","");
	    Connection myConnection = db.createConnection();
	    String query = "SELECT * FROM TrainerSpecialisms;";
	    Statement myStatement = myConnection.createStatement();
	    ResultSet rs = myStatement.executeQuery(query);
	    
	    while(rs.next()){
		TrainerSpecialism ts  = new TrainerSpecialism(rs.getInt("PersonalTrainerID"), rs.getString("SpecialismName"));
	        ptSList.add(ts);
		count++;
	    }
	    
	    rs.close();
	    System.out.println("Selected "+count+" trainer specialisms");
		
	}catch(Exception e){
	    System.out.println(e);
	}

	finally{
	    return ptSList;
	}
    }
}

//To do: List Clients, Add Client, List Trainers, Add Trainers,List Specialism,Add Specialism.


