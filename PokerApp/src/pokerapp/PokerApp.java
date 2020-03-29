package pokerapp;

import java.util.*;

/**
 * The main application which runs the poker application.
 *
 * @author James Bird-Sycamore
 * Last Updated 29/03/2020
 */
public class PokerApp {
    
    /**
     * The round class runs every round of the poker game
     * and keeps track of each player.
     */
    private class Round {
        // GLOBAL
        // OBJECTS
        private Player[] players; // The players in the round
        private Dealer dealer; // The dealer in the round
        private Parser parser = new Parser(); // Creates a parser
        private Test tester = new Test(); // Creates a tester
        
        // VARIABLES
        private int round = 0; // The current round
        private final int chips = 0; // The chips each player starts with
        private Card[] pot_cards = new Card[5]; // The cards in the middle
        
        final private int min = 25; // The minimum bet
        final private int small = 25; // The small bet
        final private int big = 50; // The big bet
        
        /**
         * Default Constructor: Creates the round object.
         * 
         * @param players The players in the round.
         * @param dealer The dealer object.
         */
        private Round(Player[] players, Dealer dealer) {
            this.players = players;
            this.dealer = dealer;
        }
        
        /**
         * Runs the round.
         */
        private void run() {
            start();
            flop();
            turn();
            river();
            // Finds the players combos and then which of the players have won.
            for (Player player : players) {
                player.getCombinations(this.pot_cards);
            }
            findWinner();
            
            System.out.println("\n"); // Add some whitespace.

            // Change the dealer
            int d = dealer.dealer;
            if (d >= players.length-1) {
                dealer.setDealer(0);
            } else {
                dealer.setDealer(d + 1);
            }

            // Testing
//            tester.checkForDoubles(players, this.cards);
//            tester.printErrors();
        }
        
        /**
         * Starts off the round.
         */
        private void start() {
            round += 1;
            
            dealer.shuffleDeck();

            System.out.print("\nRound " + round + ":\n");

            // Resets all the cards from previous rounds.
            pot_cards = new Card[5];
            for (Player player : players) {
                player.setCards(null, null);
            }
            dealer.dealCards();
//            printHands();
        }
        
        
        // Deal the flop
        private void flop() {
            dealer.dealFlop();
            Card[] flop = dealer.getFlop();
            int i = 0;
            for(Card card : flop) {
                this.pot_cards[i] = card;
                i++;
            }
//            System.out.println("\nFlop:");
//            printCards(Arrays.copyOfRange(cards, 0, 3));
//            
//            printHands();
        }
        
        // Deal the turn
        private void turn() {
            dealer.dealTurn();
            Card[] turn = dealer.getTurn();
            pot_cards[3] = turn[0];
//            System.out.println("\nTurn:");
//            printCards(Arrays.copyOfRange(cards, 0, 4));
//            
//            printHands();
        }
        
        // Deal the river
        private void river() {
            dealer.dealRiver();
            Card[] river = dealer.getRiver();
            pot_cards[4] = river[0];
//            System.out.println("\nRiver:");
//            printCards(cards);
//            
//            printHands();
        }
        
        /**
         * Finds out who wins the round or if there is a draw.
         * 
         * Only used if there are player who have not folded left
         * in the round.
         */
        private void findWinner() {
            int winner = -1;
            int[] draw = new int[players.length]; // Stores players that have drawn.
            boolean draw_flag = false; // True if there is a draw.
            int[] winning_hand = new int[7]; // The current winning hand.
            int[] players_hand; // The current player's hand.
            for (Player player : players) {
                // If the player has folded, don't include them.
                if (!player.fold) {
                    player.findHandValue();
                    players_hand = player.hand_value;
                    // Check if there is a draw.
                    if (Arrays.equals(winning_hand, players_hand)) {
                        if (draw[0] == 0) {
                            draw[0] = winner;
                            winner = -1;
                            draw[1] = player.playerNum;
                            draw_flag = true;
                        } else {
                            int n = 2;
                            while (n < draw.length) {
                                if (draw[n] == 0) {
                                    draw[n] = player.playerNum;
                                    break;
                                }
                                n++;
                            }
                        }
                    } else {
                        for (int val = 0; val < players_hand.length; val++) {
                            // If the player has a better hand value then 
                            // the winner, they are the winner. If they
                            // have the same value, move to the next value.
                            // Otherwise stop checking.
                            if (players_hand[val] > winning_hand[val]) {
                                winner = player.playerNum;
                                winning_hand = players_hand;
                                draw = new int[players.length];
                                draw_flag = false;
                                break;
                            } else if (players_hand[val] != winning_hand[val]) {
                                break;
                            }
                        }
                    }
                }
                
                System.out.println("Player"+player.playerNum+"'s hand is "+
                        parser.cardsToString(player.hand_cards)+"with value "+
                        parser.handToString(player.hand_value));
            }
            
            if (draw_flag) {
                System.out.print("Draw between ");
                for (int d : draw) {
                    if (d != 0) {
                        System.out.print("Player"+d+" ");
                    }
                }
                System.out.println(".");
            } else {
                System.out.println("Winner is Player"+winner+"!");
            }
        }
    }
    
    /**
     * Constructor: Creates the poker application object.
     */
    public PokerApp() {}

    /**
     * Main method
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PokerApp app = new PokerApp();
        Scanner scan = new Scanner(System.in);
                    
        System.out.print("\nEnter the number of players: ");
        int num = scan.nextInt();

        System.out.print("\nEnter the number of rounds: ");
        int rounds = scan.nextInt();

        int chips = 2000; // Starting amount of chips.
        // Create the players.
        Player[] players = new Player[num];
        for (int n = 0; n < num; n++) {
            players[n] = new Player(n+1, chips);
        }

        // Create dealer
        Dealer dealer = new Dealer(players);
        dealer.shuffleDeck();
        int d = 1; // The dealers number, which player is dealing

        //Create round
        Round round;
        round = app.new Round(players, dealer);

        // Run rounds
        int r = 1;
        while (r <= rounds) {
            round.run();
            r += 1;
        }
    }
}
