import java.util.*;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name) {
        super(name);
    }

    @Override
    public void makeDecision(Deck deck) {
        // Calculate suit totals
        Map<String, Integer> suitScores = calculateSuitScores();

        // Identify best suit (closest to 21 without going over)
        String bestSuit = null;
        int bestScore = -1;
        for (Map.Entry<String, Integer> entry : suitScores.entrySet()) {
            int score = entry.getValue();
            // We favour the highest score that is <= 21
            if (score > bestScore && score <= 21) {
                bestScore = score;
                bestSuit = entry.getKey();
            }
        }

        // If for some reason no suit is <= 21, just keep the hand
        if (bestSuit == null) {
            System.out.println(getName() + " keeps the hand: " + this
                    + " (all suits exceed 21 or no best suit found)");
            return;
        }

        // Check if best suit is already 21
        if (bestScore == 21) {
            System.out.println(getName() + " decides to keep the hand: " + this
                    + " (already has 21 in " + bestSuit + ")");
            return;
        }

        // Decide which card to discard
        Card cardToDiscard = chooseCardToDiscard(bestSuit);

        // Perform the swap (if a card was chosen to discard)
        if (cardToDiscard != null) {
            int index = getHand().indexOf(cardToDiscard);
            Card newCard = deck.dealHand(1).get(0);
            Card removedCard = swapCard(index, newCard);
            System.out.println(getName() + " swapped out " + removedCard
                    + " for " + newCard + "\n");
        } else {
            // If we couldn't find a decent card to discard, we’ll just keep the hand
            System.out.println(getName() + " decided to keep the hand: " + this + "\n");
        }
    }

    /**
     * Calculates the total value per suit for the current hand.
     */
    private Map<String, Integer> calculateSuitScores() {
        Map<String, Integer> suitScores = new HashMap<>();
        for (Card card : getHand()) {
            suitScores.put(
                    card.getSuit(),
                    suitScores.getOrDefault(card.getSuit(), 0) + card.getValue()
            );
        }
        return suitScores;
    }

    /**
     * Chooses a card to discard based on which suit we are targeting.
     *
     * @param targetSuit The suit we want to strengthen.
     * @return The card we aim to discard, or null if we don’t want to discard.
     */
    private Card chooseCardToDiscard(String targetSuit) {
        List<Card> hand = getHand();

        // First, try to discard a card that isn’t in the target suit
        for (Card card : hand) {
            if (!card.getSuit().equalsIgnoreCase(targetSuit)) {
                return card;  // This is a prime discard candidate
            }
        }

        // If all cards are in the target suit, optionally discard the lowest-value card
        // so that we might draw a higher-value one.
        // This is a risk because you might end up drawing something in the wrong suit,
        // but it’s a strategy if your hand is full of the same suit.
        Card lowestCard = hand.get(0);
        for (Card card : hand) {
            if (card.getValue() < lowestCard.getValue()) {
                lowestCard = card;
            }
        }
        return lowestCard;
    }
}
