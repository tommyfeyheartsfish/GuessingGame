package Server;
//GAME LOGIC: player guesses a three-digit number. 

// correctDigits() evaluate the number, return the number of correctly guessed digits in their right position. 

// points() give points to the correctly guessed number and reuturn the points gained in this round. 

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
    public int pointGain(int points){
        
    }

}
