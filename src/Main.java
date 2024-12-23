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

        boolean gameWon = false;
        List<Player> roundWinners = new ArrayList<>();
        while (!gameWon && deck.remainingCards() >= players.size()) {
            for (Player player : players) {
                System.out.println(player);
                Map.Entry<String, Integer> maxSuitScore = player.getMaxScoreAndSuit();
                System.out.println("Maximum score from a single suit: " + maxSuitScore.getValue() + " (Suit: " + maxSuitScore.getKey() + ")");

                if (maxSuitScore.getValue() == 21) {
                    System.out.println(player.getName() + " has scored 21 in " + maxSuitScore.getKey() + " and is in contention to win!");
                    roundWinners.add(player);
                } else {
                    System.out.println("Would you like to swap a card? (yes/no)");
                    String choice = scanner.nextLine().trim().toLowerCase();

                    if (choice.equals("yes")) {
                        System.out.println("Enter the index (1-5) of the card to swap: ");
                        int cardIndex;
                        do {
                            cardIndex = scanner.nextInt() - 1; // Convert to zero-based index
                            scanner.nextLine(); // Consume newline
                        } while (cardIndex < 0 || cardIndex >= player.getHand().size());

                        Card newCard = deck.dealHand(1).get(0);
                        Card removedCard = player.swapCard(cardIndex, newCard);
                        System.out.println("Swapped out " + removedCard + " for " + newCard);
                        System.out.println("Updated hand: " + player);
                    } else {
                        System.out.println("Hand stayed the same: " + player);
                    }
                }
            }

            if (!roundWinners.isEmpty()) {
                gameWon = true;
                System.out.println("Round completed. Calculating points...");
                int points = 1 / roundWinners.size();
                for (Player winner : roundWinners) {
                    winner.addPoint(points);
                    System.out.println(winner.getName() + " awarded " + points + " point(s). Total points: " + winner.getPoints());
                }
            }
        }

        if (!gameWon) {
            System.out.println("No one scored 21 and there are not enough cards left for another round. Game ends in a draw.");
        }
    }
}