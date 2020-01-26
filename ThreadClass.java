import java.io.*;
import java.net.*;
import java.util.*;

class ThreadClass implements Runnable{

    private final Socket clientSocket;

    public ThreadClass(Socket socket){
	this.clientSocket = socket;
    }

    @Override
    public void run(){
	//Initialize Models
	List<Booking> bookings = new ArrayList<Booking>();
	ApplicationProtocol appProtocol = new ApplicationProtocol();
	List <TrainerSpecialism> ptSpecialisms = new ArrayList<TrainerSpecialism>();
	//Initialize I/O Streams
	ObjectOutputStream oos = null;
	ObjectInputStream ois  = null;
	DataOutputStream dos   = null;
	DataInputStream dis    = null;
	FileOutputStream fos   = null;
	FileInputStream fis    = null;
	OutputStream out       = null;
	InputStream in         = null;

	String id;
	String command = "";
	int effectedRows;
	Booking booking;
	
	command:while(true){
	    try{
		//Receive command from client through input stream
		in = this.clientSocket.getInputStream();
		dis = new DataInputStream(in);
		command = dis.readUTF();
		System.out.println("received command");
		switch(command){
		case "listall":
		    bookings = appProtocol.listAllBookings();
		    fos = new FileOutputStream("BookingList");
		    oos = new ObjectOutputStream(fos);
		    System.out.println(bookings.size());
		    oos.writeObject(bookings);
		    oos.flush();
		    oos.reset();
		    oos.close();
		    fos.close();
		    command = "";
		    break;

		case "listclient":
		    in = this.clientSocket.getInputStream();
		    dis = new DataInputStream(in);
		    id = dis.readUTF();
		    bookings = appProtocol.listClientBookings(id);
		    fos = new FileOutputStream("BookingList");
		    oos = new ObjectOutputStream(fos);
		    oos.writeObject(bookings);
		    oos.flush();
		    oos.reset();
		    oos.close();
		    fos.close();
		    command = "";
		    break;
		case "listtrainer":
		    in = this.clientSocket.getInputStream();
		    dis = new DataInputStream(in);
		    id = dis.readUTF();
		    bookings = appProtocol.listPTBookings(id);
		    fos = new FileOutputStream("BookingList");
		    oos = new ObjectOutputStream(fos);
		    oos.writeObject(bookings);
		    oos.flush();
		    oos.reset();
		    oos.close();
		    fos.close();
		    command = "";
		    break;

		case "listdate":
		    in = this.clientSocket.getInputStream();
		    dis = new DataInputStream(in);
		    id = dis.readUTF();
		    bookings = appProtocol.listDateBookings(id);
		    fos = new FileOutputStream("BookingList");
		    oos = new ObjectOutputStream(fos);
		    oos.writeObject(bookings);
		    oos.flush();
		    oos.reset();
		    oos.close();
		    fos.close();
		    command = "";
		    break;

		case "add":
		    fis = new FileInputStream("Booking");
		    ois = new ObjectInputStream(fis);
		    booking = (Booking) ois.readObject();
		    effectedRows = appProtocol.addBooking(booking);
		    out = this.clientSocket.getOutputStream();
		    dos = new DataOutputStream(out);
		    dos.writeUTF(Integer.toString(effectedRows));
		    command = "";
		    break;

		case "update":
		    fis = new FileInputStream("Booking");
		    ois = new ObjectInputStream(fis);
		    booking = (Booking) ois.readObject();
		    effectedRows = appProtocol.updateBooking(booking);
		    System.out.println("Updated " + effectedRows + " booking");
		    out = this.clientSocket.getOutputStream();
		    dos = new DataOutputStream(out);
		    dos.writeUTF(Integer.toString(effectedRows));
		    command = "";
		    break;
		    
		case "delete":
		    fis = new FileInputStream("Booking");
		    ois = new ObjectInputStream(fis);
		    booking = (Booking) ois.readObject();
		    effectedRows = appProtocol.deleteBooking(booking);
		    System.out.println("Deleted " + effectedRows + " booking");
		    out = this.clientSocket.getOutputStream();
		    dos = new DataOutputStream(out);
		    dos.writeUTF(Integer.toString(effectedRows));
		    command = "";
		    break;

		case "listtrainerspecialisms":
		    ptSpecialisms = appProtocol.listPTSpecialisms();
		    fos = new FileOutputStream("ListPTS");
		    oos = new ObjectOutputStream(fos);
		    System.out.println(ptSpecialisms.size());
		    oos.writeObject(ptSpecialisms);
		    oos.flush();
		    oos.reset();
		    oos.close();
		    fos.close();
		    command = "";
		    break;
		}
	       
	    }catch(Exception e){
		e.printStackTrace();
		System.out.println("Client "+ this.clientSocket.getInetAddress().getHostAddress() + " has disconnected");
		break command;
	    }
	}
    
    }
}
