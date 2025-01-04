package test;

import main.Card;
import main.Deck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void constructor_creates52Cards() {
        Deck deck = new Deck();
        assertEquals(52, deck.remainingCards());
    }

    @Test
    void shuffle_randomizesCardOrder() {
        Deck deck = new Deck();
        List<Card> originalOrder = new ArrayList<>(deck.getCards());
        deck.shuffle();
        List<Card> shuffledOrder = deck.getCards();
        assertNotEquals(originalOrder, shuffledOrder);
    }

    @Test
    void dealHand_returnsCorrectNumberOfCards() {
        Deck deck = new Deck();
        List<Card> hand = deck.dealHand(5);
        assertEquals(5, hand.size());
    }

    @Test
    void dealHand_reducesDeckSize() {
        Deck deck = new Deck();
        deck.dealHand(5);
        assertEquals(47, deck.remainingCards());
    }

    @Test
    void dealHand_throwsExceptionWhenNotEnoughCards() {
        Deck deck = new Deck();
        deck.dealHand(52);
        assertThrows(IndexOutOfBoundsException.class, () -> deck.dealHand(1));
    }

    @Test
    void remainingCards_returnsCorrectCountAfterDealing() {
        Deck deck = new Deck();
        deck.dealHand(10);
        assertEquals(42, deck.remainingCards());
    }

    @Test
    void dealHand_returnsEmptyListWhenNoCardsLeft() {
        Deck deck = new Deck();
        deck.dealHand(52);
        List<Card> hand = deck.dealHand(0);
        assertTrue(hand.isEmpty());
    }
}