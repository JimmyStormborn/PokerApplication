package pokerapp;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

//             Testing
            
            tester.checkForDoubles(players, this.pot_cards);

            tester.printErrors();
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
        }
        
        // Deal the turn
        private void turn() {
            dealer.dealTurn();
            Card[] turn = dealer.getTurn();
            pot_cards[3] = turn[0];
        }
        
        // Deal the river
        private void river() {
            dealer.dealRiver();
            Card[] river = dealer.getRiver();
            pot_cards[4] = river[0];
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
     * Enter a command to run the program.
     * 
     * p, Starts a poker game
     * t, Tests the program with the test cases
     * w, Lets the user input test cases
     * reset, will reset the test cases
     * q, will quit the program
     * ?, will give the user the available commands
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PokerApp app = new PokerApp();
        Scanner scan = new Scanner(System.in);
        
        // TEST CASES
        Test tester = new Test();
        
        String input = "";
        while (!"q".equals(input)) {
            System.out.print("\nPlease enter a command (? for help): ");
            
            input = scan.nextLine();
            if ("t".equals(input) || "T".equals(input)) {
                try {
                    tester.testCases();
                } catch (Exception ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("w".equals(input) || "W".equals(input)) {
                try {
                    tester.writeTestCase();
                } catch (Exception ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("reset".equals(input)) {
                try {
                    tester.resetTestCases();
                } catch (Exception ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("p".equals(input) || "P".equals(input)) {
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
            } else if ("?".equals(input)) {
                System.out.println("p = play a game of poker\n"
                        + "t = test the program with test cases\n"
                        + "w = write test cases\n"
                        + "reset = reset the test cases\n"
                        + "q = quits the program\n"
                        + "? = help\n");
            }
        }
    }
}
