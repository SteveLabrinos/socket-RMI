package exercise4;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/** @author Steve Labrinos [stalab at linuxmail.org] on 16/2/21 */

public class RPSGameClient {
  //  Protocol's port
  private static final int RMI_PORT = 10001;
  private static final String LOOKUP = "RPSGameOperations";

  public static void main(String[] args) throws RemoteException, NotBoundException {
    Registry registry = LocateRegistry.getRegistry(RMI_PORT);
    //  RPSGameOperations object to call its methods
    RPSGameOperations gameOperations = (RPSGameOperations) registry.lookup(LOOKUP);

    System.out.println("Επιτυχής σύνδεση με χρήση του πρωτοκόλλου RMI");
    //  Game initialization through RMI Interface
    String result = gameOperations.initGame();
    System.out.print(result);

    Scanner scanner = new Scanner(System.in);
    //  Game session
    while (!result.equals("GAME OVER")) {
      System.out.print("\nΕπιλογή: ");
      //  Getting the game result by sending players choice through RMI Interface
      result = gameOperations.playGame(scanner.nextLine());
      System.out.print(result);
    }
  }
}
