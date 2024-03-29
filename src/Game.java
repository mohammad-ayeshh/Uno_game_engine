import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    private static Game instance;
    protected UnoDeck myUnoDeck;
    protected Players myPlayers;
    protected boolean isClockwise = true;
    protected boolean skipApply = false;
    protected int numOfCards4Player;
    protected int numOfCardsDealtPerRound = 1;//developer can override this value in his class

    protected DealingRuleStrategy dealingRuleStrategy;
    protected ScoringStrategy scoringStrategy;

    public void setDealingRuleStrategy(DealingRuleStrategy dealingRuleStrategy) {
        this.dealingRuleStrategy = dealingRuleStrategy;
    }

    public void setScoringStrategy(ScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }

    protected Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = createGameInstance();
        }
        return instance;
    }

    private static Game createGameInstance() {
        // Default implementation
        return new MyUnoGame();
    }

    protected void play() {
        initializeDeck();
        initializeThePlayers();
        setFirstCard();
        dealingCardsToPlayers();
        startRotation();
        determineRoundWinner();
    }

    private UnoPlayer determineRoundWinner() {
        UnoPlayer roundWinner = null;
        int scoreSum = 0;
        int maxScore = Integer.MIN_VALUE;
        //max score is 4 the player with the least amount of cards which is 0
        for (UnoPlayer player : myPlayers.getPlayers()) {
            int playerScore = player.calculateScore(getOpponents(player));
            scoreSum += playerScore;
            if (playerScore > maxScore) {
                roundWinner = player;
            }
        }
        assert roundWinner != null;
        System.out.println("Round Winner: " + roundWinner.getName());
        System.out.println("Round Points: " + scoreSum);
        return roundWinner;
    }

    private List<UnoPlayer> getOpponents(UnoPlayer player) {
        List<UnoPlayer> opponents = new ArrayList<>(myPlayers.getPlayers());
        opponents.remove(player);
        return opponents;
    }

    protected int getNumOfCards() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("please enter the number of cards per player");
        return scanner.nextInt();
    }

    protected void initializeDeck() {
        this.myUnoDeck = new MyUnoDeck();
        System.out.println("Remaining Cards: " + myUnoDeck.getDeckSize());
    }

    protected void initializeThePlayers() {
        myPlayers = new MyPlayers();
        linkPlayers();
    }


    protected void linkPlayers() {
        // Link players in a circular manner (assuming the order in which players are added)
        int playersSize = myPlayers.getPlayersSize();
        for (int i = 0; i < playersSize; i++) {
            UnoPlayer currentPlayer = myPlayers.getPlayer(i);
            UnoPlayer rightPlayer = myPlayers.getPlayer((i + 1) % playersSize);
            UnoPlayer leftPlayer = myPlayers.getPlayer((i - 1 + playersSize) % playersSize);

            currentPlayer.setRightPlayer(rightPlayer);
            currentPlayer.setLeftPlayer(leftPlayer);
        }
    }

    protected void setFirstCard() {
        UnoCard facedUpCard = myUnoDeck.drawCard();
        while (isNotValidFirstActiveCard(facedUpCard))
            facedUpCard = myUnoDeck.drawCard();
        myUnoDeck.setActiveCard(facedUpCard);
        showCurrentActiveCard();
    }

    protected void dealingCardsToPlayers() {
        if (dealingRuleStrategy != null) {
            numOfCards4Player = getNumOfCards();
            dealingRuleStrategy.applyDealingRule(myUnoDeck, myPlayers, numOfCards4Player, numOfCardsDealtPerRound);
        }
    }

    protected abstract void startRotation();

    protected abstract void playerTurn(UnoPlayer player, UnoPlayer nextPlayer);

    protected abstract void handleSpecialCards(UnoCard card, UnoPlayer nextPlayer);

    protected abstract String getUserInputString();

    protected abstract void playWildCard(String colorAsString);

    protected abstract void drawNumOfCards(int numOfCards, UnoPlayer player);

    protected abstract void showCurrentActiveCard();

    protected abstract boolean isNotValidFirstActiveCard(UnoCard firstActiveCard);

    protected abstract boolean isMoveValid(UnoCard playedCard, UnoCard activeCard);

    protected abstract boolean isSpecialActionValid(UnoCard playedCard, UnoCard activeCard);
}