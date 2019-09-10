package pokerapp;

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
    public int[] hand = new int[2];
    
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
    Checks if the player has a pair or two pair.
    
    parameters:
        - pot, the cards in the pot.
    returns: 
        - int, 1 for pair, 2 for two pair, 0 for no pair.
    */
    private int checkPair(int[][] pot) {
        int matches1 = 0; // matches with first card
        int matches2 = 0; // matches with second card
        
        // Checks for the pair
        for (int i = 0; i < pot.length; i += 1) {
            // Matching with the first card
            if (cards[0][1] == pot[i][1]) {
                matches1 += 1;
            }
            // Matching with the second card
            if (cards[1][1] == pot[i][1]) {
                matches2 += 1;
            }
        }
        
        if (matches1 == 2 && matches2 == 2) {           // four of a kind
            return 4;
        } else if (matches1 == 2 || matches2 == 2) {    // three of a kind
            return 3;
        } else if (matches1 == 1 && matches2 == 1) {    // two pair
            return 2;
        } else if (matches1 == 1 || matches2 == 1) {    // one pair
            return 1;
        } else if (cards[0][1] == cards[1][1]) {        // one pair
            return 1;
        } else {                                        // no pair
            return 0;
        }
    }
    
    /*
    Check for a flush
    */
    private boolean checkFlush(int[][] pot) {
        int matching = 1;
        
        for (int i = 0; i < cards.length; i += 1) {
            if (cards[i][0] == cards[cards.length-1-i][0]) {
                matching += 1;
            }
            for (int j = 0; j < pot.length; j+= 1) {
                if (cards[i][0] == pot[j][0]) {
                    matching += 1;
                }
                if (matching == 5) {
                    return true;
                }
            }
            matching = 1;
        }
        
        return false;
    }
    
    /*
    Finds what hand the player has.
    
    parameters:
        - pot, the cards in the pot.
    */
    public void getHand(int[][] pot) {
        int matchingCards = checkPair(pot);
        
        if (checkFlush(pot)) {
            
            hand[0] = flush;
            
        } else {
        
            switch (matchingCards) {
                case 1:
                    // Check for pair
                    hand[0] = pair;
                    break;
                case 2:
                    // Check for two pair
                    hand[0] = two_pair;
                    break;
                case 3:
                    hand[0] = three_of_a_kind;
                    break;
                case 4:
                    hand[0] = four_of_a_kind;
                    break;
                default:
                    hand[0] = high;
                    break;
            }
            
        }
    }
}
