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

    public void makeDecision(Deck deck) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to swap a card? (yes/no)");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("yes")) {
            System.out.println("Please nominate a card to swap (A-E): ");
            char cardChoice;
            int cardIndex;

            do {
                cardChoice = scanner.nextLine().trim().toUpperCase().charAt(0);
                cardIndex = cardChoice - 'A';
            } while (cardIndex < 0 || cardIndex >= getHand().size());

            Card newCard = deck.dealHand(1).get(0);
            Card removedCard = swapCard(cardIndex, newCard);
            System.out.println("Swapped out " + removedCard + " for " + newCard);
            System.out.println("Updated hand: " + this);
        } else {
            System.out.println("Hand stayed the same: " + this);
        }
    }

    @Override
    public String toString() {
        return name + "'s hand: " + hand;
    }
}