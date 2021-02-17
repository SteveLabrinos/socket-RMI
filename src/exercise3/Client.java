package exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

/** @author Steve Labrinos [stalab at linuxmail.org] on 17/2/21 */

public class Client extends Thread {
  private static final int SERVER_PORT = 10018;
  private static final String SERVER_IP = "127.0.0.1";
  private static final int NUMBER_RANGE = 100;
  private static final int DELAY = 1000;

  private final String serverHostname;
  private final int serverPort;
  private final Random random = new Random();
  private Socket controlSocket;
  private BufferedReader in = null;
  private PrintWriter out = null;

  //    Client constructor providing the connection string parameters
  public Client(String serverHostname, int serverPort) {
    this.serverHostname = serverHostname;
    this.serverPort = serverPort;
  }

  public static void main(String[] args) throws IOException {

    Client client = new Client(SERVER_IP, SERVER_PORT);
    //  Create a connection with the server
    client.connect();
    //  Start the loop async
    client.start();
    //  Execute the socket logic
    client.execute();
  }

  private void execute() throws IOException {
    String fromServer;
    //  Displaying incoming messages from Master
    //  if any number is already been processed by a specific thread
    while ((fromServer = in.readLine()) != null) {
      System.out.println("Server: " + fromServer);
    }
    //  Closing streams and the socket
    in.close();
    out.close();
    controlSocket.close();
  }

  private void connect() {
    //  Establishing the connection to the Master
    System.out.println("Σύνδεση στον server " + serverHostname + " στην πόρτα: " + this.serverPort);
    try {
      controlSocket = new Socket(serverHostname, this.serverPort);
      in = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
      out = new PrintWriter(controlSocket.getOutputStream(), true);
      System.out.println("Επιτυχής συνδεση");
    } catch (UnknownHostException e) {
      System.err.println("Άγνωστος server: " + serverHostname);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Αδυναμία σύνδεσης στον server: " + serverHostname);
      System.exit(1);
    }
  }

  @Override
  public void run() {
    //  Client thread reproduced random numbers until the connection is killed
    while (true) {
      try {
        sleep(DELAY);
        //  Sending the random number to the Master for process
        out.println(random.nextInt(NUMBER_RANGE));
      } catch (InterruptedException e) {
        System.err.println("Σφάλμα επιβολής καθυστέρησης " + e.toString());
        e.printStackTrace();
      }
    }
  }
}
