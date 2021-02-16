package exercise1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServer {
    private static final int CLIENT_PORT = 10007;
    protected Socket clientSocket;
    private final int clientPort;

    private SingleServer(int clientPort) { this. clientPort = clientPort; }

    private void setClientSocket(Socket clientSoc){
        this.clientSocket = clientSoc;
    }

    public int getClientPort () { return this.clientPort; }

    public void execute () throws IOException {
        //  Creating the input and output streams to communicate with the client
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine, outputLine;

        //  RPSGame initialization
        RPSGame game = new RPSGame();
        outputLine = game.play(null);
        out.println(outputLine);
        //  RPSGame session
        while ((inputLine = in.readLine()) != null ) {
            outputLine = game.play(inputLine);
            out.println(outputLine);
            if (outputLine.equals("GAME OVER")) break;
        }
    }

    public static void main(String[] args) throws IOException {
        //  Creating a new server object to access its methods
        SingleServer server = new SingleServer(CLIENT_PORT);
        ServerSocket serverSocketForClients = new ServerSocket(CLIENT_PORT);

        //  Open new socket on port 10007
//        ServerSocket serverSocket = null;
        try {
            while (true){
                //  One client socket is opened and waiting for connection
                System.out.println ("Αναμονή σύνδεσης κάποιου client...");
                server.setClientSocket(serverSocketForClients.accept());
                //  Establishing connection with the client
                server.execute();
            }
        }catch (IOException e){
            System.err.println("Αδυναμία σύνδεσης στην πόρτα: " + server.getClientPort());
            System.exit(1);
        }
    }
}

