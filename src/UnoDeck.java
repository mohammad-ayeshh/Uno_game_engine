import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface UnoDeck {

    public UnoCard getActiveCard();

    public void setActiveCard(UnoCard activeCard);

    public void initializeDeck();

    public void shuffleDeck();

    public UnoCard drawCard();

    public int getDeckSize();

}
