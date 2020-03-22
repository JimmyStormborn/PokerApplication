package pokerapp;

import java.util.*;

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
        private final int chip = 0;
        private Card[] cards = new Card[5];
        final private Parser parser = new Parser();
        
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
//            findWinner();
        }
        
        // Deal out the player's cards
        private void start() {
//            dealer.dealCards();
            printHands();
        }
        
        
        // Deal the flop
        private void flop() {
            dealer.dealFlop();
            Card[] flop = dealer.getFlop();
            int i = 0;
            for(Card card : flop) {
                this.cards[i] = card;
                i++;
            }
            System.out.println("\nFlop:");
            printCards(Arrays.copyOfRange(cards, 0, 3));
            
            printHands();
        }
        
        // Deal the turn
        private void turn() {
            dealer.dealTurn();
            Card[] turn = dealer.getTurn();
            cards[3] = turn[0];
            System.out.println("\nTurn:");
            printCards(Arrays.copyOfRange(cards, 0, 4));
            
            printHands();
        }
        
        // Deal the river
        private void river() {
            dealer.dealRiver();
            Card[] river = dealer.getRiver();
            cards[4] = river[0];
            System.out.println("\nRiver:");
            printCards(cards);
            
            printHands();
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
        
        boolean flag = true;
        while (flag) {
            System.out.print("Do you want to run a test (y/n): ");
           
            switch (scan.nextLine()) {
                case "y":{
                    /*
                    // Tests
                    Test test = new Test();
                    
                    System.out.print("\nEnter your test type (0 = matches): ");
                    int test_num = scan.nextInt();
                    
                    System.out.print("\nEnter your test number: ");
                    int t = scan.nextInt();
                    if (!(test.runTest(test_num, t))) {
                        System.err.println("No such test");
                        return;
                    }
                    
                    int num = test.num;
                    
                    int chips = 2000;
                    
                    Player[] players = new Player[num];
                    for (int n = 0; n < num; n++) {
                        players[n] = new Player(n, chips);
//                        players[n].setCard1(test.cards[n].suit);
//                        players[n].setCard2(test.cards[n].value);
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
*/
                    System.out.println("Testing not implemented");
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
                    dealer.shuffleDeck();
                    
                    //Create round
                    Round round;
                    round = app.new Round(players, dealer);
                    
                    // Run rounds
                    int r = 1;
                    while (r <= rounds) {
                        System.out.print("\nRound " + r + ":\n");
                        round.cards = new Card[5];
                        dealer.dealCards();
                        round.run();
                        dealer = new Dealer(players);
                        round = app.new Round(players, dealer);
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
