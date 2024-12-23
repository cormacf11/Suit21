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

        List<List<List<RoundRecord>>> gameHistory = new ArrayList<>();

        // Play the specified number of games
        for (int game = 1; game <= numGames; game++) {
            System.out.println("\n===================");
            System.out.println("  Starting game " + game);
            System.out.println("===================");

            // Create and shuffle the deck
            Deck deck = new Deck();
            deck.shuffle();

            // Deal 5 cards to each player
            for (Player player : players) {
                player.setHand(deck.dealHand(5));
            }

            boolean gameWon = false;
            List<Player> roundWinners = new ArrayList<>();
            List<List<RoundRecord>> roundHistory = new ArrayList<>();
            int roundNumber = 1;

            while (!gameWon && deck.remainingCards() >= players.size()) {
                System.out.println("\n--- Round " + roundNumber + " ---");

                List<RoundRecord> currentRoundRecords = new ArrayList<>();

                for (Player player : players) {
                    System.out.println("\nCurrent Player: " + player.getName());
                    displayPlayerHand(player);
                    System.out.println();
                    Map.Entry<String, Integer> maxSuitScore = player.getMaxScoreAndSuit();
                    System.out.println("Your suit scores:");
                    displaySuitScores(player);
                    System.out.println();

                    String decision = "kept their hand";
                    if (maxSuitScore.getValue() == 21) {
                        System.out.println(player.getName() + " has scored 21 in " + maxSuitScore.getKey() + " and is in contention to win!");
                        roundWinners.add(player);
                    } else {
                        player.makeDecision(deck);
                        decision = "swapped a card";
                    }

                    // Record round data
                    currentRoundRecords.add(new RoundRecord(player.getName(), new ArrayList<>(player.getHand()), maxSuitScore, decision));
                }

                roundHistory.add(currentRoundRecords);

                if (!roundWinners.isEmpty()) {
                    gameWon = true;
                    System.out.println("\nRound completed. Calculating points...");
                    int points = 1 / roundWinners.size();
                    for (Player winner : roundWinners) {
                        winner.addPoint(points);
                        System.out.println(winner.getName() + " awarded " + points + " point(s). Total points: " + winner.getPoints());
                    }
                }

                roundNumber++;
            }

            if (!gameWon) {
                System.out.println("\nNo one scored 21 and there are not enough cards left for another round. Game ends in a draw.");
            }

            gameHistory.add(roundHistory);
        }

        // Display final scores
        System.out.println("\n====================");
        System.out.println("  Final Scores");
        System.out.println("====================");
        players.sort((p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getPoints() + " points");
        }

        // Replay games
        System.out.print("\nWould you like to view a replay of any game? (yes/no): ");
        String replayChoice = scanner.nextLine().trim().toLowerCase();
        while (replayChoice.equals("yes")) {
            System.out.print("Enter the game number (1 to " + numGames + "): ");
            int gameNumber = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (gameNumber >= 1 && gameNumber <= numGames) {
                System.out.println("\n===================");
                System.out.println("  Replay of game " + gameNumber);
                System.out.println("===================");
                replayGame(gameHistory.get(gameNumber - 1));
            } else {
                System.out.println("Invalid game number.");
            }

            System.out.print("\nWould you like to view another replay? (yes/no): ");
            replayChoice = scanner.nextLine().trim().toLowerCase();
        }

        System.out.println("\nThank you for playing Suit 21!");
    }

    private static void displayPlayerHand(Player player) {
        List<Card> hand = player.getHand();
        System.out.println("\nYour current hand:");
        char label = 'A';
        for (Card card : hand) {
            System.out.println(label + ": " + card);
            label++;
        }
    }

    private static void displaySuitScores(Player player) {
        Map<String, Integer> suitScores = calculateSuitScores(player);
        suitScores.forEach((suit, score) -> System.out.println(suit + ": " + score));
    }

    private static Map<String, Integer> calculateSuitScores(Player player) {
        Map<String, Integer> suitScores = new HashMap<>();
        for (Card card : player.getHand()) {
            suitScores.put(card.getSuit(), suitScores.getOrDefault(card.getSuit(), 0) + card.getValue());
        }
        return suitScores;
    }

    private static void replayGame(List<List<RoundRecord>> roundHistory) {
        int roundNumber = 1;
        Map<String, Integer> playerPoints = new HashMap<>();

        // Replay each round
        for (List<RoundRecord> roundRecords : roundHistory) {
            System.out.println("\n--- Round " + roundNumber + " ---");
            for (RoundRecord record : roundRecords) {
                System.out.println("\nCurrent Player: " + record.getPlayerName());
                displayReplayHand(record.getHand());
                System.out.println();
                System.out.println("Your suit scores:");
                displayReplaySuitScores(record.getHand());
                System.out.println();
                System.out.println("Decision: " + record.getDecision());

                // Track the points for potential winners
                if (!playerPoints.containsKey(record.getPlayerName())) {
                    playerPoints.put(record.getPlayerName(), 0);
                }
                if (record.getMaxSuitScore() != null && record.getMaxSuitScore().getValue() == 21) {
                    playerPoints.put(record.getPlayerName(), playerPoints.get(record.getPlayerName()) + 1);
                }
            }
            roundNumber++;
        }

        // Determine and display the winner(s)
        int maxPoints = playerPoints.values().stream().max(Integer::compare).orElse(0);
        List<String> winners = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : playerPoints.entrySet()) {
            if (entry.getValue() == maxPoints) {
                winners.add(entry.getKey());
            }
        }

        System.out.println("\n===================");
        System.out.println("  Game Winner(s)");
        System.out.println("===================");
        for (String winner : winners) {
            System.out.println(winner + " with " + maxPoints + " point(s)");
        }
    }

    private static void displayReplayHand(List<Card> hand) {
        System.out.println("\nHand:");
        char label = 'A';
        for (Card card : hand) {
            System.out.println(label + ": " + card);
            label++;
        }
    }

    private static void displayReplaySuitScores(List<Card> hand) {
        Map<String, Integer> suitScores = calculateReplaySuitScores(hand);
        suitScores.forEach((suit, score) -> System.out.println(suit + ": " + score));
    }

    private static Map<String, Integer> calculateReplaySuitScores(List<Card> hand) {
        Map<String, Integer> suitScores = new HashMap<>();
        for (Card card : hand) {
            suitScores.put(card.getSuit(), suitScores.getOrDefault(card.getSuit(), 0) + card.getValue());
        }
        return suitScores;
    }
}