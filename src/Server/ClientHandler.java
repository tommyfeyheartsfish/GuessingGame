package Server;
//Responsible for managing the communication with an individual client. It runs in a separate thread for each client and handles all RPCs

//Runs in its own thread for each client connection.

//Handles all interactions with a client, such as receiving requests, processing them (e.g., by calling methods in GameController), and sending responses back to the client.
//TODO: !!!!!add code to ask for username as the game begins!!!~~~~~~
//Uses synchronization mechanisms (like mutexes) when accessing or modifying shared resources to ensure thread safety.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket clientSocket;
    private String key; //username
    private final String rpcCall[] = {"disconnect","guess","exit","print score"};
    //constructor 
    public ClientHandler(Socket socket){
        this.clientSocket = socket;
        
    }
    private boolean isRPCCall(String s){
        boolean isRPCCall=false;
        for(int i = 0; i<rpcCall.length; i++)
        {
            if(s.equalsIgnoreCase(rpcCall[i]))
            {
                isRPCCall=true;
            }
        }
        return isRPCCall;
    }
    @Override
    public void run(){
        try(
            //to send text to the client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            //to read text from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){
            //ask user for the username 
            // out.println("Username: ");
            key=in.readLine();
            if(GlobalContext.getInstance().keyFound(key))
            {
                out.println("Username is taken.");
            }
            if(isRPCCall(key))
            {
                out.println("Username can not be one of the commands.");
            }
            else 
            { 
                Client newClient = new Client(key);
                GlobalContext.getInstance().addItem(key,newClient);
                out.println("Welcome " + key +"!");
                int onlineClient = GlobalContext.getInstance().getOnlineClientCount();
                out.println("Number of players online: " + onlineClient );
            }
         

            String inputLine;
            while((inputLine = in.readLine())!="disconnect"){
                //process input from the client
                RPCProcessor rpcCall = new RPCProcessor(inputLine);
                //send it to RPC Process
                String response = rpcCall.rpcProcessor();
                out.println(response);
            }
        }catch(IOException e){
            System.err.println("Exception handling client: "+e.getMessage());
            e.printStackTrace();
        }finally{
            try{
                clientSocket.close();
            }catch(IOException e){
                System.err.println("Error closing client socket: "+e.getMessage());
            }
        }

        }
    }
    //call RPC Processor to process the user input 

    //"connect user1 pass1" call GameController, change the user stats, send a message to the server 

    //"disconnect" call GameController, delete user, send a message to the server 
     
    //"guess 343" change the stats, call method in GameController, change the user stats in this class and in GlobalContext, also send the message to the server. 

    //"print score" call GameController, send the result to the server 

    //"end game" call GameController, rank the players, send the results to the server


