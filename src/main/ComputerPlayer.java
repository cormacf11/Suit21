package main;

import java.util.*;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name) {
        super(name);
    }

    @Override
    public Card[] makeDecision(Deck deck) {
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
                    + " (no suits <= 21 or no best suit found)");
            return new Card[]{null, null};
        }

        // Check if best suit is already 21
        if (bestScore == 21) {
            System.out.println(getName() + " keeps the hand: " + this
                    + " (already has 21 in " + bestSuit + ")");
            return new Card[]{null, null};
        }

        // Decide which card to discard
        Card cardToDiscard = chooseCardToDiscard(bestSuit);

        // Perform the swap (if a card was chosen)
        if (cardToDiscard != null) {
            int index = getHand().indexOf(cardToDiscard);
            Card newCard = deck.dealHand(1).get(0);
            Card removedCard = swapCard(index, newCard);

            // Announce the swap in gameplay
            System.out.println(getName() + " swapped out " + removedCard
                    + " for " + newCard);

            // Return these cards for logging in RoundRecord
            return new Card[]{removedCard, newCard};
        } else {
            // If we couldn’t find a decent card to discard, just keep the hand
            System.out.println(getName() + " decided to keep the hand: " + this + "\n");
            return new Card[]{null, null};
        }
    }

    public Map<String, Integer> calculateSuitScores() {
        Map<String, Integer> suitScores = new HashMap<>();
        for (Card card : getHand()) {
            suitScores.put(
                    card.getSuit(),
                    suitScores.getOrDefault(card.getSuit(), 0) + card.getValue()
            );
        }
        return suitScores;
    }

    public Card chooseCardToDiscard(String targetSuit) {
        List<Card> hand = getHand();

        // First, try to discard a card that isn’t in the target suit
        for (Card card : hand) {
            if (!card.getSuit().equalsIgnoreCase(targetSuit)) {
                return card;  // prime discard candidate
            }
        }

        // If all cards are in the target suit, discard the lowest-value card
        Card lowestCard = hand.get(0);
        for (Card card : hand) {
            if (card.getValue() < lowestCard.getValue()) {
                lowestCard = card;
            }
        }
        return lowestCard;
    }
}
