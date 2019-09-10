package pokerapp;

/**
 * suit: S = 0, C = 1, D = 2, H = 3
 * value: J = 11, Q = 12, K = 13, A = 14
 *
 * @author TheBeast
 */
public class Parser {
    
    public Parser() {
        
    }
    
    public String cardsToString(int[][] cards) {
        String string_cards = "";
        for(int[] card : cards) {
            // Value
            switch (card[1]) {
                case 11:
                    string_cards += "J";
                    break;
                case 12:
                    string_cards += "Q";
                    break;
                case 13:
                    string_cards += "K";
                    break;
                case 14:
                    string_cards += "A";
                    break;
                default:
                    string_cards += Integer.toString(card[1]);
                    break;
            }
            
            // Suit
            switch (card[0]) {
                case 0:
                    string_cards += "S";
                    break;
                case 1:
                    string_cards += "C";
                    break;
                case 2:
                    string_cards += "D";
                    break;
                case 3:
                    string_cards += "H";
                    break;
            }
            
            string_cards += " ";
        }
        return string_cards;
    }
    
    public String cardToString(int[] card) {
        String string_cards = "";
        
        // Value
        switch (card[1]) {
            case 11:
                string_cards += "J";
                break;
            case 12:
                string_cards += "Q";
                break;
            case 13:
                string_cards += "K";
                break;
            case 14:
                string_cards += "A";
                break;
            default:
                string_cards += Integer.toString(card[1]);
                break;
        }

        // Suit
        switch (card[0]) {
            case 0:
                string_cards += "S";
                break;
            case 1:
                string_cards += "C";
                break;
            case 2:
                string_cards += "D";
                break;
            case 3:
                string_cards += "H";
                break;
        }

        string_cards += " ";
            
        return string_cards;
    }
    
    public String handToString(int[] hand) {
        String str_hand = "";
        
        switch (hand[0]) {
            case 0:
                str_hand = "High card";
                break;
            case 1:
                str_hand = "Pair";
                break;
            case 2:
                str_hand = "Two Pair";
                break;
            case 3:
                str_hand = "Three of a Kind";
                break;
            case 5:
                str_hand = "Flush";
                break;
            case 7:
                str_hand = "Four of a Kind";
                break;
        }
        
        return str_hand;
    }
}
