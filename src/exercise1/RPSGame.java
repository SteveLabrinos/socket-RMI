package exercise1;

import java.util.Random;

/** @author Steve Labrinos [stalab at linuxmail.org] on 15/2/21 */

public class RPSGame {
  private static final int GAME_CHOICES = 3;
  private final Random random = new Random();
  Player human = new Player();
  Player bot = new Player();

  public String play(String choice) {
    String initMessage = "Έναρξη Παιχνιδιού 'Πέτρα - Ψαλίδι - Χαρτί'\n";
    String menu = "Επιλέξτε:\t\t1-Πέτρα\t\t2-Ψαλίδι\t\t3-Χαρτί";

    //  validating user input before stating a game session
    if (choice == null) {
      return initMessage + menu;
    } else if (choice.equalsIgnoreCase("exit")) {
      return "GAME OVER";
    } else {
      try {
        int pChoice = Integer.parseInt(choice);
        if (pChoice < 1 || pChoice > 3) {
          return "Εισάγετε έγκυρες τιμές. Επαναλάβετε.\t" + menu;
        }
      } catch (NumberFormatException e) {
        return "Λάθος είσοδος. Προσπαθήστε ξανά.\t" + menu;
      }
    }

    //  getting players and bot choices
    String humanResult = human.playersChoice(Integer.parseInt(choice) - 1);
    String botResult = bot.playersChoice(random.nextInt(GAME_CHOICES));
    String result = "";

    //  passing result through the socket depending on the game's output
    result += humanResult + " vs " + botResult;
    if (humanResult.equals(botResult)) {
      result += "\tΙΣΟΠΑΛΙΑ";
    } else if (("Πέτρα".equals(humanResult) && "Ψαλίδι".equals(botResult))
        || ("Ψαλίδι".equals(humanResult) && "Χαρτί".equals(botResult))
        || ("Χαρτί".equals(humanResult) && "Πέτρα".equals(botResult))) {
      result += "\tΝΙΚΗΣΑΤΕ!!!";
    } else {
      result += "\tΧΑΣΑΤΕ :(";
    }

    result += "\tΓια τερματισμό πληκτρολογήστε: exit";
    return result;
  }
}
