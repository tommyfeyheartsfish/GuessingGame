// package Server;

// import java.net.Socket;
// import java.util.concurrent.ConcurrentHashMap;

// public class connectionManager {
//     private final ConcurrentHashMap<String, Socket> connections;

//     public connectionManager() {
//         this.connections = new ConcurrentHashMap<>();
//     }

//     // Adds a new client connection
//     public void addConnection(String clientId, Socket socket) {
//         connections.put(clientId, socket);
//     }

//     // Removes a client connection and cleans up resources
//     public void removeConnection(String clientId) {
//         Socket socket = connections.remove(clientId);
//         if (socket != null) {
//             try {
//                 socket.close();
//             } catch (Exception e) {
//                 // Handle exception (e.g., logging)
//                 e.printStackTrace();
//             }
//         }
//     }

//     // Retrieves a connection by client ID
//     public Socket getConnection(String clientId) {
//         return connections.get(clientId);
//     }

//     // Closes all connections and cleans up resources
//     public void closeAllConnections() {
//         for (Socket socket : connections.values()) {
//             try {
//                 socket.close();
//             } catch (Exception e) {
//                 // Handle exception (e.g., logging)
//                 e.printStackTrace();
//             }
//         }
//         connections.clear();
//     }

//     // Get count of active connections
//     public int getActiveConnectionCount() {
//         return connections.size();
//     }
// }

