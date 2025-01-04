package test;
import main.Card;
import main.Deck;
import main.Player;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void getHand_returnsCorrectHand() {
        Player player = new Player("Alice");
        List<Card> hand = Arrays.asList(new Card("Hearts", "2"), new Card("Diamonds", "3"));
        player.setHand(hand);
        assertEquals(hand, player.getHand());
    }

    @Test
    void getName_returnsCorrectName() {
        Player player = new Player("Alice");
        assertEquals("Alice", player.getName());
    }

    @Test
    void getPoints_initiallyZero() {
        Player player = new Player("Alice");
        assertEquals(0, player.getPoints());
    }

    @Test
    void addPoint_increasesPoints() {
        Player player = new Player("Alice");
        player.addPoint(1);
        assertEquals(1, player.getPoints());
    }

    @Test
    void getMaxScoreAndSuit_returnsCorrectMaxScoreAndSuit() {
        Player player = new Player("Alice");
        List<Card> hand = Arrays.asList(new Card("Hearts", "2"), new Card("Hearts", "3"), new Card("Diamonds", "4"));
        player.setHand(hand);
        Map.Entry<String, Integer> maxScoreAndSuit = player.getMaxScoreAndSuit();
        assertEquals("Hearts", maxScoreAndSuit.getKey());
        assertEquals(5, maxScoreAndSuit.getValue().intValue());
    }

    @Test
    void makeDecision_doesNotSwapCardWhenNo() {
        Player player = new Player("Alice");
        List<Card> hand = Arrays.asList(new Card("Hearts", "2"), new Card("Diamonds", "3"));
        player.setHand(hand);
        Deck deck = new Deck();
        deck.shuffle();

        ByteArrayInputStream in = new ByteArrayInputStream("no\n".getBytes());
        System.setIn(in);

        Card[] swapInfo = player.makeDecision(deck);
        assertNull(swapInfo[0]);
        assertNull(swapInfo[1]);
    }

    @Test
    void toString_returnsCorrectStringRepresentation() {
        Player player = new Player("Alice");
        List<Card> hand = Arrays.asList(new Card("Hearts", "2"), new Card("Diamonds", "3"));
        player.setHand(hand);
        assertEquals("Alice's hand: [2 of Hearts, 3 of Diamonds]", player.toString());
    }
}
