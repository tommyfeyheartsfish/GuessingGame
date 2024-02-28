package Server;
//Responsible for managing the communication with an individual client. It runs in a separate thread for each client and handles all RPCs

//Runs in its own thread for each client connection.

//Handles all interactions with a client, such as receiving requests, processing them (e.g., by calling methods in GameController), and sending responses back to the client.

//Uses synchronization mechanisms (like mutexes) when accessing or modifying shared resources to ensure thread safety.

    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.io.PrintWriter;
    import java.net.Socket;
    import java.io.IOException;
    
    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private Client cl;
     
    
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.cl = new Client();
            cl.setWriter(out);
        }
    
        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String response = processMessage(inputLine);
                    out.println(response);
                }
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port or listening for a connection");
                System.out.println(e.getMessage());
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private String processMessage(String message) {
            String response; 

                RPCProcessor messagProcessor =new RPCProcessor(message,cl);
                // Process the message from the client
                response= messagProcessor.rpcProcessor();
                
            // }
            return response;
        }
    }
    

