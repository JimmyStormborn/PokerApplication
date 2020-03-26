package pokerapp;

import java.util.*;

/**
 *
 * @author James Bird-Sycamore
 * @date 22/03/2020
 */
public class Dealer {
    private Player[] players;                   // The players in the round
    public Card[] deck = makeDeck();            // The deck of playing cards
    private int pos = 0;                        // The position in the deck
    final private Card[] flop = new Card[3];    // The cards in the flop
    final private Card[] turn = new Card[1];    // The cards in the turn
    final private Card[] river = new Card[1];   // The cards in the river
    private boolean flopped = false;            // Flag indicating the flop has been dealt
    private boolean turned = false;             // Flag indicating the turn has been dealt
    private boolean rivered = false;            // Flag indicating the river has been dealt
    private int dealer = 0;                     // The number of who is dealing
    
    /**
     * Constructor: creates the dealer object.
     * 
     * @param players The players in the round.
     */
    public Dealer(Player[] players) {
        this.players = players;
    }
    
    /**
     * Changes the dealer
     * 
     * @param dealer The number representing the new dealer.
     */
    public void setDealer(int dealer) {
        this.dealer = dealer;
    }
    
    /**
     * Deals each player their cards.
     */
    public void dealCards() {
        int i = 0; // Counts how many players have been dealt to.
        int length = players.length; // The amount of players.
        int n; // The player being dealt to.
        while (i < length) {
            n = dealer + i;
            // If the player number being dealt to is higher than the
            // amount of players, minus the amount of players to wrap around.
            // Else deal to the current player in order.
            if (n >= length) {
                players[n - length].setCard1(deck[pos]);
            } else {
                players[n].setCard1(deck[pos]);
            }
            pos++;
            i++;
        }
        
        i = 0;
        while (i < length) {
            n = dealer + i;
            // If the player number being dealt to is higher than the
            // amount of players, minus the amount of players to wrap around.
            // Else deal to the current player in order.
            if (n >= length) {
                players[n - length].setCard2(deck[pos]);
            } else {
                players[n].setCard2(deck[pos]);
            }
            pos++;
            i++;
        }
    }
    
    /**
     * Deals the flop.
     */
    public void dealFlop() {
        int i = 0;
        while (i < 3) {
            flop[i] = deck[pos];
            pos++;
            i++;
        }
        this.flopped = true;
    }
    
    /**
     * Deals the turn.
     */
    public void dealTurn() {
        if (flopped) {
            turn[0] = deck[pos];
            pos++;
            this.turned = true;
        } else {
            System.err.println("Turn before flop");
        }
    }
    
    /**
     * Deals the river.
     */
    public void dealRiver() {
        if (flopped) {
            if (turned) {
                river[0] = deck[pos];
                pos++;
                this.rivered = true;
            } else {
                System.err.println("River before turn");
            }
        } else {
            System.err.println("River before flop");
        }
    }
    
    /**
     * Retrieves the flop from the pot
     * 
     * @return The flop cards
     */
    public Card[] getFlop() {
        if (!flopped) {
            dealFlop();
        }
        return flop;
    }
    
    /**
     * Retrieves the turn from the pot
     * 
     * @return The turn card 
     */
    public Card[] getTurn() {
        if (!turned) {
            dealTurn();
        }
        return turn;
    }
    
    /**
     * Retrieves the river from the pot
     * 
     * @return The river card 
     */
    public Card[] getRiver() {
        if (!rivered) {
            dealRiver();
        }
        return river;
    }
    
    /**
     * Gets the cards of the deck and shuffles them
     * into a random order.
     * 
     * @return The shuffled deck. 
     */
    public Card[] makeDeck() {
        Card[] cards = new Card[52];
        int i = 0;
        
        for (int suit = 0; suit < 4; suit++) {
            for (int val = 2; val <= 14; val++) {
                cards[i] = new Card(suit, val);
                i++;
            }
        }
        return cards;
    }
    
    /**
     * Shuffles the cards.
     */
    public void shuffleDeck() {
        Random rand = new Random();
        int card1;
        int card2;
        int n = 0;
        int N = 100;
        while (n < N) {
            card1 = rand.nextInt(52);
            card2 = rand.nextInt(52);
            
            Card temp = deck[card1];
            deck[card1] = deck[card2];
            deck[card2] = temp;
            
            n++;
        }
        this.pos = 0;
    }
}
