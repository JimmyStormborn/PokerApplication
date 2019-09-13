package pokerapp;

import java.util.*;

/**
 * Hand values: high-card=0, pair=1, two-pair=2, three-of-a-kind=3,
 * four-of-a-kind=4
 *
 * @author TheBeast
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
    
    private int[][] cards = new int[2][2];
    private int chips;
    public int playerNum;
    public int[] hand = new int[3];
    
    public Player(int playerNum, int chips) {
        this.playerNum = playerNum;
        this.chips = chips;
    }
    
    public Player(int playerNum) {
        this(playerNum, 2000);
    }
    
    public int[][] getCards() {
        return this.cards;
    }
    
    public void setCards(int[] card1, int[] card2) {
        setCard1(card1);
        setCard2(card2);
    }
    
    public void setCard1(int[] card1) {
        this.cards[0] = card1;
    }
    
    public void setCard2(int[] card2) {
        this.cards[1] = card2;
    }
    
    /*
    Check for a flush
    */
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
    
    
    /*
    Checks if the player has any matches.
    */
    public ArrayList<ArrayList<int[]>> checkMatches(int[][] pot) {
        ArrayList<ArrayList<int[]>> matches = new ArrayList<>();
        ArrayList<int[]> m = new ArrayList<>();
        
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
    
    
    
    /*
    Checks if the player has any pairs.
    */
    public int[] checkPair(ArrayList<ArrayList<int[]>> matches) {
        int[] pairs = new int[3];
        
        for (int i = 0; i < matches.size(); i += 1) {
            if (matches.get(i).size() == 4) {
                pairs[0] = 7;
                pairs[1] = matches.get(i).get(0)[1];
                return pairs;
            } else if (matches.get(i).size() == 3) {
                pairs[0] = 3;
                for (int j = 0; j < matches.size(); j += 1) {
                    if (matches.get(j).size() == 3) {
                        if (pairs[1] < matches.get(j).get(0)[1]) {
                            pairs[1] = matches.get(j).get(0)[1];
                        }
                    }
                }
                return pairs;
            } else if (matches.get(i).size() == 2) {
                for (int j = 0; j < matches.size(); j += 1) {
                    if (pairs[0] == 0) {
                        pairs[0] = 1;
                    } else {
                        pairs[0] = 2;
                    }
                    if (matches.get(j).size() == 2) {
                        if (pairs[1] < matches.get(j).get(0)[1]) {
                            if (pairs[2] < pairs[1]) {
                                pairs[2] = pairs[1];
                            }
                            pairs[1] = matches.get(j).get(0)[1];
                        } else if (pairs[0] == 2 && pairs[2] < matches.get(j).get(0)[1]) {
                            pairs[2] = matches.get(j).get(0)[1];
                        }
                    }
                }
                return pairs;
            }
        }
        
        return pairs;
    }
    
    /*
    Finds what hand the player has.
    
    parameters:
        - pot, the cards in the pot.
    */
    public void getHand(int[][] pot) {
        int f = checkFlush(pot);
        
        if (f > 0) {
            
            hand[0] = flush;
            hand[1] = f;
            
        } else {
        
            ArrayList<ArrayList<int[]>> matches = checkMatches(pot);

            if (matches.size() > 0) {
                int[] m = checkPair(matches);
                  hand = m;
            } else {
                hand[0] = high;
                if (cards[0][1] > cards[1][1]) {
                    hand[1] = cards[0][1];
                } else {
                    hand[1] = cards[1][1];
                }
            }
        }
    }
}
