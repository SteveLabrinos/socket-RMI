package exercise2;

import exercise1.RPSGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/** @author Steve Labrinos [stalab at linuxmail.org] on 16/2/21 */

public class MultiServerThread extends Thread {
  Socket clientSocket;

  public MultiServerThread(Socket socket) {
    this.clientSocket = socket;
  }

  @Override
  public void run() {
    //  Thread has received client socket and the logic identical
    //  to the execute method of SingleServer class
    PrintWriter out = null;
    BufferedReader in = null;

    try {
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    } catch (IOException e) {
      System.out.println("Αδυναμία δημιουργίας ροών εισόδου/εξόδου με τον client");
      e.printStackTrace();
    }

    String inputLine, outputLine;

    //  RPSGame initialization
    RPSGame game = new RPSGame();
    outputLine = game.play(null);
    assert out != null;
    out.println(outputLine);
    //  RPSGame session
    try {
      while (true) {
        assert in != null;
        if ((inputLine = in.readLine()) == null) break;
        outputLine = game.play(inputLine);
        out.println(outputLine);
        if (outputLine.equals("GAME OVER")) break;
      }
    } catch (IOException e) {
      System.out.println("Σφάλμα ειδόδου/εξόδου δεδομένων ροής");
      e.printStackTrace();
    }
  }
}
