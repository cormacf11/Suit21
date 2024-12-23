import java.util.*;

public class RoundRecord {
    private String playerName;
    private List<Card> hand;
    private Map.Entry<String, Integer> maxSuitScore;
    private String decision;

    public RoundRecord(String playerName, List<Card> hand, Map.Entry<String, Integer> maxSuitScore, String decision) {
        this.playerName = playerName;
        this.hand = hand;
        this.maxSuitScore = maxSuitScore;
        this.decision = decision;
    }

    @Override
    public String toString() {
        return playerName + "'s hand: " + hand + " | Maximum score: " + maxSuitScore.getValue() + " (" + maxSuitScore.getKey() + ") | Decision: " + decision;
    }
}
