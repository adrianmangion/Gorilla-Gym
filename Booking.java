import java.io.Serializable;

public class Booking implements Serializable{

    //Attributes
    private int _bookingID;
    private int _ptID;
    private int _clientID;
    private String _date;
    private String _startTime;
    private String _specialism;
    private String _endTime;

    //Constructor
    public Booking(int bookingID, int ptID, String specialism, int clientID, String date, String startTime, String endTime)
    {
	super();
	this._bookingID = bookingID;
	this._ptID = ptID;
	this._specialism = specialism;
	this._clientID = clientID;
	this._date = date;
	this._startTime = startTime;
	this._endTime = endTime;
    }

    public Booking(int ptID, String specialism, int clientID, String date, String startTime, String endTime){
	super();
	this._ptID = ptID;
	this._specialism = specialism;
	this._clientID = clientID;
	this._date = date;
	this._startTime = startTime;
	this._endTime = endTime;
    }

    //Getters and Setters
    public void setBookingID(int bookingID){
	this._bookingID = bookingID;
    }
    
    public int getBookingID(){
	return this._bookingID;
    }

    public void setPTID(int ptID){
	this._ptID = ptID;
    }

    public int getPTID(){
	return this._ptID;
    }

    public void setSpecialism(String specialism){
	this._specialism = specialism;
    }

    public String getSpecialism(){
	return this._specialism;
    }

    public void setClientID(int clientID){
	this._clientID = clientID;
    }

    public int getClientID(){
	return this._clientID;
    }

    public void setDate(String date){
	this._date = date;
    }

    public String getDate(){
	return this._date;
    }

    public void setStartTime(String startTime){
	this._startTime = startTime;
    }

    public String getStartTime(){
	return this._startTime;
    }

    public void setEndTime(String endTime){
	this._endTime = endTime;
    }

    public String getEndTime(){
	return this._endTime;
    }
    
    @Override
    public String toString(){
	return "Booking [_bookingID =" + _bookingID + "_ptID=" + _ptID +"_specialism="+_specialism+ "_clientID=" + _clientID + "_date=" + _date + "_startTime =" + _startTime + "_endTime =" + _endTime + "]";
    }
}//End Class
