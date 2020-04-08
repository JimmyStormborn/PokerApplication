package pokerapp;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main application which runs the poker application.
 *
 * @author James Bird-Sycamore
 * Last Updated 08/04/2020
 */
public class PokerApp {
    
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
                    try {
                        Thread.sleep(100);
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    tester.printErrors();
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
                round = new Round(players);

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
                        + "? = help");
            }
        }
    }
}
