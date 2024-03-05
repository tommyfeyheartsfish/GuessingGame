// package Server;
// //Contains utilities for synchronization, mainly dealing with mutexes for accessing and modifying the global context safely

// // Used by ClientHandler, GameController, and GlobalContext to synchronize access to shared resources
// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReentrantLock;
// import java.util.concurrent.locks.Condition;

// public class SynchronizationUtility {
//     private final Lock lock = new ReentrantLock();
//     private final Condition condition = lock.newCondition();

//     // Method to perform a thread-safe action with a lock
//     public void performSafely(Runnable action) {
//         lock.lock();
//         try {
//             action.run();
//         } finally {
//             lock.unlock();
//         }
//     }

//     // Method to wait for a condition
//     public void awaitCondition() throws InterruptedException {
//         lock.lock();
//         try {
//             condition.await(); // Wait for the condition
//         } finally {
//             lock.unlock();
//         }
//     }

//     // Method to signal threads waiting on a condition
//     public void signalCondition() {
//         lock.lock();
//         try {
//             condition.signalAll(); // Signal all waiting threads
//         } finally {
//             lock.unlock();
//         }
//     }
// }

