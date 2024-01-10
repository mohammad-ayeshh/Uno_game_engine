import java.util.List;

public class MyUnoPlayer implements UnoPlayer {
    private final String name;
    private final UnoHand hand;
    private UnoPlayer rightPlayer;
    private UnoPlayer leftPlayer;

    public MyUnoPlayer(String name) {
        this.name = name;
        this.hand = new UnoHand();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addCard(UnoCard card) {
        hand.getCards().add(card);
    }

    @Override
    public UnoCard playCard(int index) {
        if (index >= 0 && index < hand.getHandSize()) {
            return hand.getCards().remove(index);
        } else {
            return null;
        }
    }

    @Override
    public UnoHand getHand() {
        return hand;
    }

    @Override
    public UnoPlayer getRightPlayer() {
        return rightPlayer;
    }

    @Override
    public void setRightPlayer(UnoPlayer rightPlayer) {
        this.rightPlayer = rightPlayer;
    }

    @Override
    public UnoPlayer getLeftPlayer() {
        return leftPlayer;
    }

    @Override
    public void setLeftPlayer(UnoPlayer leftPlayer) {
        this.leftPlayer = leftPlayer;
    }

    @Override
    public int calculateScore(List<UnoPlayer> opponents) {
        int score = 0;

        for (UnoCard card : hand.getCards()) {
            switch (card.getType()) {
                case NUMBER:
                    // Face value for number cards // red 9 = +9 points
                    score += card.getNumber();
                    break;
                case SKIP:
                case REVERSE:
                case DRAW_TWO:
                    // 20 points for each Draw 2, Reverse, or Skip card
                    for (UnoPlayer opponent : opponents) {
                        score += 20 * opponent.getHand().countActionCards(card.getType());
                    }
                    break;
                case WILD:
                case WILD_DRAW_FOUR:
                    // 50 points for Wild and Wild Draw 4 cards
                    score += 50;
                    break;
                default:
                    // Handle other card types if needed
                    break;
            }
        }

        return score;
    }
}