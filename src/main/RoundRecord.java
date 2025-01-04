package main;

import java.util.*;

public class RoundRecord {
    private String playerName;
    private List<Card> hand;
    private Map.Entry<String, Integer> maxSuitScore;
    private Card removedCard;
    private Card newCard;

    public RoundRecord(
            String playerName,
            List<Card> hand,
            Map.Entry<String, Integer> maxSuitScore,
            Card removedCard,
            Card newCard
    ) {
        this.playerName = playerName;
        this.hand = hand;
        this.maxSuitScore = maxSuitScore;
        this.removedCard = removedCard;
        this.newCard = newCard;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Map.Entry<String, Integer> getMaxSuitScore() {
        return maxSuitScore;
    }

    public Card getRemovedCard() {
        return removedCard;
    }

    public Card getNewCard() {
        return newCard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(playerName).append("'s hand: ").append(hand);
        sb.append(" | Maximum score: ").append(maxSuitScore.getValue())
                .append(" (").append(maxSuitScore.getKey()).append(")");

        if (removedCard != null && newCard != null) {
            sb.append(" | Swapped out ").append(removedCard)
                    .append(" for ").append(newCard);
        } else {
            sb.append(" | No swap");
        }
        return sb.toString();
    }
}
