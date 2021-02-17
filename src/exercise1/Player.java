package exercise1;

/** @author Steve Labrinos [stalab at linuxmail.org] on 15/2/21 */

public class Player {
  String[] CHOICES = new String[] {"Πέτρα", "Ψαλίδι", "Χαρτί"};
  /** @return one of the three available choices for the player */
  public String playersChoice(int choice) {
    return CHOICES[choice];
  }
}
