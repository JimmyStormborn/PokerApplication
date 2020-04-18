package pokerapp;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @desc The Player class. It defines the player, stores their cards and 
 * chips, and is able to find what the players hand value is.
 *
 * @author James Bird-Sycamore
 * Last Updated 18/04/2020
 */
public class Player {
    
    // Variables
    
    private Card[] player_cards = new Card[2];                  // The players cards
    public ArrayList<Card[]> combinations = new ArrayList<>();  // All possible combinations of cards for the player's hand
    public int chips;                           // The amount of chips the player has
    
    final public int playerNum;                 // The player's number
    public String player_name;                  // The player's name
    
    public Card[] hand_cards = new Card[5];     // The best possible combination of cards the player has
    public int[] hand_value = new int[6];       // The value of the player's hand
    
    public boolean fold = false;    // Whether the player has folded or not
    public boolean allin = false;   // Whether the player is all in or not
    
    public int current_bet = 0;
    
    public boolean computer = false; // Whether the player is a computer or not
    public AI ai;
    
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
     * Constructor: Creates the computer player object.
     * 
     * @param playerNum The number identifying the player.
     * @param chips The amount of chips the player starts with.
     * @param ai The AI type that the computer uses.
     */
    public Player(int playerNum, int chips, AI ai) {
        this.playerNum = playerNum;
        this.chips = chips;
        this.ai = ai;
        this.computer = true; // It is a computer
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
     * Constructor: Used for testing to create a pre made player
     * 
     * @param card1 The player's first card
     * @param card2 The player's second card
     * @param playerNum The number identifying the player
     */
    public Player(Card card1, Card card2, int playerNum) {
        this.player_cards[0] = card1;
        this.player_cards[1] = card2;
        this.playerNum = playerNum;
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
        this.combinations = new ArrayList<>();
        
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
                Card[] combination = new Card[5];
                for (int o : order) {
                    if (cards[o-1] != null) {
                        combination[n] = cards[o-1]; // Minus 1 from order because array starts from 0 not 1.
                        n++;
                    }
                }
                combination = insertSort(combination);
                if (!combinations.contains(combination)) {
                    combinations.add(combination);
                }
                c++;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Sorts the cards in order of highest value to lowest value.
     * 
     * @param cards The array of cards being sorted
     * @return The sorted array of cards
     */
    private Card[] insertSort(Card[] cards) {
        Card[] sorted_cards = cards;
        
        int i, j;
        Card key;
        for (i = 1; i < sorted_cards.length; i++) {
            key = sorted_cards[i];
            j = i-1;
            while (j >= 0) {
                if (key != null && sorted_cards[j] != null) {
                    if (key.value > sorted_cards[j].value) {
                        sorted_cards[j+1] = sorted_cards[j];
                        j -= 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            sorted_cards[j + 1] = key;
        }
        
        return sorted_cards;
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
