import java.io.IOException;
import java.util.*;
import java.io.*;
import java.net.*;
import javafx.stage.Stage;
import javafx.application.Application;

public class Network{

    final String host = "localhost";
    final int port = 1024;
    private Socket socket;

    public void setSocket() throws IOException
    {
	this.socket = new Socket(this.host,this.port);
    }

    public Socket getSocket()
    {
	return this.socket;
    }

    //Send string to server
    public void sendString(String command){
	try{
	    OutputStream out = this.socket.getOutputStream();
	    DataOutputStream dos = new DataOutputStream(out);
	    dos.writeUTF(command);
	    System.out.println("Sent");
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
    //Retrieve list of bookings from 
    public List<Booking> getList(){	
	List <Booking> bookingList = new ArrayList<Booking>();
	
	try{
	    //Create an object input stream so we can receive from the server.
	    FileInputStream fis = new FileInputStream("BookingList");
	    ObjectInputStream ois = new ObjectInputStream(fis);	

	    //Receives a list of all bookings from Server
	    bookingList = (List <Booking>) ois.readObject();
	    System.out.println("got list of: "+bookingList.size());
	    ois.close();
	    fis.close();
	    
	}catch(Exception e){
	    e.printStackTrace();
	}
	return bookingList;
    }

    public List<TrainerSpecialism> getPTSpecialismList(){
	List<TrainerSpecialism> ptSList = new ArrayList<TrainerSpecialism>();
	
	try{
	    //Create an object input stream so we can receive from the server.
	    FileInputStream fis = new FileInputStream("ListPTS");
	    ObjectInputStream ois = new ObjectInputStream(fis);

	    //Create a list of all bookings from Database
	    ptSList = (List <TrainerSpecialism>) ois.readObject();
	    System.out.println("got list of: "+ptSList.size());
	    ois.close();
	    fis.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
	return ptSList;
    }

    public int getMessage(){
	int effectedRows = 0;
	try{
	    InputStream in = this.socket.getInputStream();
	    DataInputStream dis = new DataInputStream(in);
	    effectedRows = Integer.parseInt(dis.readUTF());
	}catch(Exception e){
	    System.out.println("ApplicationProtocol: getMessage() did not get message :(");
	}
	return effectedRows;
    }

    public void sendBooking(Booking booking){
	try{
	FileOutputStream fos = new FileOutputStream("Booking");
	ObjectOutputStream oos = new ObjectOutputStream(fos);
	oos.writeObject(booking);
	oos.flush();
	oos.close();
	fos.close();
	}catch(Exception e){
	    System.out.println("Did not send booking properly :(");
	}	
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException
    {		
	try{
	    Application.launch(GuiView.class, args);
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
}
