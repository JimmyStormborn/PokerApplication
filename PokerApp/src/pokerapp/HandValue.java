package pokerapp;

import java.util.ArrayList;

/**
 * Hand values: high-card=0, pair=1, two-pair=2, three-of-a-kind=3,
 * straight=4, flush=5, full-house=6, four-of-a-kind=7, straight-flush=8,
 * royal-flush=9
 *
 * @author James Bird-Sycamore
 * Last Updated 11/04/2020
 */
public class HandValue {
    
    // Placeholders
    
    private final int pair = 1;
    private final int two_pair = 2;
    private final int three_of_a_kind = 3;
    private final int straight = 4;
    private final int flush = 5;
    private final int full_house = 6;
    private final int four_of_a_kind = 7;
    private final int straight_flush = 8;
    private final int royal_flush = 9;
    
    // Global Variables
    private int[] hand_value = new int[7];
    private Card[] hand_cards = new Card[5];
    
    /**
     * Constructor: Creates the HandValue object.
     */
    public HandValue() {}
    
    /**
     * Retrieves the cards in the player's hand
     * 
     * @return The player's hand
     */
    public Card[] getHandCards() {
        return this.hand_cards;
    }
    
    /**
     * Retrieves the value of the player's hand
     * 
     * @return The player's hand value.
     */
    public int[] getHandValue() {
        return this.hand_value;
    }
    
    /**
     * Finds the value of each combination and saves the 
     * combination with the highest hand value.
     * 
     * @param combinations All the possible combinations of cards the player can have.
     */
    public void findHandValue(ArrayList<Card[]> combinations) {
        int[] combo_value;
        
        for (Card[] combination : combinations) {
            int[] f = checkFlush(combination); // Check if it is a flush
            int[] s = checkStraight(combination); // Check if it is a straight
            // Check first if it is a royal flush, or a straight flush
            // If it isn't, then check for the other 
            if (f[0] > 0 && s[0] > 0) {
                if (s[1] == 14 && f[1] == 14) {
                    combo_value = s;
                    combo_value[0] = royal_flush;
                } else {
                    combo_value = s;
                    combo_value[0] = straight_flush;
                }
            } else {
                int[] m = checkMatches(combination);
                if (f[0] > m[0]) {
                    combo_value = f;
                } else if (s[0] > m[0]) {
                    combo_value = s;
                } else {
                    combo_value = m;
                }
            }
            
            
            // Checks if the combination is the best combination the player has.
            for (int val = 0; val < hand_value.length; val++) {
                if (combo_value[val] > hand_value[val]) {
                    hand_value = combo_value;
                    hand_cards = combination;
                } else if (combo_value[val] != hand_value[val]) {
                    break;
                }
            }
        }
    }
    
