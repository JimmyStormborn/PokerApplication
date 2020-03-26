package pokerapp;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Hand values: high-card=0, pair=1, two-pair=2, three-of-a-kind=3,
 * straight=4, flush=5, full-house=6, four-of-a-kind=7, straight-flush=8,
 * royal-flush=9
 *
 * @author James Bird-Sycamore
 */
public class Player {
    
    // Placeholders
    
    int high = 0;
    int pair = 1;
    int two_pair = 2;
    int three_of_a_kind = 3;
    int straight = 4;
    int flush = 5;
    int full_house = 6;
    int four_of_a_kind = 7;
    int straight_flush = 8;
    int royal_flush = 9;
    
    // Variables
    
    private Card[] player_cards = new Card[2]; // The players cards
    private Card[][] combinations = new Card[31][5]; // All possible combinations of cards for the player's hand
    private int chips; // The amount of chips the player has
    public int playerNum; // The player's number
    public Card[] hand = new Card[5]; // The best possible combination of cards the player has
    public int hand_value; // The value of the player's hand
    
    public Player(int playerNum, int chips) {
        this.playerNum = playerNum;
        this.chips = chips;
    }
    
    public Player(int playerNum) {
        this(playerNum, 2000);
    }
    
    public Card[] getCards() {
        return this.player_cards;
    }
    
    public Card getCard1() {
        return this.player_cards[0];
    }
    
    public Card getCard2() {
        return this.player_cards[1];
    }
    
    public void setCards(Card card1, Card card2) {
        setCard1(card1);
        setCard2(card2);
    }
    
    public void setCard1(Card card1) {
        this.player_cards[0] = card1;
    }
    
    public void setCard2(Card card2) {
        this.player_cards[1] = card2;
    }
    
    /**
     * Finds all the card combinations the player can have.
     * 
     * @param pot The cards in the middle
     * @throws FileNotFoundException 
     */
    public void getCombinations(Card[] pot) throws FileNotFoundException {
        Parser parser = new Parser();
        
        // Get all the cards that the player can use.
        Card[] cards = new Card[7];
        int n = 0;
        while (n < 5) {
            if (pot[n] != null) {
                cards[n] = pot[n];
            } else {
                break;
            }
            n++;
        }
        cards[n] = player_cards[0];
        n++;
        cards[n] = player_cards[1];
        
        File file = new File("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\all_possible_combinations.txt");
        Scanner scan = new Scanner(file);
        int[] order;
        
        int c = 0;
        while (scan.hasNextLine()) {
            order = new int[5];
            String[] str_order = scan.nextLine().split(", ");
            for (int i = 0; i < str_order.length; i++) {
                order[i] = Integer.parseInt(str_order[i]);
            }
            n = 0;
            for (int o : order) {
                if (cards[o-1] != null) {
                    this.combinations[c][n] = cards[o-1];
                    n++;
                }
            }
            n = 0;
            c++;
        }
        
//        for (Card[] combination : combinations) {
//            for (Card card : combination) {
//                if (card != null) {
//                    System.out.print(parser.cardToString(card) + " ");
//                }
//            }
//            System.out.println();
//        }
    }
}
