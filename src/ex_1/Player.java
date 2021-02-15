package ex_1;

public class Player {
    String[] CHOICES = new String[] {"Πέτρα", "Ψαλίδι", "Χαρτί"};


    public String playersChoice(int choice) {
        return CHOICES[choice];
    }
}
