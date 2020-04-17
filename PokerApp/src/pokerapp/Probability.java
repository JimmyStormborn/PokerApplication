package pokerapp;

import java.io.FileNotFoundException;
import java.io.File; 
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to find the probability of each hand has to win
 * a round of poker.
 *
 * @author James Bird-Sycamore
 * Last Updated 15/04/2020
 */
public class Probability {
    
    public Probability () {
        
    }
    
    public void run () throws FileNotFoundException {
        // Writes all the hand combinations to text file
//        try {
//            allHands();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(Probability.class.getName()).log(Level.SEVERE, null, ex);
//        }

        Parser parser = new Parser();
        DecimalFormat df = new DecimalFormat("##.##");

        File handsFile = new File("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\all_hands.txt");
        PrintWriter handsPW = new PrintWriter(handsFile);
        
        File probFile = new File("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\probabilities.txt");
        PrintWriter probPW = new PrintWriter(probFile);

        Player[] players = new Player[3];
        AI simpleAI = new AI(0);
        int n = 1;
        for (int p = 0; p < players.length; p++) {
            players[p] = new Player(n, 2000, simpleAI);
            n++;
        }
        
        ArrayList<Card[]> all_hands = allHands();
        
        boolean wins;
        double num_of_wins;
        for (int h = 0; h < all_hands.size(); h++) {
            num_of_wins = 0;
            
            if (all_hands.get(h)[0].suit == 0) {
                String hand = parser.cardsToString(all_hands.get(h)) + "\n";
                handsPW.write(hand);
            
                int N = 1000;
                n = 0;
                while (n < N) {
                    Round round = new Round(players);
                    wins = round.getProb(all_hands.get(h));
                    if (wins) {
                        num_of_wins++;
                    }
                    n++;
                }
                double prob = (num_of_wins / N);
                prob *= 100;

                probPW.write(df.format(prob) + "\n");

                double done = (double) (h+1) / all_hands.size();
                done *= 100;

                System.out.print("Finished " + df.format(done));
                System.out.print("%\n");
                
            }
            
//            System.out.println(parser.cardsToString(all_hands.get(h)) + " probability to win = " + prob + "%");
        }
        
        probPW.flush();
        probPW.close();
        handsPW.flush();
        handsPW.close();
    }
    
    private ArrayList<Card[]> allHands () {
        ArrayList<Card[]> all_hands = new ArrayList();
        
        Card[] cards = new Card[52];
        int n = 0;
        
        for (int val = 2; val <= 14; val++) {
            for (int suit = 0; suit < 4; suit++) {
                cards[n] = new Card(suit, val);
                n++;
            }
        }
        
        Card[] hand;
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
    
    /**
    private void allHands () throws FileNotFoundException {
        File file = new File("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\all_hands.txt");
        
        String[] cards = new String[52];
        int n = 0;
        
        for (int val = 2; val <= 14; val++) {
            for (int suit = 0; suit < 4; suit++) {
                cards[n] = Integer.toString(val);
                switch (suit) {
                    case 0:
                        cards[n] += "S";
                        break;
                    case 1:
                        cards[n] += "C";
                        break;
                    case 2:
                        cards[n] += "D";
                        break;
                    case 3:
                        cards[n] += "H";
                        break;
                }
                n++;
            }
        }
        
        ArrayList<String> allhands = new ArrayList();
        String hand;
        for (int i = 0; i < cards.length-1; i++) {
            int j = i+1;
            while (j < cards.length) {
                hand = cards[i] + ", ";
                hand += cards[j] + "\n";
                allhands.add(hand);
                j++;
            }
        }
        
        PrintWriter pw = new PrintWriter(file);
        for (String h : allhands) {
            pw.write(h);
        }
        
        pw.flush();
        pw.close();
    }
    */
}
