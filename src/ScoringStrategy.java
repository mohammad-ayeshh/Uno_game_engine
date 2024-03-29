import java.util.List;

public interface ScoringStrategy {
    void applyScoring(UnoPlayer player, List<UnoPlayer> opponents);
}
