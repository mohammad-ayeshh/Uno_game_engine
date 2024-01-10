import java.util.List;

public interface UnoPlayer {
    String getName();

    void addCard(UnoCard card);

    UnoCard playCard(int index);

    UnoHand getHand();

    UnoPlayer getRightPlayer();

    void setRightPlayer(UnoPlayer rightPlayer);

    UnoPlayer getLeftPlayer();

    void setLeftPlayer(UnoPlayer leftPlayer);

    int calculateScore(List<UnoPlayer> opponents);

}