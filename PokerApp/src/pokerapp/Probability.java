package pokerapp;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class to find the probability of each hand has to win
 * a round of poker.
 *
 * @author James Bird-Sycamore
 * Last Updated 18/04/2020
 */
public class Probability {
    
    /**
     * Constructor: Creates the probability object
     */
    public Probability () {}
    
    /**
     * Runs the program to find the probabilities of all the hands to win.
     * 
     * @throws FileNotFoundException If the files do not exist.
     */
    public void run () throws FileNotFoundException {

        Parser parser = new Parser(); // Writes the cards to a string
        DecimalFormat df = new DecimalFormat("##.##"); // The format for the doubles

        File handsFile = new File("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\all_hands.txt");
        PrintWriter handsPW = new PrintWriter(handsFile);
        
        File probFile = new File("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\probabilities.txt");
        PrintWriter probPW = new PrintWriter(probFile);

        // Create the player in the round, all are computer players
        Player[] players = new Player[3];
        AI simpleAI = new AI(0);
        int n = 1;
        for (int p = 0; p < players.length; p++) {
            players[p] = new Player(n, 2000, simpleAI);
            n++;
        }
        
        ArrayList<Card[]> all_hands = allHands(); // Get all the possible poker hands
        
        boolean wins; // True if the hand wins
        double num_of_wins; // The number of times a hand wins
        // Finds out the probability of all the hands
        for (int h = 0; h < all_hands.size(); h++) {
            num_of_wins = 0;
            // Removes any double hands
            if (all_hands.get(h)[0].suit == 0) {
                String hand = parser.cardsToString(all_hands.get(h)) + "\n";
                handsPW.write(hand); // Writes the hands to file
            
                int N = 1000; // The number of rounds each hand does
                n = 0; // The current round
                while (n < N) {
                    Round round = new Round(players); // New round each time
                    wins = round.getProb(all_hands.get(h)); // Runs the get probability round
                    if (wins) {
                        num_of_wins++;
                    }
                    n++;
                }
                double prob = (num_of_wins / N); // The probabilty a hand has to win
                prob *= 100; 

                probPW.write(df.format(prob) + "\n");

                double done = (double) (h+1) / all_hands.size(); // The percent of hands checked
                done *= 100;

                System.out.print("Finished " + df.format(done));
                System.out.print("%\n");
                
            }
        }
        
        probPW.flush();
        probPW.close();
        
        handsPW.flush();
        handsPW.close();
    }
    
    /**
     * Creates a list of all the possible hands.
     * 
     * @return The list of hands
     */
    private ArrayList<Card[]> allHands () {
        ArrayList<Card[]> all_hands = new ArrayList();
        
        Card[] cards = new Card[52];
        int n = 0;
        // Creates a deck
        for (int val = 2; val <= 14; val++) {
            for (int suit = 0; suit < 4; suit++) {
                cards[n] = new Card(suit, val);
                n++;
            }
        }
        
        Card[] hand;
        // Finds all the possible hands
        for (int i = 0; i < cards.length-1; i++) {
            int j = i+1;
            while (j < cards.length) {
                hand = new Card[2];
                hand[0] = cards[i];
                hand[1] = cards[j];
                all_hands.add(hand);
                j++;
            }
        }
        
        return all_hands;
    }
}
