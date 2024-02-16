package Server;
//This class contains the implementation of the RPC methods
// Manages game logic, including generating the number to guess, evaluating guesses, and keeping track of scores

//GAME LOGIC: player guesses a three-digit number. 

// connect(userName, passWord) connect client to the server, reuturn an integer indicating the status of the connnection  

//disconnect() disconnect client from the server, return an integer indicating the status of the connection

// correctDigits() evaluate the number, return the number of correctly guessed digits in their right position. 

// points() give points to the correctlu guessed number and reuturn the points gained in this round. 

// getScore() print the scores for each player. 

// endGame() ends the currrent game, displaying scores and winner. 

import java.util.Random;
public class GameController {
    private final int ANSWER;

    //constructor 
    GameController(){
        Random rad =new Random();
        ANSWER = rad.nextInt(1000);
    }

    public int correctDigits(int guess){

    }

}
