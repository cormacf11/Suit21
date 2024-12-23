import java.util.*;

public class Player {
    private String name;
    private List<Card> hand;
    private int points;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.points = 0;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint(int points) {
        this.points += points;
    }

    public Card swapCard(int index, Card newCard) {
        return hand.set(index, newCard);
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