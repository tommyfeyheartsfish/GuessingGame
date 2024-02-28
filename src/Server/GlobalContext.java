//A singleton class that maintains global state information (e.g., list of connected clients, shared resources)It also provides thread-safe access methods to modify or read the state.

//Accessed and modified by ClientHandler instances, requiring careful synchronization to prevent race conditions and ensure data consistency.
package Server;
import java.util.concurrent.ConcurrentHashMap;
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
    
        // Thread-safe collection for global state
        private final ConcurrentHashMap<String, Client> clientData;
        private final ConcurrentHashMap<String, Boolean> hasGuessed; 
        private final AtomicBoolean gameEnded;
        private long gameStartTime;
        private GameController gc;
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
       
        private void startGame() {
            gameStartTime = System.currentTimeMillis();
            gameEnded.set(false);
            // Schedule the game to end after 10 minutes
            scheduler.schedule(this::endGame, 2, TimeUnit.MINUTES);
            gc = new GameController();
        }
    
        

       

        public void startNewGame() {
            resetGameState(); // Reset the game state for a new game
            startGame();      // Start the new game
        }

        private void resetGameState() {
            hasGuessed.clear(); 

            clientData.forEach((key, client) -> {
                client.setLastCorrectlyGuessedNum(0); // Reset the last correctly guessed number
                client.setLastGuessedNum(null);});
        }

        public String guess(String clientId, String guess) {
            // Perform guessing logic, e.g., determine the number of correctly guessed digits
            int correctlyGuessedDigits = gc.correctDigits(guess); // Stub for actual check logic
            int pointGain = gc.pointGain(correctlyGuessedDigits);
        
            Client client = clientData.get(clientId);
            if (client != null) {
                client.setScore(pointGain);
                client.setLastGuessedNum(String.valueOf(guess));
                client.setLastCorrectlyGuessedNum(correctlyGuessedDigits);
                client.setHasGuessed(true);
                playerGuessed(clientId);
            }
            return gc.getAnswer() +" " + correctlyGuessedDigits + " " + pointGain;
        }

        public void playerGuessed(String key) {
            hasGuessed.put(key, true);
            checkAllPlayersGuessed();
        }

        private void checkAllPlayersGuessed() {
            if (hasGuessed.values().stream().allMatch(Boolean::booleanValue) && !gameEnded.get()) {
                endGame();
            }
        }

        public void endGame() {
            if (gameEnded.compareAndSet(false, true)) {
                String message = printScore();
                broadcastMessage(message);  
                scheduler.schedule(this::startNewGame, 5, TimeUnit.SECONDS);
                //assuming server is not shuting down
            }
        }
        public void broadcastMessage(String message) {
            clientData.values().forEach(client -> client.sendMessage(message));
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

        public boolean isGameEnded() {
            return gameEnded.get();
        }

        public int addItem(String key, Client value) {
                clientData.put(key, value);
                return 1;
        }
    
        public void updateItem(String key, Client newValue) {
            // The same as addItem in functionality, as ConcurrentHashMap replaces the value for the given key
            clientData.put(key, newValue);
        }
        
        public void removeItem(String key) {
            clientData.remove(key);
        }
    
        public void clearItems() {
            clientData.clear();
        }
        
        public boolean keyFound(String key){
            return clientData.containsKey(key);
        }

        public int getOnlineClientCount() {
            return clientData.size();
        }

        public Set<String> getOnlineClients() {
            return new HashSet<>(clientData.keySet());
        }
    }
    
