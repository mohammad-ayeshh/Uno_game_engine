import java.util.Scanner;

public class MyUnoGame extends Game {

    private static final Scanner scanner = new Scanner(System.in);
    //main methods

    @Override
    protected void startRotation() {
        UnoPlayer currentPlayer = myPlayers.getPlayer(0);
        UnoPlayer nextPlayer = currentPlayer.getRightPlayer();
        UnoPlayer prevPlayer = currentPlayer.getLeftPlayer();

        while (true) {

            playerTurn(currentPlayer, nextPlayer);
            showCurrentActiveCard();

            if (isClockwise) {
                nextPlayer = currentPlayer.getRightPlayer();
                prevPlayer = currentPlayer.getLeftPlayer();
                if (skipApply) {
                    nextPlayer = currentPlayer.getRightPlayer().getRightPlayer();
                    skipApply = !skipApply;
                }
            } else {
                nextPlayer = currentPlayer.getLeftPlayer();
                prevPlayer = currentPlayer.getRightPlayer();
                if (skipApply) {
                    nextPlayer = currentPlayer.getLeftPlayer().getLeftPlayer();
                    skipApply = !skipApply;
                }
            }
            if (currentPlayer.getHand().getHandSize() == 1)
                System.out.println(currentPlayer.getName() + ": UNOOOO00O");
            if (currentPlayer.getHand().getHandSize() < 1)
                break;
            currentPlayer = nextPlayer;
        }
    }

    //helper methods
    @Override
    protected void playerTurn(UnoPlayer player, UnoPlayer nextPlayer) {
        System.out.println(player.getName() + "'s Turn");
        printPlayerHand(player);
        boolean playedRightMove = false;
        if (player.getHand().isPlayable(myUnoDeck.getActiveCard())) {
            while (!playedRightMove) {
                playedRightMove = hasPlayedRightMove(player, nextPlayer);
            }
        } else {
            handleNonPlayableHand(player);
        }
    }

    protected void handleNonPlayableHand(UnoPlayer player) {
        UnoCard newCard = myUnoDeck.drawCard();
        player.addCard(newCard);
        System.out.println("Your hand is not playable so we draw you a " + newCard + " card");
        printPlayerHand(player);
    }

    protected boolean hasPlayedRightMove(UnoPlayer player, UnoPlayer nextPlayer) {
        int cardIndex = getCardIndex();
        cardIndex--;
        if (!isIndexValid(player, cardIndex)) return false;
        UnoCard playedCard = player.playCard(cardIndex);
        return isMoveValid(playedCard, myUnoDeck.getActiveCard()) ? handleValidMove(player, nextPlayer, playedCard) : handleNonValidMove(player, playedCard);
    }

    protected boolean handleNonValidMove(UnoPlayer player, UnoCard playedCard) {
        player.addCard(playedCard);
        System.out.println("you cant play this card");
        showCurrentActiveCard();
        printPlayerHand(player);
        return false;
    }

    protected boolean handleValidMove(UnoPlayer player, UnoPlayer nextPlayer, UnoCard playedCard) {
        System.out.println("you have played :" + playedCard);
        handleSpecialCards(playedCard, nextPlayer);
        printPlayerHand(player);
        if (playedCard.getType() != Type.WILD && playedCard.getType() != Type.WILD_DRAW_FOUR) {
            myUnoDeck.setActiveCard(playedCard);
        }
        return true;
    }

    private boolean isIndexValid(UnoPlayer player, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= player.getHand().getHandSize()) {
            System.out.println("Invalid card index. Please choose a valid index.");
            return false;
        }
        return true;
    }

    private int getCardIndex() {
        System.out.println("pick a card by entering its number");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void printPlayerHand(UnoPlayer player) {
        System.out.println("Cards in " + player.getName() + "'s hand:");
        player.getHand().showAllCards();
    }

    @Override
    protected void handleSpecialCards(UnoCard card, UnoPlayer nextPlayer) {
        switch (card.getType()) {
            case REVERSE -> isClockwise = !isClockwise;
            case SKIP -> skipApply = !skipApply;
            case DRAW_TWO -> {
                if (isClockwise) {
                    drawNumOfCards(2, nextPlayer.getRightPlayer());
                } else drawNumOfCards(2, nextPlayer.getLeftPlayer());
            }
            case WILD -> {
                System.out.println("Pick a color for the Wild card");
                playWildCard(getUserInputString());
            }
            case WILD_DRAW_FOUR -> {
                System.out.println("Pick a color for the Wild Draw Four card");
                playWildCard(getUserInputString());
                if (isClockwise) {
                    drawNumOfCards(4, nextPlayer.getRightPlayer());
                }else drawNumOfCards(4, nextPlayer.getLeftPlayer());
            }
            default -> {
            }
        }
    }

    @Override
    protected String getUserInputString() {
        return scanner.next();
    }

    @Override
    protected void playWildCard(String colorAsString) {
        Color color;
        if (colorAsString != null) {
            color = switch (colorAsString.toUpperCase()) {
                case "RED" -> Color.RED;
                case "YELLOW" -> Color.YELLOW;
                case "GREEN" -> Color.GREEN;
                case "BLUE" -> Color.BLUE;
                default -> throw new IllegalArgumentException("Invalid color string: " + colorAsString);
            };
        } else {
            throw new IllegalArgumentException("Color string cannot be null.");
        }
        UnoCard newColor = new UnoCard(color, null, -1);
        myUnoDeck.setActiveCard(newColor);
        showCurrentActiveCard();
    }

    @Override
    protected void drawNumOfCards(int numOfCards, UnoPlayer player) {
        for (int i = 0; i < numOfCards; i++) {
            player.addCard(myUnoDeck.drawCard());
        }
        System.out.println(numOfCards + " cards has been added to " + player.getName() + "'s hand");
    }

    @Override
    protected void showCurrentActiveCard() {
        System.out.println("+----------------------Current Active Card: " + myUnoDeck.getActiveCard().toString() + "---------------------+");
    }

    @Override
    protected boolean isNotValidFirstActiveCard(UnoCard firstActiveCard) {
        return firstActiveCard.isPowerUpCard();
    }

    @Override
    protected boolean isMoveValid(UnoCard playedCard, UnoCard activeCard) {
        boolean isNumberMatch = playedCard.getNumber() == activeCard.getNumber();
        boolean isColorMatch = playedCard.getColor() == activeCard.getColor();
        boolean isSpecialActionValid = isSpecialActionValid(playedCard, activeCard);
        return isNumberMatch || isColorMatch || isSpecialActionValid;
    }

    @Override
    protected boolean isSpecialActionValid(UnoCard playedCard, UnoCard activeCard) {
        Type playedType = playedCard.getType();
        Type topType = activeCard.getType();

        // Check the rules for special action cards
        return switch (playedType) {
            case SKIP, REVERSE, DRAW_TWO -> playedType == topType;
            case WILD, WILD_DRAW_FOUR -> true;
            default -> false;
        };
    }

}
