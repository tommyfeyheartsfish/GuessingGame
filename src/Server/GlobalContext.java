//A singleton class that maintains global state information (e.g., list of connected clients, shared resources)It also provides thread-safe access methods to modify or read the state.

//Accessed and modified by ClientHandler instances, requiring careful synchronization to prevent race conditions and ensure data consistency.
package Server;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
public class GlobalContext{
    //arraylist that manage the database for the game status, static

    //rank the player based on their score, return the arraylist 
    //private constructor 
   
        // Singleton instance
        private static volatile GlobalContext instance;
    
        // Thread-safe collection for global state
        private final ConcurrentHashMap<String, Client> clientData;
    
        // Private constructor
        private GlobalContext() {
            clientData = new ConcurrentHashMap<>();
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
    
        // Thread-safe method to add an item
        public int addItem(String key, Client value) {
                clientData.put(key, value);
                return 1;
        }
    
        // Thread-safe method to update an item
        public void updateItem(String key, Client newValue) {
            // The same as addItem in functionality, as ConcurrentHashMap replaces the value for the given key
            clientData.put(key, newValue);
        }
    
        // Thread-safe method to remove an item
        public void removeItem(String key) {
            clientData.remove(key);
        }
    
        // Thread-safe method to clear all items
        public void clearItems() {
            clientData.clear();
        }
        
        public boolean keyFound(String key){
            return clientData.containsKey(key);
        }

        // New method to get the number of online clients
        public int getOnlineClientCount() {
            return clientData.size();
        }

        // New method to get a list of online clients
        public Set<String> getOnlineClients() {
            return new HashSet<>(clientData.keySet());
        }
    }
    
