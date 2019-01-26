package pokerapp;

import java.util.*;

/**
 *
 * @author TheBeast
 */
public class PokerApp {
    
    private class Round {
        final private Player[] players;
        final private Dealer dealer;
        private int chip = 0;
        final private int[][] cards = new int[5][2];
        final private Parser parser = new Parser();
        
        final private int min = 25;
        final private int small = 25;
        final private int big = 50;
        
        private Round(Player[] players, Dealer dealer) {
            this.players = players;
            this.dealer = dealer;
        }
        
        private void run() {
            start();
            flop();
            turn();
            river();
        }
        
        private void start() {
            dealer.dealCards();
            for (Player player : players) {
                System.out.print("Player" + player.playerNum + " cards: ");
                printCards(player.getCards());
                player.getHand(cards);
                System.out.println("Player" + player.playerNum + " hand: " + player.hand);
            }
        }
        
        private void flop() {
            dealer.dealFlop();
            int[][] flop = dealer.getFlop();
            int i = 0;
            for(int[] card : flop) {
                this.cards[i] = card;
                i++;
            }
            printCards(Arrays.copyOfRange(cards, 0, 3));
            
            for (Player player : players) {
                System.out.print("Player" + player.playerNum + " cards: ");
                printCards(player.getCards());
                player.getHand(cards);
                System.out.println("Player" + player.playerNum + " hand: " + player.hand);
            }
        }
        
        private void turn() {
            dealer.dealTurn();
            int[][] turn = dealer.getTurn();
            cards[3] = turn[0];
            printCards(Arrays.copyOfRange(cards, 0, 4));
            
            for (Player player : players) {
                System.out.print("Player" + player.playerNum + " cards: ");
                printCards(player.getCards());
                player.getHand(cards);
                System.out.println("Player" + player.playerNum + " hand: " + player.hand);
            }
        }
        
        private void river() {
            dealer.dealRiver();
            int[][] river = dealer.getRiver();
            cards[4] = river[0];
            printCards(cards);
            
            for (Player player : players) {
                System.out.print("Player" + player.playerNum + " cards: ");
                printCards(player.getCards());
                player.getHand(cards);
                System.out.println("Player" + player.playerNum + " hand: " + player.hand);
            }
        }
        
        private void printCards(int[][] cards) {
            System.out.println(parser.cardsToString(cards));
        }
    }
    
    public PokerApp() {}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PokerApp app = new PokerApp();
        
        Parser parser = new Parser();
        
        // Create players
        int num = 5;
        int chips = 2000;
        Player[] players = new Player[num];
        for (int n = 0; n < num; n++) {
            players[n] = new Player(n, chips);
        }
        
        // Create dealer
        Dealer dealer = new Dealer(players);
        
        //Create round
        Round round;
        round = app.new Round(players, dealer);
        
        // Run rounds
        round.run();
        
    }
    
}
