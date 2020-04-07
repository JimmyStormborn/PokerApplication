package pokerapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.io.FileReader; 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator; 
import java.util.LinkedHashMap;
import java.util.Map; 
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
  
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*;

/**
 *
 * @author James Bird-Sycamore
 * @date 22/03/2020
 */
public class Test {
    
    private ArrayList<String> errors = new ArrayList<String>();
    
    private int winner;
    private boolean draw;
    private int[] draw_players;
    
    public Test() {}
    
    public void printErrors() {
        if (errors.size() > 0) {
            System.err.println("List of errors:");
            for (String error : errors) {
                System.err.println(error);
            }
        } else {
            System.err.println("Congrats! There are no errors.");
        }
    }
    
    public void checkForDoubles(Player[] players, Card[] pot) {
        Card[] cards = new Card[players.length*2 + pot.length];
        int n = 0;
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
        // Set up the parser
        JSONParser parser = new JSONParser();
        // Parse the file to an object
        Object obj = parser.parse(new FileReader("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\test_cases.json"));
        // Change the object into a JSON object
        JSONObject json = (JSONObject) obj;
        
        JSONArray tests = (JSONArray) json.get("tests");
        
        if (tests == null) {
            System.err.println("There are no test cases.");
            return;
        }
        
        for (int i = 0; i < tests.size(); i++) {
            JSONObject test = (JSONObject) tests.get(i);
            JSONArray players = (JSONArray) test.get("players");
            JSONArray pot = (JSONArray) test.get("pot");
            
            JSONArray player;
            Map card;
            for (int p = 0; p < players.size(); p++) {
                player = (JSONArray) players.get(p);
                int n = 0;
                while (n < 2) {
                    card = (Map) player.get(n);
                    System.out.println("suit " + card.get("suit") + " value: " + card.get("value"));
                    n++;
                }
            }
            for (int c = 0; c < pot.size(); c++) {
                card = (Map) pot.get(c);
                System.out.println("suit " + card.get("suit") + " value: " + card.get("value"));
            }
        }
    }
    
    /**
     * Writes the test cases. 
     * 
     * Makes it easier than manually typing out the json file.
     * 
     * @throws Exception File Not Found exception
     */
    public void writeTestCase() throws Exception {
        // Set up the parser
        JSONParser parser = new JSONParser();
        // Parse the file to an object
        Object obj = parser.parse(new FileReader("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\test_cases.json"));
        // Change the object into a JSON object
        JSONObject json = (JSONObject) obj;
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Writing test cases");
        
        System.out.print("\nEnter the number of players: ");

        int num_of_players = scan.nextInt();

        Player[] players = new Player[num_of_players];

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

        JSONObject test = new JSONObject();

        JSONArray JSONplayers = new JSONArray();

        Map JSONcard;
        JSONArray player;

        player = new JSONArray();
        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", players[0].getCard1().suit);
        JSONcard.put("value", players[0].getCard1().value);
        player.add(JSONcard);

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", players[0].getCard2().suit);
        JSONcard.put("value", players[0].getCard2().value);
        player.add(JSONcard);

        JSONplayers.add(player);

        player = new JSONArray();
        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", players[1].getCard1().suit);
        JSONcard.put("value", players[1].getCard1().value);
        player.add(JSONcard);

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", players[1].getCard2().suit);
        JSONcard.put("value", players[1].getCard2().value);
        player.add(JSONcard);

        JSONplayers.add(player);

        player = new JSONArray();
        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", players[2].getCard1().suit);
        JSONcard.put("value", players[2].getCard1().value);
        player.add(JSONcard);

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", players[2].getCard2().suit);
        JSONcard.put("value", players[2].getCard2().value);
        player.add(JSONcard);

        JSONplayers.add(player);

        test.put("players", JSONplayers);

        JSONArray JSONpot = new JSONArray();

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", pot[0].suit);
        JSONcard.put("value", pot[0].value);
        JSONpot.add(JSONcard);

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", pot[1].suit);
        JSONcard.put("value", pot[1].value);
        JSONpot.add(JSONcard);

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", pot[2].suit);
        JSONcard.put("value", pot[2].value);
        JSONpot.add(JSONcard);

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", pot[3].suit);
        JSONcard.put("value", pot[3].value);
        JSONpot.add(JSONcard);

        JSONcard = new LinkedHashMap(2);
        JSONcard.put("suit", pot[4].suit);
        JSONcard.put("value", pot[4].value);
        JSONpot.add(JSONcard);

        test.put("pot", JSONpot);

        JSONArray tests = (JSONArray) json.get("tests");
        if (tests == null) {
            tests = new JSONArray();
        }

        tests.add(test);
        json.put("tests", tests);

        PrintWriter pw = new PrintWriter("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\test_cases.json");
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
        
        PrintWriter pw = new PrintWriter("C:\\Users\\kylar\\Documents\\PokerApplication\\PokerApp\\src\\pokerapp\\test_cases.json");
        pw.write(json.toJSONString());
        
        pw.flush();
        pw.close();
        
        System.out.println("Test Cases have been reset.");
    }
}
