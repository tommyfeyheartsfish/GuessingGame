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
        private ScheduledExecutorService scheduler;

    
        // Private constructor
        private GlobalContext() {
            clientData = new ConcurrentHashMap<>();
            hasGuessed = new ConcurrentHashMap<>();
            gameEnded = new AtomicBoolean(false);
            scheduler = Executors.newScheduledThreadPool(1);
            startGame();
        }
       
    
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

        private void startGame() {
            gameStartTime = System.currentTimeMillis();
            gameEnded.set(false);
            // Schedule the game to end after 10 minutes
            scheduler.schedule(this::endGame, 10, TimeUnit.MINUTES);
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
                // Logic to end the game, e.g., print scores/ranks
                scheduler.shutdown(); // Shutdown the scheduler
            }
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
    
