package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
    public static void main(String[] args) {
    int port = 1024;

    try (ServerSocket serverSocket = new ServerSocket(port)){
        System.out.println("Server is listening on port "+port);

        while(true)
        {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected");
        }
    }catch (IOException e)
    {
        System.out.println("Server exception: " + e.getMessage());
        e.printStackTrace();
    }
}
}

