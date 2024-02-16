package Server;

// // When the server gets a new connection request from client. Server will then be in an endless loop servicing this client until the client has disconnected. Once the client has disconnected, the server will then go back to accepting a new connection. This model is a single threaded server that can only handle one client at a time. //

import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and input stream
    private Socket          clientSocket   = null;
    private ServerSocket    serverSocket   = null;
    private DataInputStream in       =  null;
 
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            clientSocket = serverSocket.accept();
            System.out.println("Client accepted");
 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(clientSocket.getInputStream()));
 
            String line = "";
 
            // reads message from client until "Over" is sent
            while (!line.equals("Over"))
            {
                try
                {
                    line = in.readUTF();
                    System.out.println(line);
 
                }
                catch(IOException i)
                {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");
 
            // close connection
            clientSocket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
     // authentication method to validate username and password
	 private static boolean authenticate(String username, String password) {
        // Hardcoding username and password for demonstration purposes
        String validUsername = "user123";
        String validPassword = "pass456";
        String guestUser = "guest";
        String guestPass = "123";
        boolean validUser = username.equals(validUsername) && 
                password.equals(validPassword);
         
        boolean validGuest = username.equals(guestUser) &&
                password.equals(guestPass);
           
        if (validUser || validGuest) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String args[])
    {
    Server server = new Server(5000);
    }
}
