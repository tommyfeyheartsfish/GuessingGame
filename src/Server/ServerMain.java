package Server;

//The main class that starts the server and listens for incoming client connections.

//When a new client connection is accepted, Server creates a new instance of Client and passes the client's socket to it.

//It then spawns a new thread for the Client instance to manage the communication with this client.

import java.net.*;
import java.io.*;
 
public class ServerMain
{
    //initialize socket and input stream
    private Socket          clientSocket   = null;

    private int port;
    private boolean running =false;

    // constructor with port
    public ServerMain(int port)
    {
        this.port = port;
    }

    public void startServer(){
        running = true;
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server started on port "+ port);

            while(running){
                try{
                    clientSocket = serverSocket.accept();

                    //create and start a new Client thread for each client connection
                    new Thread(new ClientHandler(clientSocket)).start();
                }catch(IOException e){
                    System.err.println("error connection: "+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
            catch(IOException e){
                System.err.println("Could not listen on port "+port+": "+e.getMessage());
                e.printStackTrace();
            }
        }
        public void stopServer(){
            running = false;
        }
    
    //     // starts server and waits for a connection
    //     try
    //     {
    //         serverSocket = new ServerSocket(port);
    //         System.out.println("Server started");
 
    //         System.out.println("Waiting for a client ...");
 
    //         clientSocket = serverSocket.accept();
    //         System.out.println("Client accepted");
 
    //         // takes input from the client socket
    //         in = new DataInputStream(
    //             new BufferedInputStream(clientSocket.getInputStream()));
 
    //         String line = "";
 
    //         // reads message from client until "Over" is sent
    //         while (!line.equals("Over"))
    //         {
    //             try
    //             {
    //                 line = in.readUTF();
    //                 System.out.println(line);
 
    //             }
    //             catch(IOException i)
    //             {
    //                 System.out.println(i);
    //             }
    //         }
    //         System.out.println("Closing connection");
 
    //         // close connection
    //         clientSocket.close();
    //         in.close();
    //     }
    //     catch(IOException i)
    //     {
    //         System.out.println(i);
    //     }
    // }
     // authentication method to validate username and password
	//  private static boolean authenticate(String username, String password) {
    //     // Hardcoding username and password for demonstration purposes
    //     String validUsername = "user123";
    //     String validPassword = "pass456";
    //     String guestUser = "guest";
    //     String guestPass = "123";
    //     boolean validUser = username.equals(validUsername) && 
    //             password.equals(validPassword);
         
    //     boolean validGuest = username.equals(guestUser) &&
    //             password.equals(guestPass);
           
    //     if (validUser || validGuest) {
    //         return true;
    //     } else {
    //         return false;
    //     }
    // }
    
    public static void main(String args[])
    {
        int port = 5000;
        ServerMain server = new ServerMain(port);
        server.startServer();
    }
}
