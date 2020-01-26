import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.*;

class ServerController{
    
    public static void main(String[]  args)
    {
	final int port = 1024;

	ServerSocket serverSocket = null;

	try{
	    /* 
	     * Loops indefinitely and waits for a connection
	     * from client.
	     */
	    serverSocket = new ServerSocket(port);
	    serverSocket.setReuseAddress(true);
	    while(true){
		Socket client = serverSocket.accept();
		System.out.println("Client "+client.getInetAddress().getHostAddress()+"has connected.");
		ThreadClass thread = new ThreadClass(client);

		//Background thread to handle each client separately
		new Thread(thread).start();
	    }
	}catch(IOException e){
	    System.out.println("Failed to initialize server. Kindly restart.");
	}finally{
	    if(serverSocket != null){
		try{
		    /* When client connection has finished
		     * Close the connection. 
		     */
		    serverSocket.close();
		}catch(IOException e){
		    System.out.println("Failed to close server socket.");
		}
	    }
	}
    }
}
