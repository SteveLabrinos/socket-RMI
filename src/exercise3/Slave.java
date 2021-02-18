package exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/** @author Steve Labrinos [stalab at linuxmail.org] on 17/2/21 */

public class Slave {
  private static final int SERVER_PORT = 10028;
  private static final String SERVER_IP = "127.0.0.1";

  private final String serverHostname;
  private final int serverPort;
  private final List<Integer> processedNumbers;
  private Socket controlSocket;
  private BufferedReader in = null;
  private PrintWriter out = null;

  //    Client constructor providing the connection string parameters
  public Slave(String serverHostname, int serverPort) {
    this.serverHostname = serverHostname;
    this.serverPort = serverPort;
    processedNumbers = new ArrayList<>();
  }

  public static void main(String[] args) throws IOException {

    Slave slave = new Slave(SERVER_IP, SERVER_PORT);
    //  Create a connection with the server
    slave.connect();
    //  Execute the socket logic
    slave.execute();
  }

  public void slaveTask(String fromServer) {
    //  Check if the number is already processed by this slave
    int num = Integer.parseInt(fromServer);
    if (processedNumbers.contains(num)) {
      out.println("Ο αριθμός " + num + " έχει ήδη επεξεργαστεί");
    } else {
      processedNumbers.add(num);

      //  Displaying the current slaves list of numbers locally
      System.out.print("\nΑριθμοί που έχουν επεξεργαστεί μέχρι τώρα: ");
      processedNumbers.forEach(p -> System.out.print(p + "  "));
    }
  }

  private void execute() throws IOException {
    String fromServer;
    //  Getting random numbers for process from the Master
    while ((fromServer = in.readLine()) != null) {
      slaveTask(fromServer);
    }
    //  Closing streams and the socket
    in.close();
    out.close();
    controlSocket.close();
  }

  private void connect() {
    //  Establishing the connection to the Master
    System.out.println(
        "Σύνδεση στον server " + this.serverHostname + " στην πόρτα: " + this.serverPort);
    try {
      controlSocket = new Socket(this.serverHostname, this.serverPort);
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
}
