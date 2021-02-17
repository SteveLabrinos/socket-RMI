package exercise4;

import java.rmi.Remote;
import java.rmi.RemoteException;

/** @author Steve Labrinos [stalab at linuxmail.org] on 16/2/21 */

public interface RPSGameOperations extends Remote {

  /** @return starting game's message */
  String initGame() throws RemoteException;

  /** @return game's outcome (win, loose, tie) message */
  String playGame(String choice) throws RemoteException;
}
