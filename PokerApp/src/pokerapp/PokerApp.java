package pokerapp;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main application which runs the poker application.
 *
 * @author James Bird-Sycamore
 * Last Updated 14/04/2020
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
     * d, Gathers the data for the probabilities
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
        // Runs until the player quits
        while (!"q".equals(input)) {
            System.out.print("\nPlease enter a command (? for help): ");
            
            input = scan.nextLine();
            if ("t".equals(input) || "T".equals(input)) {
                // Run tests
                try {
                    tester.testCases();
                    try {
                        Thread.sleep(100); // Pause before printing errors
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    tester.printErrors();
                } catch (Exception ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("w".equals(input) || "W".equals(input)) {
                // Write a test case
                try {
                    tester.writeTestCase();
                } catch (Exception ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("reset".equals(input)) {
                // Set the test cases to empty
                try {
                    tester.resetTestCases();
                } catch (Exception ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("p".equals(input) || "P".equals(input)) {
                // Play a poker game
                System.out.print("\nEnter the number of players: ");
                int num = scan.nextInt();
                if (num > 0 && num <= 10) {
                    int chips = 2000; // Starting amount of chips.
                    // Create the players.
                    Player[] players = new Player[num];
                    AI simpleAI = new AI(0);
                    players[0] = new Player(1, chips);
                    for (int n = 1; n < num; n++) {
                        players[n] = new Player(n+1, chips, simpleAI);
                    }

                    // Create dealer
                    Dealer dealer = new Dealer(players);
                    dealer.shuffleDeck();

                    //Create round
                    Round round;
                    round = new Round(players);

                    // Run rounds
                    round.run();
                } else {
                    System.err.println("The number of players must be greater than 0 and less than 10");
                }
            } else if ("d".equals(input)) {
                // Get data
                System.out.println("Gathering data...");
                Probability prob = new Probability();
                try {
                    prob.run();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PokerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if ("?".equals(input)) {
                // Print help
                System.out.println("p = play a game of poker\n"
                        + "t = test the program with test cases\n"
                        + "w = write test cases\n"
                        + "reset = reset the test cases\n"
                        + "d = get all the data for probabilities\n"
                        + "q = quits the program\n"
                        + "? = help");
            }
        }
    }
}
