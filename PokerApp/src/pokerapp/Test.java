package pokerapp;

import java.util.ArrayList;

import java.io.*;
import java.util.*;
  
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

/**
 * Tests the program for any errors.
 *
 * @author James Bird-Sycamore
 * @date 18/04/2020
 */
public class Test {
    
    private ArrayList<String> errors = new ArrayList<>();
    
    /**
     * Constructor: Creates the test object.
     */
    public Test() {}
    
    /**
     * Prints the errors that were found during testing.
     */
    public void printErrors() {
        if (errors.size() > 0) {
            System.err.println("List of errors:");
            for (String error : errors) {
                System.err.println(error);
            }
        } else {
            System.err.println("Congrats! There are no errors.");
        }
        errors = new ArrayList<>(); // Resets the errors
    }
    
    /**
     * Checks to see there are no duplicate cards in the round.
     * 
     * @param players The players in the round.
     * @param pot The pot cards in the round.
     */
    public void checkForDoubles(Player[] players, Card[] pot) {
        Card[] cards = new Card[players.length*2 + pot.length]; // All the cards in the round
        int n = 0;
        // Collect all the cards in the round into one array
        for (Player player : players) {
            cards[n] = player.getCard1();
            n++;
            cards[n] = player.getCard2();
            n++;
        }
        int p = 0;
        while (p < 5) {
            cards[n] = pot[p];
            n++;
            p++;
        }
        
        String error = "There are two of the same card: ";
        
        // Make sure none of the cards are the same as eachother
        for (int i = 0; i < cards.length; i++) {
            for (int j = i+1; j < cards.length; j++) {
                if (cards[i].value == cards[j].value && cards[i].suit == cards[j].suit) {
                    errors.add(error+cards[i].value + "" + cards[i].suit);
                }
            }
        }
    }
    
    /**
     * Runs the test cases to make sure the program 
     * is outputting the correct winners.
     * 
     * @throws Exception File Not Found exception
     */
    public void testCases() throws Exception {
        Player[] players;
        Card[] pot;
        
        Parser parser = new Parser();
        // Set up the parser
        JSONParser JSONparser = new JSONParser();
        // Parse the file to an object
        Object obj = JSONparser.parse(new FileReader("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\test_cases.json"));
        // Change the object into a JSON object
        JSONObject json = (JSONObject) obj;
        
        JSONArray tests = (JSONArray) json.get("tests");
        
        if (tests == null) {
            System.err.println("There are no test cases.");
            return;
        }
        
        
        JSONArray JSONplayer;
        Map JSONcard;
        Card card, card1, card2;
        for (int i = 0; i < tests.size(); i++) {
            JSONObject test = (JSONObject) tests.get(i);
            JSONArray JSONplayers = (JSONArray) test.get("players");
            JSONArray JSONpot = (JSONArray) test.get("pot");
            
            players = new Player[JSONplayers.size()];
            pot = new Card[5];
            for (int p = 0; p < JSONplayers.size(); p++) {
                JSONplayer = (JSONArray) JSONplayers.get(p);
                JSONcard = (Map) JSONplayer.get(0);
                card1 = new Card(Math.toIntExact((long) JSONcard.get("suit")), Math.toIntExact((long) JSONcard.get("value")));
                JSONcard = (Map) JSONplayer.get(1);
                card2 = new Card(Math.toIntExact((long) JSONcard.get("suit")), Math.toIntExact((long) JSONcard.get("value")));
                players[p] = new Player(card1, card2, p+1);
            }
            for (int c = 0; c < JSONpot.size(); c++) {
                JSONcard = (Map) JSONpot.get(c);
                pot[c] = new Card(Math.toIntExact((long) JSONcard.get("suit")), Math.toIntExact((long) JSONcard.get("value")));
            }
            
            JSONArray JSONwinners = (JSONArray) test.get("winner");
            int[] winners = new int[JSONwinners.size()];
            int w = 0;
            while (w < JSONwinners.size()) {
                winners[w] = Math.toIntExact((long) JSONwinners.get(w));
                w++;
            }
            
            // Check that the output is correct
            Round round = new Round(players, pot);
            int[] output_winners = round.test(i+1);
            
            w = 0;
            while (w < output_winners.length) {
                if (output_winners[w] == 0) {
                    break;
                } else if (output_winners[w] != winners[w]) {
                    String error = "Error for test" + (i+1) + ": " + output_winners[w] + " does not match with " + winners[w];
                    errors.add(error);
                }
                w++;
            }
        }
    }
    
