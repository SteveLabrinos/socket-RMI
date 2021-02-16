package exercise2;

import java.io.*;
import java.net.ServerSocket;

/**
 * @author Steve Labrinos [stalab at linuxmail.org] on 16/2/21
 *
 */

public class MultiServer {
    //  Client's port
    private static final int CLIENT_PORT = 10008;

    public static void main(String[] args) throws IOException {
        //  ServerSocket to invoke the accept method, that returns the client socket
        ServerSocket serverSocketForClients = new ServerSocket(CLIENT_PORT);
        //  Infinite loop that creates a new thread for each client that connects
        try {
            while (true){
                //  One client socket is opened and waiting for connection
                System.out.println ("Αναμονή σύνδεσης κάποιου client...");

                //  Creating a new thread when a client connects
                MultiServerThread serverThread = new MultiServerThread(serverSocketForClients.accept());
                System.out.println("Επιτυχής σύνδεση. Δημιουργήθηκε νέο νήμα για την εξυπηρέτησή του");
                //  starting the thread to begin the game
                serverThread.start();
            }
        }catch (IOException e){
            System.err.println("Αδυναμία σύνδεσης στην πόρτα: " + CLIENT_PORT);
            System.exit(1);
        }
    }
}

