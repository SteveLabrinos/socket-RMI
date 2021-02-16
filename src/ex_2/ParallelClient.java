package ex_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Steve Labrinos [stalab at linuxmail.org] on 16/2/21
 *
 */

public class ParallelClient {
  //  Server port and ip address of the server
  private static final int SERVER_PORT = 10008;
  private static final String SERVER_IP = "127.0.0.1";

  private final String serverHostname;
  private final int serverPort;
  private BufferedReader in = null;
  private PrintWriter out = null;

  public ParallelClient(String serverHostname, int serverPort) {
    this.serverHostname = serverHostname;
    this.serverPort = serverPort;
  }

  public static void main(String[] args) throws IOException {
    ParallelClient client = new ParallelClient(SERVER_IP, SERVER_PORT);

    client.execute();
  }

  private void connect() {
    System.out.println("Σύνδεση στον server " + serverHostname + " στην πόρτα: " + this.serverPort);
    try {
      Socket controlSocket = new Socket(serverHostname, this.serverPort);
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

  public void execute() throws IOException {
    connect();
    //  Read input from the user, to send to the server
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String fromServer;
    String fromUser;
    //  Server welcoming message about the game
    System.out.println("Server: " + in.readLine());

    while ((fromServer = in.readLine()) != null) {
      System.out.println("Server: " + fromServer);

      if (fromServer.equals("GAME OVER")) {
        break;
      }

      System.out.print("Επιλογή: ");
      fromUser = stdIn.readLine();

      if (fromUser != null) {
        out.println(fromUser);
      }
    }
  }

}
