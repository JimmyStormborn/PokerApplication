package pokerapp;

import java.util.*;

/**
 *
 * @author TheBeast
 */
public class Dealer {
    private Player[] players;
    // private int[][] deck = makeDeck();
    public int[][] deck;
    private int pos = 0;
    final private int[][] flop = new int[3][2];
    final private int[][] turn = new int [1][2];
    final private int[][] river = new int [1][2];
    private boolean flopped = false;
    private boolean turned = false;
    private boolean rivered = false;
    private int dealer = 0;
    
    public Dealer(Player[] players) {
        this.players = players;
    }
    
    public void setDealer(int dealer) {
        this.dealer = dealer;
    }
    
    /**
     * Deals each player their cards.
     */
    public void dealCards() {
        int i = 1;
        int length = players.length;
        int n;
        while (i <= length) {
            n = dealer + i;
            if (n >= length) {
                players[n - length].setCard1(deck[pos]);
            } else {
                players[n].setCard1(deck[pos]);
            }
            pos++;
            i++;
        }
        
        i = 1;
        while (i <= length) {
            n = dealer + i;
            if (n >= length) {
                players[n - length].setCard2(deck[pos]);
            } else {
                players[n].setCard2(deck[pos]);
            }
            pos++;
            i++;
        }
    }
    
    public void dealFlop() {
        int i = 0;
        while (i < 3) {
            flop[i] = deck[pos];
            pos++;
            i++;
        }
        this.flopped = true;
    }
    
    public void dealTurn() {
        if (flopped) {
            turn[0] = deck[pos];
            pos++;
            this.turned = true;
        } else {
            System.err.println("Turn before flop");
        }
    }
    
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
    
    public int[][] getFlop() {
        if (!flopped) {
            dealFlop();
        }
        return flop;
    }
    
    public int[][] getTurn() {
        if (!turned) {
            dealTurn();
        }
        return turn;
    }
    
    public int[][] getRiver() {
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
    private int[][] makeDeck() {
        int[][] cards = new int[52][2];
        int i = 0;
        
        for (int suit = 0; suit < 4; suit++) {
            for (int val = 2; val <= 14; val++) {
                cards[i][0] = suit;
                cards[i][1] = val;
                i++;
            }
        }
        this.deck = cards;
        shuffleDeck();
        return cards;
    }
    
    /**
     * Shuffles the cards.
     */
    private void shuffleDeck() {
        Random rand = new Random();
        int card1;
        int card2;
        int n = 0;
        int N = 100;
        while (n < N) {
            card1 = rand.nextInt(52);
            card2 = rand.nextInt(52);
            
            int[] temp = deck[card1];
            deck[card1] = deck[card2];
            deck[card2] = temp;
            
            n++;
        }
    }
}
