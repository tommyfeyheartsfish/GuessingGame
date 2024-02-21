package Client;

import java.io.BufferedReader;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

// Establish a connection to the server
// Execute the Connect call
// Sleep for a random amount of time between 1 and 10 seconds
// Execute the Disconnect call

 
public class client {
    // initialize socket and input output streams
    private Socket socket = null;
    private PrintWriter out;
    private BufferedReader in;
    // constructor to put ip address and port
    public client(String address, int port)
    {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
 
            // PrintWriter to send data to the server
            out = new PrintWriter(socket.getOutputStream(), true);
 
            // BufferedReader to read data from the server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner =new Scanner(System.in);

            String response;
            String messageToSend;
            do{
                //prompt the user for input
                messageToSend = scanner.nextLine();
                out.println(messageToSend);

        // Read and print the response from the server
                response = in.readLine();
                System.out.println(response);
                

        }while(!"disconnect".equalsIgnoreCase(messageToSend));
    } catch (IOException i) {
            System.out.println(i);
        }       
        
        

        // close the connection
        try {
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException i) {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        client client = new client("192.168.0.28", 5000);
    }
}

