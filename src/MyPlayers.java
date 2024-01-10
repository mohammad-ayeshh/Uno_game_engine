import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyPlayers extends Players {

    Scanner scanner ;
    private int numOfPlayers;

    public MyPlayers() {
        scanner = new Scanner(System.in);
        initializeThePlayers();
    }

    @Override
    protected void printPlayers(UnoPlayer startingPlayer) {
        if (startingPlayer == null) {
            System.out.println("There are no players in the game.");
            return;
        }

        UnoPlayer current = startingPlayer;

        while (current != null) {
            System.out.println("Player: " + current);

            if (current.getRightPlayer() == null) {
                System.err.println("Warning: Right node is null for player " + current + ". Handle this case appropriately.");
                break;
            }

            // You can also print additional information if needed, e.g., current.getHand().toString()
            current = current.getRightPlayer();

            // Exit the loop if we've come back to the starting player
            if (current == startingPlayer) {
                break;
            }
        }
    }

    @Override
    protected void reversePlayers(UnoPlayer startingPlayer) {
        if (startingPlayer == null || startingPlayer.getRightPlayer() == null) {
            return;
        }

        UnoPlayer current = startingPlayer;
        UnoPlayer next;
        UnoPlayer prev = null;

        // Iterate through the players and update the left and right references
        do {
            next = current.getRightPlayer();
            current.setRightPlayer(prev);
            current.setLeftPlayer(next);
            prev = current;
            current = next;
        } while (current != startingPlayer);

        // Update the starting player (which is now the last player)
        startingPlayer = prev;

        // Update the right reference of the first player to the last player
        UnoPlayer firstPlayer = startingPlayer;
        while (firstPlayer.getRightPlayer() != null) {
            firstPlayer = firstPlayer.getRightPlayer();
        }

        firstPlayer.setRightPlayer(startingPlayer);

    }

    public MyUnoPlayer getPlayer(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index);
        } else {
            return null;
        }
    }

    public List<MyUnoPlayer> getPlayers() {
        return players;
    }

    @Override
    protected void initializeThePlayers() {
        System.out.println("How many players are playing?");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next();
        }

        int numOfPlayers = scanner.nextInt();

        // Validate num of players
        numOfPlayers = getNumOfPlayers(numOfPlayers,10);

        System.out.println("The number of players is: " + numOfPlayers);

        this.players = new ArrayList<>();

        fillThePlayersList(numOfPlayers);
    }

    private void fillThePlayersList(int numOfPlayers) {
        for (int i = 1; i <= numOfPlayers; i++) {
            System.out.print("Enter the name of Player " + i + ": ");
            String playerName = scanner.next();
            MyUnoPlayer player = new MyUnoPlayer(playerName);
            players.add(player);
        }
    }

    private int getNumOfPlayers(int numOfPlayers,int maxNumOfPlayer) {
        while (numOfPlayers < 2 || numOfPlayers > maxNumOfPlayer) {
            System.out.println("Invalid number of players. Please enter a number between 2 and 10.");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }

            numOfPlayers = scanner.nextInt();
        }
        return numOfPlayers;
    }

}