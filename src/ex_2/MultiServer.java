package ex_2;

import java.io.*;
import java.net.ServerSocket;

/**
 * @author Steve Labrinos [stalab at linuxmail.org] on 16/2/21
 *
 */

public class MultiServer {
    private static final int CLIENT_PORT = 10008;
    //  Object's client port. initialized on the constructor. CLIENT_PORT for this exercise
    private final int clientPort;

    private MultiServer (int clientPort) { this. clientPort = clientPort; }

    public int getClientPort () { return this.clientPort; }

    public static void main(String[] args) throws IOException {
        //  Creating a new server object to access its methods
        MultiServer server = new MultiServer(CLIENT_PORT);
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
                serverThread.start();
            }
        }catch (IOException e){
            System.err.println("Αδυναμία σύνδεσης στην πόρτα: " + server.getClientPort());
            System.exit(1);
        }
    }
}

