package Server;
//Responsible for managing the communication with an individual client. It runs in a separate thread for each client and handles all RPCs

//Runs in its own thread for each client connection.

//Handles all interactions with a client, such as receiving requests, processing them (e.g., by calling methods in GameController), and sending responses back to the client.

//Uses synchronization mechanisms (like mutexes) when accessing or modifying shared resources to ensure thread safety.

public class client {
    //usaer stats 

    //call RPC Processor to process the user input 

    //"connect user1 pass1" call GameController, change the user stats, send a message to the server 

    //"disconnect" call GameController, delete user, send a message to the server 
     
    //"guess 343" change the stats, call method in GameController, change the user stats in this class and in GlobalContext, also send the message to the server. 

    //"print score" call GameController, send the result to the server 

    //"end game" call GameController, rank the players, send the results to the server

}