    /**
     * Writes the test cases to a JSON file for storage. 
     * 
     * Makes it easier than manually typing out the JSON file.
     * 
     * @throws Exception File Not Found exception
     */
    public void writeTestCase() throws Exception {
        // Set up the parser
        JSONParser parser = new JSONParser();
        // Parse the file to an object
        Object obj = parser.parse(new FileReader("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\test_cases.json"));
        // Change the object into a JSON object
        JSONObject json = (JSONObject) obj;
        // Scan the input from the user
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Writing test cases");
        
        System.out.print("\nEnter the number of players: ");

        int num_of_players = scan.nextInt();

        Player[] players = new Player[num_of_players];
        // Start off by creating the players in the round.
        int suit, value;
        Card card1, card2;
        int n = 0;
        while (n < num_of_players) {
            System.out.print("Enter Player" + (n+1) + "'s first card's suit: ");
            suit = scan.nextInt();
            System.out.print("Enter Player" + (n+1) + "'s first card's value: ");
            value = scan.nextInt();
            card1 = new Card(suit, value);
            System.out.print("Enter Player" + (n+1) + "'s second card's suit: ");
            suit = scan.nextInt();
            System.out.print("Enter Player" + (n+1) + "'s second card's value: ");
            value = scan.nextInt();
            card2 = new Card(suit, value);
            players[n] = new Player(card1, card2, n+1);
            n++;
        }

        // Create the pot cards that are in the round
        Card[] pot = new Card[5];

        Card card;
        n = 0;
        while (n < 5) {
            System.out.print("Enter pot card " + (n+1) + "'s suit: ");
            suit = scan.nextInt();
            System.out.print("Enter pot card " + (n+1) + "'s value: ");
            value = scan.nextInt();
            card = new Card(suit, value);
            pot[n] = card;
            n++;
        }

        // Add the players and pot to the JSON object
        JSONObject test = new JSONObject();

        JSONArray JSONplayers = new JSONArray();

        Map JSONcard;
        JSONArray player;

        n = 0;
        while (n < num_of_players) {
            player = new JSONArray();
            JSONcard = new LinkedHashMap(2);
            JSONcard.put("suit", players[n].getCard1().suit);
            JSONcard.put("value", players[n].getCard1().value);
            player.add(JSONcard);

            JSONcard = new LinkedHashMap(2);
            JSONcard.put("suit", players[n].getCard2().suit);
            JSONcard.put("value", players[n].getCard2().value);
            player.add(JSONcard);

            JSONplayers.add(player);
            n++;
        }

        test.put("players", JSONplayers);

        JSONArray JSONpot = new JSONArray();

        n = 0;
        while (n < 5) {
            JSONcard = new LinkedHashMap(2);
            JSONcard.put("suit", pot[n].suit);
            JSONcard.put("value", pot[n].value);
            JSONpot.add(JSONcard);
            n++;
        }

        test.put("pot", JSONpot);
        
        boolean draw_flag = false;
        // Bugged
//        System.out.print("Is the round a draw (y/n): ");
//        String input = scan.nextLine();
//        if (input == "y") {
//            draw_flag = true;
//        } else {
//            draw_flag = false;
//        }
                
        // Get the correct output for the winner of the round
        JSONArray JSONwinners = new JSONArray();
        if (draw_flag) {
            n = 0;
            while (n < num_of_players) {
                System.out.print("Is Player" + (n+1) + " a winner (y/n): ");
                if (scan.nextLine() == "y") {
                    JSONwinners.add(n+1);
                }
                n++;
            }
        } else {
            System.out.print("Enter the winner of the round: ");
            JSONwinners.add(scan.nextInt());
        }
        test.put("winner", JSONwinners);

        JSONArray tests = (JSONArray) json.get("tests");
        if (tests == null) {
            tests = new JSONArray();
        }

        tests.add(test);
        json.put("tests", tests);

        // Write everthing to the file
        PrintWriter pw = new PrintWriter("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\test_cases.json");
        pw.write(json.toJSONString());

        pw.flush();
        pw.close();
    }
    
    /**
     * Will reset the test cases file to empty.
     * 
     * @throws Exception File Not Found exception
     */
    public void resetTestCases() throws Exception {
        JSONObject json = new JSONObject();
        
        PrintWriter pw = new PrintWriter("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\text\\test_cases.json");
        pw.write(json.toJSONString());
        
        pw.flush();
        pw.close();
        
        System.out.println("Test Cases have been reset.");
    }
}
