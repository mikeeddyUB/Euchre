package euchre.players;

import java.util.List;

import euchre.game.utilities.Card;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Team;

public interface Player {
	public Card playCard(List<Card> playedCards, Suite trump);
	public List<Card> sortCards();
	public Card discard(Suite trump);
	public Suite decideTrump(Suite turnedSuite, boolean anySuite, boolean mustCallTrump);
	public Team getTeam();
	public Long getId(); // RENAME THIS TO POSITION AND USE IT THAT WAY?
	public boolean isDealer();
	public void setDealer(boolean dealer);
	public void addCard(Card card);
	public List<Card> getHand();
}
