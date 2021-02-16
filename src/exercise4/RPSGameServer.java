package exercise4;

import exercise1.RPSGame;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Steve Labrinos [stalab at linuxmail.org] on 16/2/21
 *
 */

public class RPSGameServer extends UnicastRemoteObject implements RPSGameOperations {
    //  Protocol's port
    private static final int RMI_PORT = 10001;
    //  Using ex_1 RPSGame class to apply the same logic through RMI
    private static RPSGame game;

    //  Calling super on the constructor to extend UnicastRemoteObject
    protected RPSGameServer () throws RemoteException {
        super();
    }

    //  Init the game, sending the starting game info
    @Override
    public String initGame() {
        game = new RPSGame();
        return game.play(null);
    }

    //  One game session
    /**
     * @return  winner [bot, player] depending on user's choice and a random pick for the bot
     *
     */
    @Override
    public String playGame(String choice) {
        return game.play(choice);
    }

    public static void main(String[] args) {
        try {
            //  Defining the port that the RMI communication is being established
            Registry registry = LocateRegistry.createRegistry(RMI_PORT);
            registry.rebind("RPSGameOperations", new RPSGameServer());
            System.out.println("Το RMI πρωτόκολλο είναι έτοιμο");

        } catch (Exception e) {
            System.err.println("Σφάλμα εξυπηρετητή: " + e.toString());
            e.printStackTrace();
        }
    }
}
