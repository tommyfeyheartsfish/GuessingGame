package Server;
//Parses and processes RPC requests, dispatching them to the appropriate handler methods.
//read in from the client 
//

public class RPCProcessor {
    private String input;
    private String inputParts[];

    public RPCProcessor(String input){
        this.input = input; 
        //how to solit the string
        inputParts = input.split("\\s+");
        switch (inputParts[0]){
            case "connect":
                //call connection class
                break;
            case "disconnect":
                //call disconnect class
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

    }
    //TODO:how does the connection exccute 
    //
    //TODO:make sure there is no inoutParts[3]
    private void connection(){
        String username = inputParts[1];
        String password = inputParts[2];
        //call 
    }

    //TODO:
     private void disconnect(){

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
