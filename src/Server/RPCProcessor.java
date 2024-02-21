package Server;
//Parses and processes RPC requests, dispatching them to the appropriate handler methods.
//read in from the client 
//
//TODO: figure out how to delete a client!!!~~~~
public class RPCProcessor {
    private String input;
    private String inputParts[];

    public RPCProcessor(String input){
        this.input = input; 
        inputParts = input.split("\\s+");
    }

    public String rpcProcessor(){
        String response=null;
        switch (inputParts[0]){
            case "username":
                response = addUser();
                break;
            case "disconnect":
                response = disconnect();
                break;
            case "guess":
                //call guess class
                break; 
            case "print score":
                //call print score class
                break;
            case "end game":
                //call end game class
                break;
            default:
                System.out.println("Unknown RPC call: " + input);
                break;
        }
        return response;
    }
    private String addUser(){
        //if there is a third item in the array 
        //if there is no second item in the array 
        if(inputParts.length!=2)
        {
            return "username can't have space.";
        }
        else 
        {
            String username = inputParts[1];
           
            if(GlobalContext.getInstance().keyFound(username))
            {
                return "Username is taken.";
            }
            else 
            { 
                Client newClient = new Client(username);
                GlobalContext.getInstance().addItem(username,newClient);
                return "Welcome "+username;
            } 
        }
    }

     private String disconnect(){
        

     }

     private void guess(){

     } 
     private void printScore(){

     }
     private void endGame(){

     }

        //split the input by the space 

        //switch to process the input 
        //input examples//connect user1 pass1
                            //call client Handler-> call global context to check the authentication
                        //disconnect 
                            //call client handler -> call server 
                        //guess 456 
                            //call game controller ->call client handler to change the score
                        //guess 234
                        //print score
                            //call client handler 
                        //end game 
                            //call client handler -> call global handler
        //
    
}
