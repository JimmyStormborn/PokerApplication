/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerapp;

/**
 * The Card object represents a playing card.
 *
 * @author James Bird-Sycamore
 * @date 22/03/2020
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
    /**
     * Sets the suit and value of the card
     * 
     * @param suit the card's suit
     * @param value the card's value
     */
    
    /*
    public void setCard(int suit, int value) {
        this.suit = suit;
        this.value = value;
    }
    
    public int getSuit() {
        return this.suit;
    }
    
    public int getValue() {
        return this.value;
    }
    */
}
