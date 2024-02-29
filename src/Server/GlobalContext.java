package Server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GlobalContext{
    //arraylist that manage the database for the game status, static

    //rank the player based on their score, return the arraylist 
    //private constructor 
   
        // Singleton instance
        private static volatile GlobalContext instance;
        // private static final GlobalContext instance = new GlobalContext();
        // Thread-safe collection for global state
        private final ConcurrentHashMap<String, Client> clientData;
        private final ConcurrentHashMap<String, Boolean> hasGuessed; 
        private final AtomicBoolean gameEnded;
        private long gameStartTime;
 
        private ScheduledExecutorService scheduler;

        // Method to get the singleton instance
        public static GlobalContext getInstance() {
            if (instance == null) {
                synchronized (GlobalContext.class) {
                    if (instance == null) {
                        instance = new GlobalContext();
                    }
                }
            }
            return instance;
        }

        // Private constructor
        private GlobalContext() {
            clientData = new ConcurrentHashMap<>();
            hasGuessed = new ConcurrentHashMap<>();
            gameEnded = new AtomicBoolean(false);
            scheduler = Executors.newScheduledThreadPool(1);
            startGame();
        }
       
        public synchronized void startGame() {
            gameStartTime = System.currentTimeMillis();
            gameEnded.set(false);
            // Schedule the game to end after 10 minutes
            scheduler.schedule(this::endGame, 2, TimeUnit.MINUTES);
            GameController.getInstance().setNewAnwer();;
        }
    
        

       

        public void startNewGame() {
            resetGameState(); // Reset the game state for a new game
            startGame();      // Start the new game
        }

        private synchronized void resetGameState() {
            hasGuessed.clear(); 

            clientData.forEach((key, client) -> {
                client.setLastCorrectlyGuessedNum(0); // Reset the last correctly guessed number
                client.setLastGuessedNum(null);
            client.setHasGuessed(false);});
        }

        public synchronized String guess(String clientId, String guess) {
            // Perform guessing logic, e.g., determine the number of correctly guessed digits
            int correctlyGuessedDigits = GameController.getInstance().correctDigits(guess); // Stub for actual check logic
            int pointGain = GameController.getInstance().pointGain(correctlyGuessedDigits);
        
            Client client = clientData.get(clientId);
            if (client != null) {
                client.setScore(pointGain);
                client.setLastGuessedNum(String.valueOf(guess));
                client.setLastCorrectlyGuessedNum(correctlyGuessedDigits);
                client.setHasGuessed(true);
                // playerGuessed(clientId);
            }
            return GameController.getInstance().getAnswer() +" " + correctlyGuessedDigits + " " + pointGain;
        }

        public synchronized void playerGuessed(String key, boolean played) {
            hasGuessed.put(key, played);
            
        }

        public synchronized String checkAllPlayersGuessed() {
            if (hasGuessed.values().stream().allMatch(Boolean::booleanValue) && !gameEnded.get()) {
               return endGame();
            }
            else 
                return "waiting for other players"; 

        }

        public String endGame() {
            String message=null;
            if (gameEnded.compareAndSet(false, true)) {
                message = printScore();
                // broadcastMessage(message);  
                scheduler.schedule(this::startNewGame, 5, TimeUnit.SECONDS);
                //assuming server is not shuting down
                
            }
            return message;
        }
        public void broadcastMessage(String message) {
                
                // Snapshot the clients outside of synchronization block
        List<Client> clientsSnapshot;
        synchronized (this) {
            clientsSnapshot = new ArrayList<>(clientData.values());
        }
        // Now broadcast outside of the synchronized block
        for (Client client : clientsSnapshot) {
            client.sendMessage(message);
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
                sb.append((i + 1) + ". " + username + " - Score: " + score+"     ");
            }
            return sb.toString();
        }
        public List<Map.Entry<String, Client>> rankClients() {
        // Sort clients by score in descending order
        return clientData.entrySet().stream()
                .sorted(Map.Entry.<String, Client>comparingByValue(Comparator.comparingInt(Client::getScore)).reversed())
                .collect(Collectors.toList());
        }
     
        public List<String> getPlayersWhoGuessed() {
            return clientData.values().stream()
                    .filter(Client::hasGuessed) // Filter clients who have guessed
                    .map(client -> client.getUsername() + " - Correctly Guessed Digits: " + client.getLastCorrectlyGuessedNum())
                    .collect(Collectors.toList()); // Collect results into a list
        }

        public synchronized boolean isGameEnded() {
            return gameEnded.get();
        }

        public synchronized int addItem(String key, Client value) {
                clientData.put(key, value);
                return 1;
        }
    
        public synchronized void updateItem(String key, Client newValue) {
            // The same as addItem in functionality, as ConcurrentHashMap replaces the value for the given key
            clientData.put(key, newValue);
        }
        
        public synchronized void removeItem(String key) {
            clientData.remove(key);
        }
    
        public synchronized void clearItems() {
            clientData.clear();
        }
        
        public synchronized boolean keyFound(String key){
            return clientData.containsKey(key);
        }

        public synchronized int getOnlineClientCount() {
            return clientData.size();
        }

        public synchronized Set<String> getOnlineClients() {
            return new HashSet<>(clientData.keySet());
        }
    }
    
