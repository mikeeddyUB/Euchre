package euchre.players;

import java.util.List;

import euchre.game.utilities.Card;
import euchre.game.utilities.Team;

public abstract class AbstractPlayer {
	
	protected Long id;
	protected Team team;
	protected List<Card> hand;
	protected boolean dealer;
	
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
