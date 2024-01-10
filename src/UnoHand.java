import java.util.ArrayList;
import java.util.List;

public class UnoHand {

    private List<UnoCard> cards;

    public List<UnoCard> getCards() {
        return cards;
    }

    public int countActionCards(Type actionType) {
        int count = 0;

        for (UnoCard card : cards) {
            if (card.getType() == actionType) {
                count++;
            }
        }

        return count;
    }

    public UnoHand() {
        this.cards = new ArrayList<>();
    }

    public void showAllCards() {
        if (cards.size() > 0) {
            System.out.println("+------------------------------------------------------------------------+");
            for (int i = 0; i < cards.size(); i += 3) {
                for (int j = 0; j < 3 && i + j < cards.size(); j++) {
                    System.out.print("| " + String.format("Card %d: %-12s", i + j + 1, cards.get(i + j)) + " |");
                }
                System.out.println();
            }
            System.out.println("+------------------------------------------------------------------------+");
        }
        else
            System.out.println("+----------------------------no cards in hand----------------------------+");
    }


    public boolean isPlayable(UnoCard activeCard) {
        for (UnoCard card : cards) {
            if (card.getType() == Type.WILD || card.getType() == Type.WILD_DRAW_FOUR || (card.getType() == Type.NUMBER && card.getNumber() == activeCard.getNumber())
                    || card.getColor() == activeCard.getColor())
                return true;
            else if (card.getType() == activeCard.getType() && card.getType() != Type.NUMBER) {
                return true;
            }
        }
        return false;
    }

    public int getHandSize() {
        return cards.size();
    }
}
