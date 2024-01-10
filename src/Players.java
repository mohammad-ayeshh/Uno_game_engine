import java.util.List;

public abstract class Players {

    protected List<MyUnoPlayer> players;

    public int getPlayersSize() {
        return players.size();
    }

    public abstract List<MyUnoPlayer> getPlayers();

    public abstract MyUnoPlayer getPlayer(int index);

    //I did not use these methods because they had some bugs in them,
    // so I kept going with my initial simple implementation of reversing the players
    protected abstract void printPlayers(UnoPlayer startingPlayer);

    protected abstract void reversePlayers(UnoPlayer startingPlayer);

    protected abstract void initializeThePlayers();

}