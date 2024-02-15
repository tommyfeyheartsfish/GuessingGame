package Client;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

public class Client
{
    public static void main(String[] args) {
        String serverName = "192.168.0.28";
        int serverPort = 1024;

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

