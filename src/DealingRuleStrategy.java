public interface DealingRuleStrategy {
    void applyDealingRule(UnoDeck unoDeck, Players myPlayers, int numOfCards4Player, int numOfCardsDealtPerRound);
}