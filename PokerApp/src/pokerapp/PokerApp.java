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
        
        // Deal out the player's cards
        private void start() {
            for (Player player : players) {
                System.out.print("Player" + player.playerNum + " cards: ");
                printCards(player.getCards());
                player.getHand(cards);
                System.out.println("Player" + player.playerNum + " hand: " + parser.handToString(player.hand));
            }
        }
        
        
        // Deal the flop
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
                System.out.println("Player" + player.playerNum + " hand: " + parser.handToString(player.hand));
            }
        }
        
        // Deal the turn
        private void turn() {
            dealer.dealTurn();
            int[][] turn = dealer.getTurn();
            cards[3] = turn[0];
            printCards(Arrays.copyOfRange(cards, 0, 4));
            
            for (Player player : players) {
                System.out.print("Player" + player.playerNum + " cards: ");
                printCards(player.getCards());
                player.getHand(cards);
                System.out.println("Player" + player.playerNum + " hand: " + parser.handToString(player.hand));
            }
        }
        
        // Deal the river
        private void river() {
            dealer.dealRiver();
            int[][] river = dealer.getRiver();
            cards[4] = river[0];
            printCards(cards);
            
            for (Player player : players) {
                System.out.print("Player" + player.playerNum + " cards: ");
                printCards(player.getCards());
                player.getHand(cards);
                System.out.println("Player" + player.playerNum + " hand: " + parser.handToString(player.hand));
            }
        }
        
        private void printCards(int[][] cards) {
            System.out.println(parser.cardsToString(cards));
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
        
        boolean flag = true;
        while (flag) {
            System.out.print("Do you want to run a test (y/n): ");
           
            switch (scan.nextLine()) {
                case "y":{
                    // Tests
                    Test test = new Test();
                    
                    System.out.print("\nEnter your test number (0,1,2,3,...): ");
                    int test_num = scan.nextInt();
                    test.runTest(test_num);
                    
                    int num = test.num;
                    
                    int chips = 2000;
                    
                    Player[] players = new Player[num];
                    for (int n = 0; n < num; n++) {
                        players[n] = new Player(n, chips);
                        players[n].setCard1(test.cards[n][0]);
                        players[n].setCard2(test.cards[n][1]);
                    }       
                    
                    // Create dealer
                    Dealer dealer = new Dealer(players);
                    dealer.deck = test.pot;
                    
                    //Create round
                    Round round;
                    round = app.new Round(players, dealer);
                    
                    // Run rounds
                    round.run();
                    
                    flag = false;
                        break;
                    }
                case "n":{
                    // Input
                    
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
                    
                    //Create round
                    Round round;
                    round = app.new Round(players, dealer);
                    
                    // Run rounds
                    int r = 1;
                    while (r <= rounds) {
                        System.out.print("\nRound " + r + ":\n");
                        dealer = new Dealer(players);
                        dealer.dealCards();
                        round.run();
                        r += 1;
                    }
                    
                    flag = false;
                        break;
                    }
                case "q":
                    flag = false;
                    break;
            }
        }
        
    }
    
}
