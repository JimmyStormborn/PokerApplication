package pokerapp;

/**
 *
 * @author TheBeast
 */
public class Player {
    
    private int[][] cards = new int[2][2];
    private int chips;
    
    public Player(int chips) {
        this.chips = chips;
    }
    
    public Player() {
        this(2000);
    }
    
    public int[][] getCards() {
        return this.cards;
    }
    
    public void setCards(int[] card1, int[] card2) {
        this.cards[0] = card1;
        this.cards[1] = card2;
    }
    
    public void setCard1(int[] card1) {
        this.cards[0] = card1;
    }
    
    public void setCard2(int[] card2) {
        this.cards[1] = card2;
    }
}
