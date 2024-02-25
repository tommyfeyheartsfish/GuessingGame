package Server;
//GAME LOGIC: player guesses a three-digit number. 

// correctDigits() evaluate the number, return the number of correctly guessed digits in their right position. 

// points() give points to the correctly guessed number and reuturn the points gained in this round. 

import java.util.Random;
public class GameController {
    private final int ANSWER;
    private final String ANSWER_IN_STRING;

    //constructor 
    GameController(){
        Random rad =new Random();
        int max=1000,min=99;
        ANSWER = rad.nextInt((max - min + 1) + min);
        ANSWER_IN_STRING=String.valueOf(ANSWER);
    }

    public int correctDigits(String guess){
        int count = 0;

        int minLength = Math.min(ANSWER_IN_STRING.length(), guess.length());
        for (int i = 0; i < minLength; i++) {
            if (ANSWER_IN_STRING.charAt(i) == guess.charAt(i)) {
                // Increment the counter if characters match at the same position
                count++;
            }
        }
        return count;
    }

    public int pointGain(int count){
        final int POINTS_PER_COUNT=10;
        return count*POINTS_PER_COUNT;
        
    }
    public String getAnswer()
    {
        return ANSWER_IN_STRING;
    }

}
