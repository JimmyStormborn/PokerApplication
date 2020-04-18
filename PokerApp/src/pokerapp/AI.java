package pokerapp;

/**
 * Calculates what the computer player should do.
 * 
 * aiType = 0 is simpleAI
 *
 * @author James Bird-Sycamore
 * Last Updated: 18/04/2020
 */
public class AI {
    
    int aiType; // The ai used for the computer player.
    
    /**
     * Constructor: Creates the AI object
     * 
     * @param aiType The AI being used by the computer player. 
     */
    public AI (int aiType) {
        this.aiType = aiType;
    }
    
    /**
     * Figures out which AI the computer is using, and then runs the AI.
     * 
     * @return The input for the computer's move. 
     */
    public String runAI () {
        if (aiType == 0) {
            return simpleAI();
        } else {
            System.err.println("AI not setup correctly");
            return null;
        }
    }
    
    /**
     * Simple AI, always checks or calls.
     * 
     * @return The input for the computer's move. 
     */
    private String simpleAI () {
        String output;
        output = "c";
        return output;
    }
    
}
