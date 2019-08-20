package pokerapp;

/**
 *
 * @author TheBeast
 */
public class Player {
    
    // Placeholders
    String pair = "1p";
    String two_pair = "2p";
    String three_of_a_kind = "3p";
    String four_of_a_kind = "4p";
    
    // Variables
    
    private int[][] cards = new int[2][2];
    private int chips;
    public int playerNum;
    public String hand = "";
    
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
    public int checkPair(int[][] pot) {
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
    Finds what hand the player has.
    
    parameters:
        - pot, the cards in the pot.
    */
    public void getHand(int[][] pot) {
        int matchingCards = checkPair(pot);
        
        if (matchingCards == 1) {                      // Check for pair
            hand = pair;
        } else if (matchingCards == 2) {               // Check for two pair
            hand = two_pair;
        } else if (matchingCards == 3) {
            hand = three_of_a_kind;
        } else if (matchingCards == 4) {
            hand = four_of_a_kind;
        }
    }
}
