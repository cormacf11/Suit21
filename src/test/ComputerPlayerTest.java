package test;

import main.Card;
import main.ComputerPlayer;
import main.Deck;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ComputerPlayerTest {
    @Test
    void makeDecision_keepsHandWhenBestSuitIs21() {
        ComputerPlayer player = new ComputerPlayer("Computer");
        List<Card> hand = Arrays.asList(new Card("Hearts", "10"), new Card("Hearts", "11"));
        player.setHand(hand);
        Deck deck = new Deck();
        deck.shuffle();
        Card[] swapInfo = player.makeDecision(deck);
        assertNull(swapInfo[0]);
        assertNull(swapInfo[1]);
    }

    @Test
    void makeDecision_swapsCardWhenBestSuitUnder21() {
        ComputerPlayer player = new ComputerPlayer("Computer");
        List<Card> hand = Arrays.asList(new Card("Hearts", "10"), new Card("Diamonds", "10"));
        player.setHand(hand);
        Deck deck = new Deck();
        deck.shuffle();
        Card[] swapInfo = player.makeDecision(deck);
        assertNotNull(swapInfo[0]);
        assertNotNull(swapInfo[1]);
    }

    @Test
    void calculateSuitScores_returnsCorrectScores() {
        ComputerPlayer player = new ComputerPlayer("Computer");
        List<Card> hand = Arrays.asList(new Card("Hearts", "10"), new Card("Hearts", "5"), new Card("Diamonds", "6"));
        player.setHand(hand);
        Map<String, Integer> suitScores = player.calculateSuitScores();
        assertEquals(15, suitScores.get("Hearts").intValue());
        assertEquals(6, suitScores.get("Diamonds").intValue());
    }

    @Test
    void chooseCardToDiscard_discardsNonTargetSuitCard() {
        ComputerPlayer player = new ComputerPlayer("Computer");
        List<Card> hand = Arrays.asList(new Card("Hearts", "10"), new Card("Diamonds", "5"));
        player.setHand(hand);
        Card cardToDiscard = player.chooseCardToDiscard("Hearts");
        assertEquals("Diamonds", cardToDiscard.getSuit());
    }

    @Test
    void chooseCardToDiscard_discardsLowestValueCardWhenAllInTargetSuit() {
        ComputerPlayer player = new ComputerPlayer("Computer");
        List<Card> hand = Arrays.asList(new Card("Hearts", "10"), new Card("Hearts", "5"));
        player.setHand(hand);
        Card cardToDiscard = player.chooseCardToDiscard("Hearts");
        assertEquals(5, cardToDiscard.getValue());
    }
}
