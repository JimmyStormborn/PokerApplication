package pokerapp;

/**
 * suit: S = 0, C = 1, D = 2, H = 4
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
}
