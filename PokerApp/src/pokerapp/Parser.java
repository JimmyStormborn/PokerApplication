package pokerapp;

/**
 * suit: S = 0, C = 1, D = 2, H = 3
 * value: J = 11, Q = 12, K = 13, A = 14
 * 
 * Hand values: high-card=0, pair=1, two-pair=2, three-of-a-kind=3,
 * straight=4, flush=5, full-house=6, four-of-a-kind=7, straight-flush=8,
 * royal-flush=9
 *
 * @author James Bird-Sycamore
 * @date 28/03/2020
 */
public class Parser {
    
    /**
     * Constructor: Creates the parser object.
     */
    public Parser() {}
    
    /**
     * Converts an array of Card objects into a readable string.
     * 
     * @param cards The cards being converted in string.
     * @return The String of cards in a readable format.
     */
    public String cardsToString(Card[] cards) {
        String string_cards = "";
        for(Card card : cards) {
            if (card != null) {
                // Value
                switch (card.value) {
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
                        string_cards += Integer.toString(card.value);
                        break;
                }

                // Suit
                switch (card.suit) {
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
        }
        return string_cards;
    }
    
    /**
     * Converts a card into a readable String format.
     * Converts Card object into array and sends to cardsToString function.
     * 
     * @param card The card that is being converted.
     * @return The String of the card in a readable format.
     */
    public String cardToString(Card card) {
        Card[] card_array = new Card[1];
        card_array[0] = card;
        
        return cardsToString(card_array);
    }
    
    /**
     * Converts the hand value into a readable String format.
     * 
     * @param hand The hand that is being converted.
     * @return The String of the converted hand in a readable format. 
     */
    public String handToString(int[] hand) {
        String str_hand = "";
        
        if (hand[0] == 4 || hand[0] == 5 || hand[0] == 8 || hand[0] == 9) {
            switch (hand[1]) {
                    case 6:
                        str_hand = "Six high ";
                        break;
                    case 7:
                        str_hand = "Seven high ";
                        break;
                    case 8:
                        str_hand = "Eight high ";
                        break;
                    case 9:
                        str_hand = "Nine high ";
                        break;
                    case 10:
                        str_hand = "Ten high ";
                        break;
                    case 11:
                        str_hand = "Jack high ";
                        break;
                    case 12:
                        str_hand = "Queen high ";
                        break;
                    case 13:
                        str_hand = "King high ";
                        break;
                    case 14:
                        str_hand = "Ace high ";
                        break;
                }
        }
        
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
            case 4:
                str_hand += "straight";
                break;
            case 5:
                str_hand += "flush";
                break;
            case 6:
                str_hand = "Full house";
                break;
            case 7:
                str_hand = "Four of a Kind";
                break;
            case 8:
                str_hand += "straight flush";
                break;
            case 9:
                str_hand += "royal flush";
                break;
        }
        
        if (hand[0] != 5 && hand[0] != 4 && hand[0] != 8 && hand[0] != 9) {
            switch (hand[1]) {
                case 2:
                    str_hand += " of twos";
                    break;
                case 3:
                    str_hand += " of threes";
                    break;
                case 4:
                    str_hand += " of fours";
                    break;
                case 5:
                    str_hand += " of fives";
                    break;
                case 6:
                    str_hand += " of sixes";
                    break;
                case 7:
                    str_hand += " of sevens";
                    break;
                case 8:
                    str_hand += " of eights";
                    break;
                case 9:
                    str_hand += " of nines";
                    break;
                case 10:
                    str_hand += " of tens";
                    break;
                case 11:
                    str_hand += " of jacks";
                    break;
                case 12:
                    str_hand += " of queens";
                    break;
                case 13:
                    str_hand += " of kings";
                    break;
                case 14:
                    str_hand += " of aces";
                    break;
            }
            if (hand[2] != 0) {
                switch (hand[2]) {
                    case 2:
                        str_hand += " and twos";
                        break;
                    case 3:
                        str_hand += " and threes";
                        break;
                    case 4:
                        str_hand += " and fours";
                        break;
                    case 5:
                        str_hand += " and fives";
                        break;
                    case 6:
                        str_hand += " and sixes";
                        break;
                    case 7:
                        str_hand += " and sevens";
                        break;
                    case 8:
                        str_hand += " and eights";
                        break;
                    case 9:
                        str_hand += " and nines";
                        break;
                    case 10:
                        str_hand += " and tens";
                        break;
                    case 11:
                        str_hand += " and jacks";
                        break;
                    case 12:
                        str_hand += " and queens";
                        break;
                    case 13:
                        str_hand += " and kings";
                        break;
                    case 14:
                        str_hand += " and aces";
                        break;
                }
            }
        }
        return str_hand;
    }
}
