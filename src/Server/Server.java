package Server;

// import java.io.IOException;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.io.InputStream;
// import java.io.OutputStream;

// // When the server gets a new connection request from client. Server will then be in an endless loop servicing this client until the client has disconnected. Once the client has disconnected, the server will then go back to accepting a new connection. This model is a single threaded server that can only handle one client at a time. //

// public class Server 
// {
//     public static void main(String[] args) {
//     int port = 1024;

//     try (ServerSocket serverSocket = new ServerSocket(port)){
//         System.out.println("Server is listening on port "+port);

//         while(true)
//         {
//             Socket clientSocket = serverSocket.accept();
//             System.out.println("New client connected");
//         }
//     }catch (IOException e)
//     {
//         System.out.println("Server exception: " + e.getMessage());
//         e.printStackTrace();
//     }
// }
// }
// A Java program for a Server
import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
 
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");
 
            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
 
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
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}


