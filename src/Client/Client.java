package Client;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

// //The client program should

// Establish a connection to the server
// Execute the Connect call
// Sleep for a random amount of time between 1 and 10 seconds
// Execute the Disconnect call

public class Client
{
    public static void main(String[] args) {
        String serverName = "192.168.0.28";
        int serverPort = 5000;

        try(Socket socket = new Socket(serverName,serverPort))
        {
            System.out.println("Connected to the server");

        }
        catch(IOException e)
        {
            System.out.println("CLient error: "+e.getMessage());
            e.printStackTrace();
        }
    }
}

