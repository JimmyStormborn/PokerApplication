package pokerapp;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @desc The Player class. It defines the player, stores their cards and 
 * chips, and is able to find what the players hand value is.
 *
 * @author James Bird-Sycamore
 * @date 27/03/2020
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
    
    final private Card[] player_cards = new Card[2]; // The players cards
    public Card[][] combinations = new Card[31][5]; // All possible combinations of cards for the player's hand
    private int chips; // The amount of chips the player has
    final public int playerNum; // The player's number
    public Card[] hand_cards = new Card[5]; // The best possible combination of cards the player has
    public int hand_value; // The value of the player's hand
    
    /**
     * Default Constructor: Creates the player object.
     * 
     * @param playerNum The number identifying the player.
     * @param chips The amount of chips the player starts with.
     */
    public Player(int playerNum, int chips) {
        this.playerNum = playerNum;
        this.chips = chips;
    }
    
    /**
     * Constructor: Creates the player object.
     * Uses 2000 chips for the default number of chips.
     * 
     * @param playerNum The number identifying the player.
     */
    public Player(int playerNum) {
        this(playerNum, 2000);
    }
    
    /**
     * Retrieves the players cards.
     * 
     * @return Both cards the player has. 
     */
    public Card[] getCards() {
        return this.player_cards;
    }
    
    /**
     * Retrieves one of the players cards.
     * 
     * @return The player card at position 0. 
     */
    public Card getCard1() {
        return this.player_cards[0];
    }
    
    /**
     * Retrieves one of the players cards.
     * 
     * @return The player card at position 1. 
     */
    public Card getCard2() {
        return this.player_cards[1];
    }
    
    /**
     * Changes the player's cards.
     * 
     * @param card1 The new value of the first card.
     * @param card2 The new value of the second card.
     */
    public void setCards(Card card1, Card card2) {
        setCard1(card1);
        setCard2(card2);
    }
    
    /**
     * Changes one of the player's cards.
     * 
     * @param card1 The new value of the first card.
     */
    public void setCard1(Card card1) {
        this.player_cards[0] = card1;
    }
    
    /**
     * Changes one of the player's cards
     * 
     * @param card2 The new value of the second card. 
     */
    public void setCard2(Card card2) {
        this.player_cards[1] = card2;
    }
    
    /**
     * Finds all the card combinations the player can have.
     * 
     * @param pot The cards in the middle
     */
    public void getCombinations(Card[] pot) {
//        Parser parser = new Parser();
        
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
        Scanner scan;
        try {
            scan = new Scanner(file);
            
            int[] order; // The order of cards in integer format
            int c = 0; // The position of combinations
            // Reads each possible or of combinations and adds the combo of cards
            // to the array of combinations.
            while (scan.hasNextLine()) {
                order = new int[5]; // Can only be 5 long
                String[] str_order = scan.nextLine().split(", ");
                // Converts the string into integers
                for (int i = 0; i < str_order.length; i++) {
                    order[i] = Integer.parseInt(str_order[i]);
                }
                
                // Creates all combinations of cards in card format.
                n = 0; // The position in the cards, 0 - 4.
                for (int o : order) {
                    if (cards[o-1] != null) {
                        this.combinations[c][n] = cards[o-1]; // Minus 1 from order because array starts from 0 not 1.
                        n++;
                    }
                }
                c++;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
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
    
    /**
     * Finds the value of the player's hand and the corresponding cards.
     */
    public void findHandValue() {
        HandValue hand_finder = new HandValue();
        hand_finder.findHandValue(combinations);
        hand_value = hand_finder.getHandValue();
        hand_cards = hand_finder.getHandCards();
    }
}
