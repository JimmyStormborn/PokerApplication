package pokerapp;

import java.util.Arrays;

/**
* The round class runs every round of the poker game
* and keeps track of each player.
* 
* @author James Bird-Sycamore
* Last Updated 08/04/2020
*/
public class Round {
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
    * Constructor: Used to test Program with test cases
    * 
    * @param players
    * @param pot 
    */
   public Round (Player[] players, Card[] pot) {
       this.players = players;
       this.dealer = new Dealer(players);
       this.pot_cards = pot;
   }

   /**
    * Default Constructor: Creates the round object.
    * 
    * @param players The players in the round.
    */
   public Round(Player[] players) {
       this.players = players;
       this.dealer = new Dealer(players);
   }
   
   public int[] test(int num) {
       System.out.println("\nTesting Case " + num);
        for (Player player : players) {
            player.getCombinations(this.pot_cards);
        }
       return findWinner();
   }

   /**
    * Runs the round.
    */
   public void run() {
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

       try {
            Thread.sleep(100);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
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
   private int[] findWinner() {
       int winner = -1;
       int[] winners = new int[players.length]; // Stores players that have drawn.
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
                    int n = 2;
                    while (n < winners.length) {
                        if (winners[n] == 0) {
                            winners[n] = player.playerNum;
                            draw_flag = true;
                            break;
                        }
                        n++;
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
                           winners = new int[players.length];
                           winners[0] = winner;
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
           for (int d : winners) {
               if (d != 0) {
                   System.out.print("Player"+d+" ");
               }
           }
           System.out.println(".");
       } else {
           System.out.println("Winner is Player"+winner+"!");
       }
       
       return winners;
   }
}
