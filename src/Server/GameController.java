package Server;

import java.util.Random;
public class GameController {
    private static GameController instance;
    private int answer;
    private String answer_in_String;

    //constructor 
    GameController(){
       answer = 0;
       answer_in_String=null;
    }

    public static synchronized GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
    public void setNewAnwer(){
        Random rad =new Random();
        // int max=1000,min=100;
        answer = rad.nextInt(900) + 100; 
        answer_in_String=String.valueOf(answer);
    }

    public int correctDigits(String guess){
        int count = 0;

        int minLength = Math.min(answer_in_String.length(), guess.length());
        for (int i = 0; i < minLength; i++) {
            if (answer_in_String.charAt(i) == guess.charAt(i)) {
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
        return answer_in_String;
    }

}
