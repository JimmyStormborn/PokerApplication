package pokerapp;

import java.util.*;

/**
 * @desc 
 *
 * @author James Bird-Sycamore
 * @date 28/03/2020
 */
public class PokerApp {
    
    private class Round {
        // GLOBAL
        // OBJECTS
        private Player[] players; // The players in the round
        private Dealer dealer; // The dealer in the round
        private Parser parser = new Parser(); // Creates a parser
        private Test tester = new Test(); // Creates a tester
        
        // VARIABLES
        private int round = 0;
        private final int chips = 0;
        private Card[] pot_cards = new Card[5];
        
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
                player.getCombinations(this.pot_cards);
            }
            
            findWinner();
            
            System.out.println("\n");

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
        
        // Deal out the player's cards
        private void start() {
            round += 1;
            
            dealer.shuffleDeck();

            System.out.print("\nRound " + round + ":\n");
            System.out.println("Player" + dealer.dealer + " is dealing");

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
        
        private void findWinner() {
            int winner = -1;
            int[] draw = new int[players.length];
            boolean draw_flag = false;
            int[] winning_hand = new int[7];
            int[] players_hand;
            for (Player player : players) {
                
                player.findHandValue();
                players_hand = player.hand_value;
                
                if (Arrays.equals(winning_hand, players_hand)) {
                    draw[0] = winner;
                    winner = -1;
                    draw[1] = player.playerNum;
                    draw_flag = true;
                } else if (players_hand[0] > winning_hand[0] || 
                    ((players_hand[1] > winning_hand[1] || players_hand[2] > winning_hand[2]
                    || players_hand[3] > winning_hand[3] || players_hand[4] > winning_hand[4]
                    || players_hand[5] > winning_hand[5] || players_hand[6] > winning_hand[6]) && players_hand[0] == winning_hand[0])) {
                    winner = player.playerNum;
                    winning_hand = players_hand;
                    draw = new int[players.length];
                    draw_flag = false;
                }
                
                System.out.println("Player"+player.playerNum+"'s hand is "+
                        parser.cardsToString(player.hand_cards)+"with value "+
                        parser.handToString(player.hand_value));
            }
            
            if (draw_flag) {
                System.out.print("Draw between ");
                for (int d : draw) {
                    System.out.print("Player"+d+" ");
                }
                System.out.println();
            } else {
                System.out.println("Winner is Player"+winner+"!");
            }
        }
        
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
            round.run();
            r += 1;
        }
    }
    
}
