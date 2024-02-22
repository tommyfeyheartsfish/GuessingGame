package Server;

public class Client {
    private int score;
    private String lastGuessedNum;
    private int lastCorrectlyGuessedNum;
    private String username; // Assuming you need a username field as well
    private boolean status; 

    // Constructor
    public Client(String username) {
        this.username = username;
        this.score = 0; // Initialized to 0
        this.lastGuessedNum = null; // Initialized to null
        this.lastCorrectlyGuessedNum = 0; // Initialized to 0
        this.status=false;
    }
    
    //constructor overload 
    public Client(){
        this.username = null;
        this.score = 0; // Initialized to 0
        this.lastGuessedNum = null; // Initialized to null
        this.lastCorrectlyGuessedNum = 0; // Initialized to 0
        this.status=false;
    }

    // Getters and Setters
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public boolean getStatus(){
        return status;
    }

    public void flipStatus(){
        if(status)
        {
            status=false;
        }
        if(status)
        {
            status=true;
        }
    }

    public String getLastGuessedNum() {
        return lastGuessedNum;
    }

    public void setLastGuessedNum(String lastGuessedNum) {
        this.lastGuessedNum = lastGuessedNum;
    }

    public int getLastCorrectlyGuessedNum() {
        return lastCorrectlyGuessedNum;
    }

    public void setLastCorrectlyGuessedNum(int lastCorrectlyGuessedNum) {
        this.lastCorrectlyGuessedNum = lastCorrectlyGuessedNum;
    }

    public String getUsername() {
        return username;
    }
}

