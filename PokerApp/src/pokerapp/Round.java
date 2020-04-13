package pokerapp;

import java.util.Arrays;
import java.util.Scanner;

/**
* The round class runs every round of the poker game
* and keeps track of each player.
* 
* @author James Bird-Sycamore
* Last Updated 13/04/2020
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
   private boolean pre_flop = true;
   
   private int current_bet = 0;
   private int pot_chips = 0;
   private int side_chips = 0;

   final private int min = 10; // The minimum bet
   final private int small = 10; // The small bet
   final private int big = 20; // The big bet
   
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
       
        while (players.length != 1) {
            start();
            System.out.println("\nStart:");
            System.out.println("\nPot Chips: " + pot_chips);
            runPlayers();

            flop();
            current_bet = 0;
            System.out.println("\nFlop:");
            System.out.println("\nPot Chips: " + pot_chips);
            System.out.println(parser.cardsToString(pot_cards) + "\n");
            runPlayers();

            turn();
            current_bet = 0;
            System.out.println("\nTurn:");
            System.out.println("\nPot Chips: " + pot_chips);
            System.out.println(parser.cardsToString(pot_cards) + "\n");
            runPlayers();

            river();
            current_bet = 0;
            System.out.println("\nRiver:");
            System.out.println("\nPot Chips: " + pot_chips);
            System.out.println(parser.cardsToString(pot_cards) + "\n");
            runPlayers();

            // Finds the players combos and then which of the players have won.
            for (Player player : players) {
                player.getCombinations(this.pot_cards);
            }
            System.out.println();

            findWinner();
            
            // End of round, find how chips each player has and if a player is out
            
            int n = 0;
            for (Player player : players) {
                System.out.println("Player" + player.playerNum + "'s chips = " + player.chips);
                if (player.chips <= 0) {
                    n++;
                }
            }
            
            if (n > 0) {
                Player[] new_players = new Player[players.length-n];
                for (int p = 0; p < players.length; p++) {
                    int i = 0;
                    if (players[p].chips > 0) {
                        new_players[i] = players[p];
                        i++;
                    }
                }
                players = new_players;
            }

            System.out.println(); // Add some whitespace.
            
            Scanner scan = new Scanner(System.in);
            System.out.print("Press Enter to start next round, else type q to quit: ");
            if ("q".equals(scan.nextLine())) {
                break;
            }

            // Change the dealer
            int d = dealer.dealer;
            if (d >= players.length-1) {
                dealer.setDealer(0);
            } else {
                dealer.setDealer(d + 1);
            }
       }
        
        System.out.println("Congratulations Player" + players[0].playerNum + " you win!");

       // Testing
       /*
       tester.checkForDoubles(players, this.pot_cards);

       try {
            Thread.sleep(100);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
       tester.printErrors();
       */
   }

   /**
    * Starts off the round.
    */
   private void start() {
       round += 1;

       dealer.shuffleDeck();

       System.out.println("\nRound " + round + ":");

       // Resets all the cards from previous rounds.
       pot_cards = new Card[5];
       pot_chips = 0;
       pre_flop = true;
       for (Player player : players) {
           player.setCards(null, null);
           player.fold = false;
           player.allin = false;
       }
       
        int d = dealer.dealer;
        int s, b;
        if ((d+1) >= players.length) {
            s = (d+1) - players.length;
        } else {
            s = d+1;
        }
        if ((d+2) >= players.length) {
           b = (d+2) - players.length;
        } else {
            b = d+2;
        }
        if (players[s].chips < small) {
            players[s].current_bet = players[s].chips;
            current_bet = small;
            pot_chips += players[s].chips;
            players[s].chips = 0;
            players[s].allin = true;
        } else {
            players[s].current_bet = small;
            current_bet = small;
            pot_chips += small;
            players[s].chips -= small;
        }
        if (players[b].chips < big) {
            players[b].current_bet = players[b].chips;
            current_bet = big;
            pot_chips += players[b].chips;
            players[b].chips = 0;
            players[b].allin = true;
        } else {
            players[b].current_bet = big;
            current_bet = big;
            pot_chips += big;
            players[b].chips -= big;
       }
       
       dealer.dealCards();
   }


   // Deal the flop
   private void flop() {
       dealer.dealFlop();
       pre_flop = false;
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
                    int n = 1;
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
            System.out.println("Player"+player.playerNum+"'s hand is "+
                    parser.cardsToString(player.hand_cards)+"with value "+
                    parser.handToString(player.hand_value));
           }
       }

       if (draw_flag) {
           System.out.print("Draw between ");
           for (int d : winners) {
               if (d != 0) {
                   System.out.print("Player"+d+" ");
                   players[d-1].chips += pot_chips / 2;
               }
           }
       } else {
           players[winner-1].chips += pot_chips;
           
           System.out.println("Winner is Player"+winner+"!");
       }
       
       return winners;
   }
   
    private void printPlayer(Player player) {
        if (!player.computer) {
             int player_num = player.playerNum;
             Card[] player_cards = player.getCards();
             int[] hand_value = player.hand_value;
             System.out.println("\nPlayer" + player_num + ": " + parser.cardsToString(player_cards) + "\tChips: " + player.chips);
        }
   }
   
    private void runPlayers() {
        Scanner scan = new Scanner(System.in);
        String input;
        int bet;
        boolean flag;
        boolean raise = false;

        // Figure out who starts the turn off
        int p; // The current players turn
        if (pre_flop) {
            p = dealer.dealer + 3;
        } else {
            p = dealer.dealer + 1;
            // Reset the previous rounds bet
            current_bet = 0;
            for (Player player : players) {
                player.current_bet = 0;
            }
        }
        
        int n = 0;
        while (n < players.length) {
           
            if (p >= players.length) {
                p -= players.length;
            }
           
            printPlayer(players[p]);
            bet = 0;
            flag = true;
            while (flag) {
                if (players[p].fold || players[p].allin) {
                    flag = false;
                } else if (players[p].computer) {
                    // Add AI
                } else {
                    System.out.print("Enter your move (f = fold, c = call/check, r = raise): ");
                    input = scan.nextLine();
                    if ("f".equals(input)) {
                        players[p].fold = true;
                        System.out.println("\nPlayer" + players[p].playerNum + " folds.");
                        flag = false;
                    } else if ("c".equals(input)) {
                        if (players[p].current_bet < current_bet) {
                            System.out.print("\nPlayer" + players[p].playerNum + " calls ");
                            if (players[p].chips <= current_bet) {
                                players[p].current_bet = players[p].chips;
                                pot_chips += players[p].chips;
                                players[p].chips = 0;
                                players[p].allin = true;
                                System.out.println(players[p].current_bet + " chips, they are all in.");
                            } else {
                                bet = current_bet - players[p].current_bet;
                                players[p].chips -= bet;
                                pot_chips += bet;
                                players[p].current_bet = current_bet;
                                System.out.println(bet + " chips.");
                            }
                            players[p].current_bet = current_bet;
                        } else {
                            System.out.println("\nPlayer" + players[p].playerNum + " checks.");
                        }
                        flag = false;
                    } else if ("r".equals(input)) {
                        while (flag) {
                            System.out.print("\nEnter raise amount: ");
                            bet = scan.nextInt();
                            // Player goes all in
                            if (bet + current_bet >= players[p].chips) {
                                bet = players[p].chips; // They bet all their chips
                                pot_chips += players[p].chips; // Adds the chips to the pot
                                players[p].chips = 0; // Removes all their chips
                                players[p].current_bet = bet + current_bet; // Their new current bet
                                current_bet = players[p].current_bet; // The reounds new current bet
                                players[p].allin = true;
                                System.out.println("\nPlayer" + players[p].playerNum + " raises " + bet + " chips, they are all in.");
                                flag = false;
                            } else if (bet > 0) {
                                players[p].chips -= ((bet + current_bet) - players[p].current_bet); // They remove their chips by the amount they raised above their previous bet.
                                players[p].current_bet = bet + current_bet; // Their new current bet.
                                current_bet = players[p].current_bet; // The rounds new current bet.
                                pot_chips += bet; // Adds the chips to the pot
                                System.out.println("\nPlayer" + players[p].playerNum + " raises " + bet + " chips.");
                                flag = false;
                            } else {
                                System.err.println("Raise must be above 0 chips");
                            }
                        }
                        raise = true;
                    }
                }
            }
            System.out.println("\nPot Chips: " + pot_chips);
            
            
            // If there is a raise, any player who hasn't folded has to go again.
            if (raise) {
                n = 1;
                raise = false;
            } else {
                n++;
            }
            p++;
       }
   }
}
