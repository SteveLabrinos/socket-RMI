package exercise3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/** @author Steve Labrinos [stalab at linuxmail.org] on 17/2/21 */

public class MasterHandler extends Thread {
  private final Socket clientSocket;
  private final CopyOnWriteArrayList<MasterHandler> slaves;
  private final CopyOnWriteArrayList<MasterHandler> clients;
  private final int port;
  private PrintWriter out = null;
  private BufferedReader in = null;
  private String inputLine;

  public MasterHandler(
      Socket clientSocket,
      int port,
      CopyOnWriteArrayList<MasterHandler> slaves,
      CopyOnWriteArrayList<MasterHandler> clients) {
    this.clientSocket = clientSocket;
    this.slaves = slaves;
    this.clients = clients;
    this.port = port;
  }

  //  run method for client connections
  private void clientHandlerTask() throws IOException {
    Random random = new Random();

    while (true) {
      assert in != null;
      if ((inputLine = in.readLine()) == null) break;
      //  Sending the task to a random slave only if there are slaves connected.
      //  Otherwise ignores the input
      if (!slaves.isEmpty()) {
        slaves.get(random.nextInt(slaves.size())).out.println(inputLine);
      } else {
        System.out.println("Αγνοείται ο αριθμός: " + inputLine + ", λόγω μη ύπαρξης κάποιου slave");
      }
    }
  }

  //  run method for slave connections
  private void slaveHandlerTask() throws IOException {

    while (true) {
      assert in != null;
      if ((inputLine = in.readLine()) == null) break;
      //  Message that a number has already been processed received
      clients.forEach(client -> client.out.println(inputLine));
    }
  }

  private void closeConnection() throws IOException {
    //  Client or slave has disconnected, so the handler removes itself
    //  from the array before closing the socket
    if ((port == Master.CLIENT_PORT)) {
      clients.remove(this);
    } else {
      slaves.remove(this);
    }
    //  Closing the streams and the socket
    in.close();
    out.close();
    clientSocket.close();
  }

  @Override
  public void run() {

    try {
      //  Streams initialization
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      //  Execute client or slave task depending on the socket port
      if (port == Master.CLIENT_PORT) {
        clientHandlerTask();
      } else {
        slaveHandlerTask();
      }
      //  Remove thread from the list and close the socket
      closeConnection();
    } catch (IOException e) {
      System.out.println("Αδυναμία δημιουργίας ροών εισόδου/εξόδου με τον client");
      e.printStackTrace();
    }
  }
}