    /**
     * Checks the combination of cards for a pair, two pair, three of a kind,
     * full house, and a four of a kind. It first checks how many matches the
     * combination has and then calculates the hand value.
     * 
     * @param combination The combination of cards being checked.
     * @return The hand value of the combination of cards.
     */
    private int[] checkMatches(Card[] combination) {
        int[] combo_value = new int[7];
        int[] kicker;
        
        int matches;
        int n, j;
        OUTER:
        for (int i = 0; i < combination.length; i++) {
            matches = 0;
            n = 0;
            j = 1 + i;
            while (n < 4) {
                // If true, wrap around to the start.
                if (j >= combination.length) {
                    j -= combination.length;
                }
                // If true, we have a match.
                if (combination[i].value == combination[j].value) {
                    if (matches == 0) {
                        matches = 2;
                    } else {
                        matches +=1 ;
                    }
                }
                
                j++;
                n++;
            }
            switch (matches) {
                case 4:
                    combo_value[0] = four_of_a_kind;
                    combo_value[1] = combination[i].value;
                    kicker = findKicker(combination, combination[i].value, 0, 1);
                    combo_value[3] = kicker[0];
                    break OUTER;
                case 3:
                    if (combo_value[0] == pair) {
                        combo_value[0] = full_house;
                        combo_value[2] = combo_value[1];
                        combo_value[1] = combination[i].value;
                        break OUTER;
                    } else {
                        combo_value[0] = three_of_a_kind;
                        combo_value[1] = combination[i].value;
                        kicker = findKicker(combination, combination[i].value, 0, 2);
                        combo_value[3] = kicker[0];
                        combo_value[4] = kicker[1];
                    }
                    break;
                case 2:
                    if (combo_value[0] == three_of_a_kind) {
                        combo_value[0] = full_house;
                        combo_value[2] = combination[i].value;
                        break OUTER;
                    } else if (combo_value[0] == pair) {
                        if (combo_value[1] != combination[i].value) {
                            combo_value[0] = two_pair;
                            if (combination[i].value > combo_value[1]) {
                                combo_value[2] = combo_value[1];
                                combo_value[1] = combination[i].value;
                            } else {
                                combo_value[2] = combination[i].value;
                            }   kicker = findKicker(combination, combo_value[1], combo_value[2], 1);
                            combo_value[3] = kicker[0];
                            break OUTER;
                        }
                    } else {
                        combo_value[0] = pair;
                        combo_value[1] = combination[i].value;
                        kicker = findKicker(combination, combination[i].value, 0, 3);
                        combo_value[3] = kicker[0];
                        combo_value[4] = kicker[1];
                        combo_value[5] = kicker[2];
                    }
                    break;
                default:
                    if (combo_value[0] == 0 && combo_value[1] < combination[i].value) {
                        combo_value[1] = combination[i].value;
                        kicker = findKicker(combination, combination[i].value, 0, 4);
                        combo_value[3] = kicker[0];
                        combo_value[4] = kicker[1];
                        combo_value[5] = kicker[2];
                        combo_value[6] = kicker[3];
                    }   break;
            }
        }
        
        return combo_value;
    }
    
    /**
     * Finds all the kickers in the players hand.
     * 
     * @param combination The cards being checked for kickers
     * @param match_value1 The value of the card that has matches, so not a kicker.
     * @param match_value2 Same as match_value1, but only used for a two pair.
     * @param num The number of kickers it needs to find.
     * @return Returns the value of the kickers.
     */
    private int[] findKicker(Card[] combination, int match_value1, int match_value2, int num) {
        int[] kicker = new int[4];
        int n = 0;
        while (n < num) {
            for (int i = 0; i < combination.length; i++) {
                if (combination[i].value != match_value1 && combination[i].value != match_value2) {
                    kicker[n] = combination[i].value;
                    n++;
                }
            }
            break;
        }
        return kicker;
    }
    
    /**
     * Checks the combination for a straight.
     * For a combination to be a straight, each card must be the next
     * card in order ie K Q J 10 9 or 8 7 6 5 4
     * 
     * @param combination The combination of cards being checked.
     * @return The hand value of the combination. Empty if it isn't a straight.
     */
    private int[] checkStraight(Card[] combination) {
        int[] combo_value = new int[7];
        boolean s = true; // True if the combination is a straight.
        // Checks that the next card is the next card in order.
        for (int i = 0; i < combination.length-1; i++) {
            if (combination[i].value != combination[i+1].value+1) {
                s = false;
                break;
            }
        }
        
        if (s) {
            combo_value[0] = straight;
            combo_value[1] = combination[0].value;
            combo_value[3] = combination[1].value;
            combo_value[4] = combination[2].value;
            combo_value[5] = combination[3].value;
            combo_value[6] = combination[4].value;
        }
        
        return combo_value;
    }
    
    /**
     * Checks the if the combination is a flush.
     * All the cards must have the same suit.
     * 
     * @param combination The card combinations being checked
     * @return The hand value of the combination. Empty if it isn't a flush.
     */
    private int[] checkFlush(Card[] combination) {
        int[] combo_value = new int[7];
        boolean f = true; // True if the combination is a flush
        // Checks that all the cards are the same suit
        for (int i = 0; i < combination.length-1; i++) {
            if (combination[i].suit != combination[i+1].suit) {
                f = false;
                break;
            }
        }
        // It is a flush so set combo as a flush.
        if (f) {
            combo_value[0] = flush;
            combo_value[1] = combination[0].value;
        }
        
        return combo_value;
    }
}
