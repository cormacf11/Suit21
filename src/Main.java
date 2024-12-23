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
            if (name.equalsIgnoreCase("Computer")) {
                players.add(new ComputerPlayer(name));
            } else {
                players.add(new Player(name));
            }
        }

        // Input number of games
        System.out.print("Enter the number of games to play: ");
        int numGames = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        List<List<RoundRecord>> gameHistory = new ArrayList<>();

        // Play the specified number of games
        for (int game = 1; game <= numGames; game++) {
            System.out.println("\nStarting game " + game + "...");

            // Create and shuffle the deck
            Deck deck = new Deck();
            deck.shuffle();

            // Deal 5 cards to each player
            for (Player player : players) {
                player.setHand(deck.dealHand(5));
            }

            boolean gameWon = false;
            List<Player> roundWinners = new ArrayList<>();
            List<RoundRecord> roundHistory = new ArrayList<>();

            while (!gameWon && deck.remainingCards() >= players.size()) {
                for (Player player : players) {
                    System.out.println(player);
                    Map.Entry<String, Integer> maxSuitScore = player.getMaxScoreAndSuit();
                    System.out.println("Maximum score from a single suit: " + maxSuitScore.getValue() + " (Suit: " + maxSuitScore.getKey() + ")");

                    String decision = "kept their hand";
                    if (maxSuitScore.getValue() == 21) {
                        System.out.println(player.getName() + " has scored 21 in " + maxSuitScore.getKey() + " and is in contention to win!" + "\n");
                        roundWinners.add(player);
                    } else {
                        player.makeDecision(deck);
                        decision = "swapped a card";
                    }

                    // Record round data
                    roundHistory.add(new RoundRecord(player.getName(), new ArrayList<>(player.getHand()), maxSuitScore, decision));
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

            gameHistory.add(roundHistory);
        }

        // Display final scores
        System.out.println("\nFinal Scores:");
        players.sort((p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getPoints() + " points");
        }

        // Replay games
        System.out.print("Would you like to view a replay of any game? (yes/no): ");
        String replayChoice = scanner.nextLine().trim().toLowerCase();
        while (replayChoice.equals("yes")) {
            System.out.print("Enter the game number (1 to " + numGames + "): ");
            int gameNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (gameNumber >= 1 && gameNumber <= numGames) {
                System.out.println("\nReplay of game " + gameNumber + ":");
                List<RoundRecord> roundHistory = gameHistory.get(gameNumber - 1);
                for (RoundRecord record : roundHistory) {
                    System.out.println(record);
                }
            } else {
                System.out.println("Invalid game number.");
            }

            System.out.print("Would you like to view another replay? (yes/no): ");
            replayChoice = scanner.nextLine().trim().toLowerCase();
        }

        System.out.println("Thank you for playing Suit 21!");
    }
}