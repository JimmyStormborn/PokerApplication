package pokerapp;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @desc 
 *
 * @author James Bird-Sycamore
 * @date 22/03/2020
 */
public class PokerApp {
    
    private class Round {
        final private Player[] players;
        final private Dealer dealer;
        private final int chips = 0;
        private Card[] pot_cards = new Card[5];
        final private Parser parser = new Parser();
        final private Test tester = new Test(); // Creates a tester
        
        final private int min = 25;
        final private int small = 25;
        final private int big = 50;
        
        private Round(Player[] players) {
            this.players = players;
            dealer = new Dealer(players);
        }
        
        private Round(Player[] players, Dealer dealer) {
            this.players = players;
            this.dealer = dealer;
        }
        
        private void run() {
            start();
            flop();
            turn();
            river();
            for (Player player : players) {
                try {
                    player.getCombinations(this.pot_cards);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            findWinner();

            // Testing
//            tester.checkForDoubles(players, this.cards);
//            tester.printErrors();
        }
        
        // Deal out the player's cards
        private void start() {
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
        private void findWinner() {
            int winner = -1;
            ArrayList<Integer> draw = new ArrayList<>();
            int[] winning_hand = new int[6];
            for (Player player : players) {
                if (player.hand_value[0] > winning_hand[0]) {
                    winner = player.playerNum;
                    winning_hand = player.hand_value;
                } else if (player.hand_value[0] == winning_hand[0]) {
                    if (player.hand_value[1] > winning_hand[1]) {
                        winner = player.playerNum;
                        winning_hand = player.hand_value;
                    } else if (player.hand_value[1] == winning_hand[1]) {
                        if (player.hand_value[2] > winning_hand[2]) {
                            winner = player.hand_value[2];
                        } else if (player.hand_value[2] == winning_hand[2]) {
                            if (!draw.contains(winner) && winner >= 0) {
                                draw.add(winner);
                            }
                            if (!draw.contains(player.playerNum)) {
                                draw.add(player.playerNum);
                            }
                            winner = -1;
                        }
                    }
                }
            }
            if (winner < 0) {
                if (!draw.isEmpty()) {
                    System.out.print("\nDraw between ");
                    boolean flag = false;
                    for (int d : draw) {
                        if (flag) {
                            System.out.print("and ");
                        }
                        System.out.print("player " + d + " ");
                        flag = true;
                    }
                    System.out.println(".");
                }
            } else {
                System.out.println("\nWinner is " + winner + ", hand = " + parser.handToString(winning_hand));
            }
        }
        */
        
        private void printCards(Card[] cards) {
            System.out.println(parser.cardsToString(cards));
        }
        
        private void printHands() {
            for (Player player : players) {
                System.out.print("\nPlayer" + player.playerNum + " cards: ");
                printCards(player.getCards());
//                player.getHand(cards);
//                System.out.println("Player" + player.playerNum + " hand: " + parser.handToString(player.hand_value));
            }
        }
    }
    
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

        int chips = 2000;

        Player[] players = new Player[num];
        for (int n = 0; n < num; n++) {
            players[n] = new Player(n, chips);
        }

        // Create dealer
        Dealer dealer = new Dealer(players);
        dealer.shuffleDeck();
        int d = 0; // The dealers number, which player is dealing

        //Create round
        Round round;
        round = app.new Round(players, dealer);

        // Run rounds
        int r = 1;
        while (r <= rounds) {

//            System.out.print("\nRound " + r + ":\n");
//            System.out.println("Player" + d + " is dealing");

            round.pot_cards = new Card[5];
            dealer.dealCards();
            round.run();

            // Change the dealer
            if (d >= players.length-1) {
                d = 0;
            } else {
                d += 1;
            }
            dealer.setDealer(d);
            dealer.shuffleDeck();
            round = app.new Round(players, dealer);
            r += 1;
        }
    }
    
}
