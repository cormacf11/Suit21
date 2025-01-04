package test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import main.Card;

class CardTest {

    @Test
    void getSuit_returnsCorrectSuit() {
        Card card = new Card("Hearts", "Ace");
        assertEquals("Hearts", card.getSuit());
    }

    @Test
    void getValue_returns11ForAce() {
        Card card = new Card("Hearts", "Ace");
        assertEquals(11, card.getValue());
    }

    @Test
    void getValue_returns10ForKing() {
        Card card = new Card("Hearts", "King");
        assertEquals(10, card.getValue());
    }

    @Test
    void getValue_returns10ForQueen() {
        Card card = new Card("Hearts", "Queen");
        assertEquals(10, card.getValue());
    }

    @Test
    void getValue_returns10ForJack() {
        Card card = new Card("Hearts", "Jack");
        assertEquals(10, card.getValue());
    }

    @Test
    void getValue_returnsNumericValueForNumberCard() {
        Card card = new Card("Hearts", "7");
        assertEquals(7, card.getValue());
    }

    @Test
    void getValue_throwsNumberFormatExceptionForInvalidNumber() {
        Card card = new Card("Hearts", "Invalid");
        assertThrows(NumberFormatException.class, card::getValue);
    }

    @Test
    void toString_returnsCorrectStringRepresentation() {
        Card card = new Card("Hearts", "Ace");
        assertEquals("Ace of Hearts", card.toString());
    }
}