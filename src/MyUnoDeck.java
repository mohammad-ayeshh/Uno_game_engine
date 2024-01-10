import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyUnoDeck implements UnoDeck {

    UnoCard activeCard; //current faced-up card
    private final List<UnoCard> cards;

    public UnoCard getActiveCard() {
        return activeCard;
    }

    public void setActiveCard(UnoCard activeCard) {
        this.activeCard = activeCard;
    }

    public MyUnoDeck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public void initializeDeck() {
        for (Color color : Color.values()) {

            cards.add(new UnoCard(color, Type.NUMBER, 0));

            for (int i = 0; i < 2; i++) {
                for (int number = 1; number <= 9; number++) {
                    cards.add(new UnoCard(color, Type.NUMBER, number));
                }
                cards.add(new UnoCard(color, Type.SKIP, -1));
                cards.add(new UnoCard(color, Type.REVERSE, -1));
                cards.add(new UnoCard(color, Type.DRAW_TWO, -1));
            }
        }
        for (int i = 0; i < 4; i++) {
            cards.add(new UnoCard(null, Type.WILD, -1));
            cards.add(new UnoCard(null, Type.WILD_DRAW_FOUR, -1));
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    public UnoCard drawCard() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    public int getDeckSize() {
        return cards.size();
    }
}
