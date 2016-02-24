package euchre.players;

import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import euchre.game.utilities.Card;
import euchre.game.utilities.EuchreUtils;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Team;

public abstract class AbstractPlayer implements Player{
	
	protected Long id;
	protected Team team;
	protected List<Card> hand;
	protected boolean dealer;
	
	public abstract Suite decideTrump(Suite turnedSuite, boolean anySuite, boolean mustCallTrump);
	public abstract Card playCardOfSuite(List<Card> playedCards, Suite suiteLead, Suite trump); // add these to the interface?
	public abstract Card playAnyCard(List<Card> playedCards, Suite trump);
	public abstract Card chooseDiscard(Suite trump);
	
	public String toString(){
		return "Player " + this.id;
	}
	
	public void addCard(Card card) {
		this.hand.add(card);
	}
	
	protected void removeCard(Card cardToRemove) {
		boolean cardFound = false;
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).equals(cardToRemove)) {
				hand.remove(i);
				cardFound = true;
			}
		}
		if (!cardFound) {
			throw new RuntimeException("Couldnt find card: " + cardToRemove);
		}
	}
	
	public Card discard(Suite trump) {
		Card card = chooseDiscard(trump);
		this.removeCard(card);
		System.out.println("Player " + getId() + " discarded " + card.toString());
		return card;
	}
	
	public Card playCard(List<Card> playedCards, Suite trump) { // go through and abstract the required logic
		Card cardToPlay = null;
		Suite suiteLead;
		boolean followSuite = false;
		if (playedCards.size() > 0) {
			// assuming the cards are in order...
			suiteLead = playedCards.get(0).getSuite();
			if (EuchreUtils.isLeft(playedCards.get(0), trump)) {
				suiteLead = trump;
			}

			for (Card card : getHand()) {
				// change for abstraction: go through and see if they need to follow suite, if they do
				// call player.decideMethodNameLater() and add an assert that the card was legal (for human choice)
				if (cardToPlay == null && card.getSuite().equals(suiteLead)) {
					followSuite = true;
					// TODO: if there is only one option, just play the card instead of running extra logic
					break;
				}
			}
			if (followSuite){
				cardToPlay = playCardOfSuite(playedCards, suiteLead, trump);
				getHand().remove(cardToPlay);
			}
		}

		if (cardToPlay == null) {
			cardToPlay = playAnyCard(playedCards, trump);
			getHand().remove(cardToPlay);
		}

		cardToPlay.setPlayedBy(this);
		System.out.println(this.toString() + " played " + cardToPlay.toString()
				+ (followSuite ? "(followed suite)" : "(random)"));
		return cardToPlay;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public Team getTeam() {
		return team;
	}

	public boolean isDealer() {
		return dealer;
	}

	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}
}
