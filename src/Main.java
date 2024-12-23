import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of players
        int numPlayers;
        do {
            System.out.print("Enter the number of players (2-6): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } while (numPlayers < 2 || numPlayers > 6);

        // Input player names
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }

        // Create and shuffle the deck
        Deck deck = new Deck();
        deck.shuffle();

        // Deal 5 cards to each player
        for (Player player : players) {
            player.setHand(deck.dealHand(5));
        }

        // Display each player's hand and maximum score from a single suit
        for (Player player : players) {
            System.out.println(player);
            Map.Entry<String, Integer> maxSuitScore = player.getMaxScoreAndSuit();
            System.out.println("Maximum score from a single suit: " + maxSuitScore.getValue() + " (Suit: " + maxSuitScore.getKey() + ")");
        }
    }
}