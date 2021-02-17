package exercise3;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CopyOnWriteArrayList;

/** @author Steve Labrinos [stalab at linuxmail.org] on 17/2/21 */

public class Master extends Thread {
  static final int CLIENT_PORT = 10018;
  static final int SLAVE_PORT = 10028;
  private static final CopyOnWriteArrayList<MasterHandler> clients = new CopyOnWriteArrayList<>();
  private static final CopyOnWriteArrayList<MasterHandler> slaves = new CopyOnWriteArrayList<>();

  private final int port;
  private final ServerSocket serverSocket;

  public Master(int port) throws IOException {
    this.port = port;
    serverSocket = new ServerSocket(port);
  }

  public static void main(String[] args) throws IOException {
    //  Create 2 objects to listen to the different ports with separate threads
    Master clientListener = new Master(CLIENT_PORT);
    Master slaveListener = new Master(SLAVE_PORT);
    //  Starting the client/slave listeners
    clientListener.start();
    slaveListener.start();
  }

  @Override
  public void run() {
    while (true) {
      try {
        //  Create a new thread handler to communicate with clients/slaves
        //  providing the different connection port
        MasterHandler masterThread =
            new MasterHandler(serverSocket.accept(), port, slaves, clients);
        //  Add the thread to the appropriate list of connections
        String connectionType;
        if (port == CLIENT_PORT) {
          clients.add(masterThread);
          connectionType = "client";
        } else {
          slaves.add(masterThread);
          connectionType = "slave";
        }

        System.out.println(
            "Επιτυχής σύνδεση "
                + connectionType
                + ". Δημιουργήθηκε νέο νήμα για την εξυπηρέτησή του");

        //  Start the connection thread
        masterThread.start();
      } catch (IOException e) {
        System.err.println("Σφάλμα εισόδου/εξόδου: " + e.getMessage());
        e.printStackTrace();
      }
    }
  }
}
