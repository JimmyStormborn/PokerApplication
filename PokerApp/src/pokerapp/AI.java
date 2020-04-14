package pokerapp;

/**
 * Calculates what the computer player should do.
 * 
 * aiType = 0 is simpleAI
 *
 * @author James Bird-Sycamore
 * Last Updated: 14/04/2020
 */
public class AI {
    
    int aiType;
    
    public AI (int aiType) {
        this.aiType = aiType;
    }
    
    public String runAI () {
        String output = "";
        if (aiType == 0) {
            return simpleAI();
        } else {
            System.err.println("AI not setup correctly");
            return null;
        }
    }
    
    public String simpleAI () {
        String output;
        output = "c";
        return output;
    }
    
}
