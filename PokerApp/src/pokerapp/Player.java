package pokerapp;

import java.util.*;

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
    
    private Card[] cards = new Card[2];
    private int chips;
    public int playerNum;
    public Card[] hand = new Card[5];
    public int hand_value;
    
    public Player(int playerNum, int chips) {
        this.playerNum = playerNum;
        this.chips = chips;
    }
    
    public Player(int playerNum) {
        this(playerNum, 2000);
    }
    
    public Card[] getCards() {
        return this.cards;
    }
    
    public Card getCard1() {
        return this.cards[0];
    }
    
    public Card getCard2() {
        return this.cards[1];
    }
    
    public void setCards(Card card1, Card card2) {
        setCard1(card1);
        setCard2(card2);
    }
    
    public void setCard1(Card card1) {
        this.cards[0] = card1;
    }
    
    public void setCard2(Card card2) {
        this.cards[1] = card2;
    }
}
