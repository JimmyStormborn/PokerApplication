package pokerapp;

/**
 * The Card object represents a playing card.
 *
 * @author James Bird-Sycamore
 * Last Updated: 28/03/2020
 */
public class Card {
    
    public int suit;
    public int value;
    
    /**
     * Constructor: creates the card.
     * 
     * @param suit the card's suit
     * @param value the card's value
     */
    public Card (int suit, int value) {
        this.suit = suit;
        this.value = value;
    }
}
