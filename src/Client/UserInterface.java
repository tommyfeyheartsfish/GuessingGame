package Client;
//Handles all user interactions, displaying prompts and messages, and collecting user inputs.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserInterface {
    private BufferedReader stdIn;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private ClientContext newClient;

    public UserInterface(Socket socket) throws IOException {
        this.socket = socket;
        this.stdIn = new BufferedReader(new InputStreamReader(System.in));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.newClient =new ClientContext();
    }

    public void start() {
        try {
            String username = promptForUsername();
            out.println(username); // Send the username to the server

            // Handle server response
            String serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);

            // Additional interaction logic here
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String promptForUsername() throws IOException {
        String username = null;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter your username (not a command): ");
            username = stdIn.readLine();

            //Check if the username is a reserved command.
     
            if (username.equalsIgnoreCase("quit") || username.equalsIgnoreCase("exit")) {
                System.out.println("Username cannot be a command. Please try again.");
                continue;
            }

            // Send the username to the server for validation
            out.println("checkUsername " + username); // Assuming the server expects a specific format for checking usernames
            String response = in.readLine();

            if ("Username is taken.".equals(response)) {
                System.out.println("Username is already taken. Please try again.");
            } else if ("Username OK".equals(response)) {
                isValid = true; // Exit the loop if the server indicates the username is not taken
                newClient.setUsername(username);//set the username in the client context
            } else {
                // Handle unexpected server response
                System.out.println("Unexpected server response: " + response);
            }
        }
        return username;
    }

    public static void main(String[] args) {
        // Example usage
        try {
            Socket socket = new Socket("localhost", 12345); // Connect to the server
            UserInterface ui = new UserInterface(socket);
            ui.start(); // Start the user interface interaction
        } catch (IOException e) {
            System.out.println("Unable to start the user interface: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

