package Server;
//Parses and processes RPC requests, dispatching them to the appropriate handler methods.
//read in from the client 
//
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RPCProcessor {
    private String input;
    private String[] inputParts;
    private Client cl;

    public RPCProcessor(String input, Client client){
        this.input = input; 
        this.cl = client;
        inputParts = processString(input);
        System.out.println(Arrays.toString(inputParts));
    }

    public String rpcProcessor(){
        String response=null;
        switch (inputParts[0]){
            case "checkUsername":
                response = addUser();
                break;
            case "clientOnline":
                response = getOnlineCLients();
                break;
            case "quit":
                response = disconnect();
                break;
            case "guess":
                response = guess();
                break; 
            case "score":
                response = getScore();
                break;
            case "print score":
                response = printScore();
                break;
            case "pass":
                response = printGuesses();
                break;
            default:
                response ="Unknown RPC call: " + input;
                break;
        }
        return response;
    }
    public String addUser(){
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
                cl.setUsername(username);
                GlobalContext.getInstance().addItem(username,cl);
                return "Username OK";
            } 
        }
    }

    public String getOnlineCLients(){
        Set<String> onlineClient = GlobalContext.getInstance().getOnlineClients();
        StringBuilder sb = new StringBuilder();
        sb.append("Players online: ");
        if (onlineClient.isEmpty()) {
        sb.append("None");
        } else {
            int index = 0;
            for (String client : onlineClient) {
                sb.append(client);
                if (index < onlineClient.size() - 1) {
                    sb.append(", "); // Add a comma and space except after the last client
                }
                index++;
            }
        }
        return sb.toString();
    }

     private String disconnect(){
        //remove the player
        GlobalContext.getInstance().removeItem(cl.getUsername());
        return "removed";
     }
 
     private String guess(){
            //call game controller ->call client handler to change the score
            if(inputParts.length!=2)
            {
                return "space found";
            }
            else if(Integer.valueOf(inputParts[1])<0||Integer.valueOf(inputParts[1])>1000)
            {
                return "number out of range";
            }
            else if(cl.hasGuessed())
                return "has guessed";
            else
            {
                String guess = inputParts[1];
                GameController gc = new GameController();
                int count = gc.correctDigits(guess);
                int points = gc.pointGain(count);
                String answer = gc.getAnswer();
                //update local context
                cl.setLastGuessedNum(guess);
                cl.setScore(points);
                cl.setLastCorrectlyGuessedNum(count);
                cl.setHasGuessed(true);
                //update the global context 
                GlobalContext.getInstance().playerGuessed(cl.getUsername());
                return "guess recorded " + answer +" " + count + " "+points;

            }
     } 

     //for printing the scores after the game ended 
     private String printScore(){
         List<Map.Entry<String, Client>> rankedClients = GlobalContext.getInstance().rankClients();
         StringBuilder sb=new StringBuilder();
        sb.append("Rankings:");
        for (int i = 0; i < rankedClients.size(); i++) 
        {
            String username = rankedClients.get(i).getKey();
            int score = rankedClients.get(i).getValue().getScore();
            sb.append((i + 1) + ". " + username + " - Score: " + score);
        }
        return sb.toString();
    }

    //for fethching the current score of the client
    private String getScore(){
        return String.valueOf(cl.getScore());
    }

    //when a client call end game before the game ends, it returns the guesses has been made by other clients 
     private String printGuesses(){
        StringBuilder sb = new StringBuilder();
        List<String> playersWhoGuessed = GlobalContext.getInstance().getPlayersWhoGuessed(); 
    if (playersWhoGuessed.isEmpty()) {
        sb.append("No players have guessed yet.");
    } else {
        for (String playerInfo : playersWhoGuessed) {
            sb.append(playerInfo).append("\n");
        }
    }
    return sb.toString();
     }

     private String[] processString(String input) {
        // Split the input string by spaces
        String[] words = input.split(" ");
        
        // Return the array containing the words
        return words;
    }

}
