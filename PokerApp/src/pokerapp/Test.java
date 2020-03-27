package pokerapp;

import java.util.ArrayList;

/**
 *
 * @author James Bird-Sycamore
 * @date 22/03/2020
 */
public class Test {
    
    private ArrayList<String> errors = new ArrayList<String>();
    
    public Test() {}
    
    public void printErrors() {
        if (errors.size() > 0) {
            System.err.println("List of errors:");
            for (String error : errors) {
                System.err.println(error);
            }
        } else {
            System.err.println("Congrats! There are no errors.");
        }
    }
    
    public void checkForDoubles(Player[] players, Card[] pot) {
        Card[] cards = new Card[players.length*2 + pot.length];
        int n = 0;
        for (Player player : players) {
            cards[n] = player.getCard1();
            n++;
            cards[n] = player.getCard2();
            n++;
        }
        int p = 0;
        while (p < 5) {
            cards[n] = pot[p];
            n++;
            p++;
        }
        
        String error = "There are two of the same card: ";
        
        for (int i = 0; i < cards.length; i++) {
            for (int j = i+1; j < cards.length; j++) {
                if (cards[i].value == cards[j].value && cards[i].suit == cards[j].suit) {
                    errors.add(error+cards[i].value + "" + cards[i].suit);
                }
            }
        }
    }
    
}
