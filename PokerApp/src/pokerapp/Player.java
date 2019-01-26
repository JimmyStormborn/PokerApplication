package pokerapp;

/**
 *
 * @author TheBeast
 */
public class Player {
    
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
    
    public void getHand(int[][] pot) {
        int matching1 = 0;
        int matching2 = 0;
        int matching = 0;
        
        if (cards[0][0] == cards[1][0]) {
            matching1 = 2;
            matching2 = 2;
        }
        
        for (int i = 0; i < pot.length; i++) {
            if (cards[0][0] == pot[i][0]) {
                matching1++;
            } else if (cards[1][0] == pot[i][0]) {
                matching2++;
            }
        }
        
        if (matching1 > matching2) {
            matching = matching1;
        } else {
            matching = matching2;
        }
        if (matching == 2) {
            hand = "pair";
        } else if (matching == 3) {
            hand = "three of a kind";
        } else if (matching == 4) {
            hand = "four of a kind";
        }
    }
}
