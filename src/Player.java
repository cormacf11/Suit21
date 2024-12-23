import java.util.*;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public Map.Entry<String, Integer> getMaxScoreAndSuit() {
        Map<String, Integer> suitScores = new HashMap<>();
        for (Card card : hand) {
            int value = card.getValue();
            suitScores.put(card.getSuit(), suitScores.getOrDefault(card.getSuit(), 0) + value);
        }
        return suitScores.entrySet().stream().max(Map.Entry.comparingByValue()).orElse(null);
    }

    @Override
    public String toString() {
        return name + "'s hand: " + hand;
    }
}