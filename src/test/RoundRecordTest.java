package test;

import main.Card;
import main.RoundRecord;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class RoundRecordTest {

    @Test
    void getPlayerName_returnsCorrectName() {
        RoundRecord record = new RoundRecord("Alice", new ArrayList<>(), null, null, null);
        assertEquals("Alice", record.getPlayerName());
    }

    @Test
    void getHand_returnsCorrectHand() {
        List<Card> hand = Arrays.asList(new Card("Hearts", "2"), new Card("Diamonds", "3"));
        RoundRecord record = new RoundRecord("Alice", hand, null, null, null);
        assertEquals(hand, record.getHand());
    }

    @Test
    void getMaxSuitScore_returnsCorrectMaxSuitScore() {
        Map.Entry<String, Integer> maxSuitScore = new AbstractMap.SimpleEntry<>("Hearts", 5);
        RoundRecord record = new RoundRecord("Alice", new ArrayList<>(), maxSuitScore, null, null);
        assertEquals(maxSuitScore, record.getMaxSuitScore());
    }

    @Test
    void getRemovedCard_returnsCorrectRemovedCard() {
        Card removedCard = new Card("Hearts", "2");
        RoundRecord record = new RoundRecord("Alice", new ArrayList<>(), null, removedCard, null);
        assertEquals(removedCard, record.getRemovedCard());
    }

    @Test
    void getNewCard_returnsCorrectNewCard() {
        Card newCard = new Card("Diamonds", "3");
        RoundRecord record = new RoundRecord("Alice", new ArrayList<>(), null, null, newCard);
        assertEquals(newCard, record.getNewCard());
    }

    @Test
    void toString_returnsCorrectStringRepresentation_noSwap() {
        List<Card> hand = Arrays.asList(new Card("Hearts", "2"), new Card("Diamonds", "3"));
        Map.Entry<String, Integer> maxSuitScore = new AbstractMap.SimpleEntry<>("Hearts", 5);
        RoundRecord record = new RoundRecord("Alice", hand, maxSuitScore, null, null);
        assertEquals("Alice's hand: [2 of Hearts, 3 of Diamonds] | Maximum score: 5 (Hearts) | No swap", record.toString());
    }

    @Test
    void toString_returnsCorrectStringRepresentation_withSwap() {
        List<Card> hand = Arrays.asList(new Card("Hearts", "2"), new Card("Diamonds", "3"));
        Map.Entry<String, Integer> maxSuitScore = new AbstractMap.SimpleEntry<>("Hearts", 5);
        Card removedCard = new Card("Hearts", "2");
        Card newCard = new Card("Clubs", "4");
        RoundRecord record = new RoundRecord("Alice", hand, maxSuitScore, removedCard, newCard);
        assertEquals("Alice's hand: [2 of Hearts, 3 of Diamonds] | Maximum score: 5 (Hearts) | Swapped out 2 of Hearts for 4 of Clubs", record.toString());
    }
}
