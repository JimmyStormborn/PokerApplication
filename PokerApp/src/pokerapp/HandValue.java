package pokerapp;

import java.util.ArrayList;

/**
 * Hand values: high-card=0, pair=1, two-pair=2, three-of-a-kind=3,
 * straight=4, flush=5, full-house=6, four-of-a-kind=7, straight-flush=8,
 * royal-flush=9
 *
 * @author James Bird-Sycamore
 * @date 28/03/2020
 */
public class HandValue {
    
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
            combo_value = checkMatches(combination);
            if (combo_value[0] > hand_value[0] || 
                    ((combo_value[1] > hand_value[1] || combo_value[2] > hand_value[2]
                    || combo_value[3] > hand_value[3] || combo_value[4] > hand_value[4]
                    || combo_value[5] > hand_value[5] || combo_value[6] > hand_value[6]) 
                    && combo_value[0] == hand_value[0])) {
                hand_value = combo_value;
                hand_cards = combination;
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
        int[] hand_value = new int[7];
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
                    hand_value[0] = four_of_a_kind;
                    hand_value[1] = combination[i].value;
                    kicker = findKicker(combination, combination[i].value, 0, 1);
                    hand_value[3] = kicker[0];
                    break OUTER;
                case 3:
                    if (hand_value[0] == pair) {
                        hand_value[0] = full_house;
                        hand_value[2] = hand_value[1];
                        hand_value[1] = combination[i].value;
                        break OUTER;
                    } else {
                        hand_value[0] = three_of_a_kind;
                        hand_value[1] = combination[i].value;
                        kicker = findKicker(combination, combination[i].value, 0, 2);
                        hand_value[3] = kicker[0];
                        hand_value[4] = kicker[1];
                    }
                    break;
                case 2:
                    if (hand_value[0] == three_of_a_kind) {
                        hand_value[0] = full_house;
                        hand_value[2] = combination[i].value;
                        break OUTER;
                    } else if (hand_value[0] == pair) {
                        if (hand_value[1] != combination[i].value) {
                            hand_value[0] = two_pair;
                            if (combination[i].value > hand_value[1]) {
                                hand_value[2] = hand_value[1];
                                hand_value[1] = combination[i].value;
                            } else {
                                hand_value[2] = combination[i].value;
                            }   kicker = findKicker(combination, hand_value[1], hand_value[2], 1);
                            hand_value[3] = kicker[0];
                            break OUTER;
                        }
                    } else {
                        hand_value[0] = pair;
                        hand_value[1] = combination[i].value;
                        kicker = findKicker(combination, combination[i].value, 0, 3);
                        hand_value[3] = kicker[0];
                        hand_value[4] = kicker[1];
                        hand_value[5] = kicker[2];
                    }
                    break;
                default:
                    if (hand_value[0] == 0 && hand_value[1] < combination[i].value) {
                        hand_value[1] = combination[i].value;
                        kicker = findKicker(combination, combination[i].value, 0, 4);
                        hand_value[3] = kicker[0];
                        hand_value[4] = kicker[1];
                        hand_value[5] = kicker[2];
                        hand_value[6] = kicker[3];
                    }   break;
            }
        }
        
        return hand_value;
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
    
     /*
    Check for a flush
    private int checkFlush(int[][] pot) {
        int matching = 1;
        int f = 0;
        
        for (int i = 0; i < cards.length; i += 1) {
            if (cards[i][0] == cards[cards.length-1-i][0]) {
                matching += 1;
            }
            for (int j = 0; j < pot.length; j += 1) {
                if (pot[j][1] != 0) {
                    if (cards[i][0] == pot[j][0]) {
                        matching += 1;
                    }
                    if (matching == 5) {
                        int highest = cards[i][1];
                        if (i > 0) {
                            if (cards[i][0] == cards[i-1][0]) {
                                if (highest < cards[i-1][1]) {
                                    highest = cards[i-1][1];
                                }
                            }
                        } else {
                            if (cards[i][0] == cards[i+1][0]) {
                                if (highest < cards[i+1][1]) {
                                    highest = cards[i+1][1];
                                }
                            }
                        }
                        for (int k = 0; k < pot.length; k += 1) {
                            if (pot[k][0] == cards[i][0]) {
                                if (highest < pot[k][1]) {
                                    highest = pot[k][1];
                                }
                            }
                        }
                        return highest;
                    }
                }
            }
            matching = 1;
        }
        
        return f;
    }
    
    */
    
    
    /*
    Checks if the player has any matches.
    public ArrayList<ArrayList<int[]>> checkMatches(int[][] pot) {
        ArrayList<ArrayList<int[]>> matches = new ArrayList<>();
        ArrayList<int[]> m;
        
        int i, j;
        for (i = 0; i < pot.length; i += 1) {
            if (pot[i][1] != 0) {
                j = i;
                m = new ArrayList<>();
                while (j < pot.length) {
                    if (j != i) {
                        if (pot[i][1] == pot[j][1]) {
                            m.add(pot[i]);
                            m.add(pot[j]);
                            if (!matches.contains(m)) {
                                matches.add(m);
                            }
                        }
                    }
                    j += 1;
                }

                // Matching with the first card
                if (cards[0][1] == pot[i][1]) {
                    m.add(cards[0]);
                    
                    // Matching with the second card
                    if (cards[1][1] == pot[i][1]) {
                        m.add(cards[1]);
                    }
                    
                    if (!m.contains(pot[i])) {
                        m.add(pot[i]);
                    }
                    
                    if (!matches.contains(m)) {
                        matches.add(m);
                    }
                
                } else if (cards[1][1] == pot[i][1]) {
                    m.add(cards[1]);
                    
                    if (!m.contains(pot[i])) {
                        m.add(pot[i]);
                    }
                    
                    if (!matches.contains(m)) {
                        matches.add(m);
                    }
                }
            }
            
            m = new ArrayList<>();
            if (cards[1][1] == cards[0][1]) {
                m.add(cards[1]);
                m.add(cards[0]);
                if (!matches.contains(m)) {
                    matches.add(m);
                }
            }
        }
        
        return matches;
    }
    
    */
    
    
    /*
    Checks if the player has any pairs.
    public int[] checkPair(ArrayList<ArrayList<int[]>> matches) {
        int[] pairs = new int[3];
        
        for (int i = 0; i < matches.size(); i += 1) {
            switch (matches.get(i).size()) {
                case 4:
                    pairs[0] = 7;
                    pairs[1] = matches.get(i).get(0)[1];
                    break;
                case 3:
                    if (pairs[0] < 3) {
                        if (pairs[0] <= 2) {
                            // full house
                            pairs[0] = 6;
                            pairs[2] = pairs[1];
                            pairs[1] = pairs[1] = matches.get(i).get(0)[1];
                        } else {
                            pairs[0] = 3;
                            if (pairs[1] < matches.get(i).get(0)[1]) {
                                pairs[1] = matches.get(i).get(0)[1];
                            }
                        }
                    } else if (pairs[0] == 6) {
                        if (pairs[1] < matches.get(i).get(0)[1]) {
                            pairs[1] = matches.get(i).get(0)[1];
                        }
                    }
                    break;
                case 2:
                    if (pairs[0] == 0) {
                        pairs[0] = 1;
                        pairs[1] = matches.get(i).get(0)[1];
                    } else if (pairs[0] == 1) {
                        pairs[0] = 2;
                        if (pairs[1] < matches.get(i).get(0)[1]) {
                            pairs[2] = pairs[1];
                            pairs[1] = matches.get(i).get(0)[1];
                        } else if (pairs[2] < matches.get(i).get(0)[1]) {
                            pairs[2] = matches.get(i).get(0)[1];
                        }
                    } else if (pairs[0] == 3) {
                        // Full house
                        pairs[0] = 6;
                        if (pairs[2] > matches.get(i).get(0)[1]) {
                            pairs[2] = matches.get(i).get(0)[1];
                        }
                    } else if (pairs[0] == 6) {
                        if (pairs[2] > matches.get(i).get(0)[1]) {
                            pairs[2] = matches.get(i).get(0)[1];
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        
        return pairs;
    }
    
    */
    
    /*
    private ArrayList<ArrayList<int[]>> addToStraights(ArrayList<ArrayList<int[]>> straights, int[] card1, int[] card2) {
        ArrayList<int[]> straight;
        boolean flag = true;
        
        if (!straights.isEmpty()) {
            for (ArrayList<int[]> s : straights) {
                int n = 0;
                while (n < s.size()) {
                    flag = true;
                    if (card1[1] == s.get(n)[1] + 1 || card1[1] == s.get(n)[1] - 1) {
                        for (int[] c : s) {
                            if (c[1] == card1[1]) {
                                flag = false;
                            }
                        }
                        if(flag) {
                            s.add(card1);
                        }
                    }
                    
                    flag = true;
                    if (card2[1] == s.get(n)[1] + 1 || card2[1] == s.get(n)[1] - 1) {
                        for (int[] c : s) {
                            if (c[1] == card2[1]) {
                                flag = false;
                            }
                        }
                        if(flag) {
                            s.add(card2);
                        }
                    }
                    n += 1;
                }
            }
        } else {
            straight = new ArrayList<>();
            straight.add(card1);
            straight.add(card2);
            straights.add(straight);
        }
        
        return straights;
    }
    */
    
    /*
    private int checkStraight(int[][] pot) {
        int value = 0;
        ArrayList<ArrayList<int[]>> straights = new ArrayList<>();
        ArrayList<int[]> s = new ArrayList<>();
        
        if (cards[0][1] == cards[1][1] + 1 || cards[0][1] == cards[1][1] - 1) {
            s.add(cards[0]);
            s.add(cards[1]);
            straights.add(s);
        }
        
        int i, j;
        for (i = 0; i < pot.length; i += 1) {
            if (pot[i][1] != 0) {
                j = i;
                while (j < pot.length) {
                    if (j != i) {
                        if (pot[i][1] == pot[j][1] + 1 || pot[i][1] == pot[j][1] - 1) {
                            straights = addToStraights(straights, pot[i], pot[j]);
                        }
                    }
                    j += 1;
                }

                // Matching with the first card
                if (cards[0][1] == pot[i][1] + 1 || cards[0][1] == pot[i][1] - 1) {
                    straights = addToStraights(straights, cards[0], pot[i]);
                    
                    // Matching with the second card
                    if (cards[1][1] == pot[i][1] + 1 || cards[1][1] == pot[i][1] - 1) {
                        straights = addToStraights(straights, cards[1], pot[i]);
                    }
                
                } else if (cards[1][1] == pot[i][1]) {
                    straights = addToStraights(straights, cards[1], pot[i]);
                }
            }
        }
        
        for (ArrayList<int[]> straight : straights) {
            if (straight.size() >= 5) {
                int highest = 0;
                for (i = 0; i < straight.size(); i += 1) {
                    if (highest < straight.get(i)[1]) {
                        highest = straight.get(i)[1];
                    }
                }
                if (value < highest) {
                    value = highest;
                }
            }
        }
        
        return value;
    }
    */
    
    /*
    Finds what hand the player has.
    
    parameters:
        - pot, the cards in the pot.
    public void getHand(int[][] pot) {
        int s = checkStraight(pot);
        int f = checkFlush(pot);
        
        if (s > 0 && f > 0) {
            if (f == 14 && s == 14) {
//                hand_value[0] = royal_flush;
            }
//            hand_value[0] = straight_flush;
//            hand_value[1] = s;
        } else if (s > 0) {
            
//            hand_value[0] = straight;
//            hand_value[1] = s;
            
        } else if (f > 0) {

//            hand_value[0] = flush;
//            hand_value[1] = f;

        } else {

            ArrayList<ArrayList<int[]>> matches = checkMatches(pot);

            if (matches.size() > 0) {
                int[] m = checkPair(matches);
//                hand_value = m;
            } else {
//                hand_value[0] = high;
                if (cards[0][1] > cards[1][1]) {
//                    hand_value[1] = cards[0][1];
                } else {
//                    hand_value[1] = cards[1][1];
                }
            }
            
        }
    }
*/
    
}
