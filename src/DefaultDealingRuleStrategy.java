public class DefaultDealingRuleStrategy implements DealingRuleStrategy {
    @Override
    public void applyDealingRule(UnoDeck unoDeck, Players myPlayers, int numOfCards4Player, int numOfCardsDealtPerRound) {
        //give the developer a way to set the num of cards per deal

        int numOfDealingRounds = numOfCards4Player / numOfCardsDealtPerRound;
        for (int i = 0; i < numOfDealingRounds; i++) {
            for (UnoPlayer player : myPlayers.getPlayers()) {
                for (int j = 0; j < numOfCardsDealtPerRound; j++) {
                    player.addCard(this.myUnoDeck.drawCard());
                }
            }
        }
        int remainingCards = numOfCards4Player % numOfCardsDealtPerRound;
        for (int i = 0; i < remainingCards; i++) {
            for (UnoPlayer player : myPlayers.getPlayers()) {
                player.addCard(this.myUnoDeck.drawCard());
            }
        }
    }
}