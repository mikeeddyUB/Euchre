package euchre.players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import euchre.euchre.EuchreGame;
import euchre.game.utilities.Card;
import euchre.game.utilities.EuchreUtils;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Suite.SuiteName;

public class BasicPlayer implements Player {

	private Long id;
	private Team team;
	private List<Card> hand;
	private boolean dealer;

	public BasicPlayer() {
		this.hand = new ArrayList<Card>();
	}

	public BasicPlayer(Long id, Team team) {
		setId(id);
		setTeam(team);
		this.hand = new ArrayList<Card>();
	}

	public void addCard(Card card) {
		this.hand.add(card);
		System.out.println("Player " + getId() + " received a " + card.toString());
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

	public Card playCard() {
		return null;
	}

	/**
	 * defines the order for the cards in your hand (not used at the moment)
	 */
	public List<Card> sortCards() {
		return null;
	}

	private void removeCard(Card cardToRemove) {
		boolean cardFound = false;
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).equals(cardToRemove)) {
				hand.remove(i);
				cardFound = true;
			}
		}
		if (!cardFound) {
			throw new RuntimeException("Couldnt find card");
		}
	}

	public Card discard(Suite trump) {

		Card card = EuchreUtils.getLosingCard(hand, trump, null);
		// remove card from hand and return it
		removeCard(card);
		System.out.println("Player " + getId() + " discarded " + card.toString());
		return card;
	}

	private Map<SuiteName, Integer> buildSuiteMap(Suite turnedSuite) {
		Map<SuiteName, Integer> suiteMap = new HashMap<SuiteName, Integer>();
		suiteMap.put(SuiteName.SPADE, 0);
		suiteMap.put(SuiteName.CLUB, 0);
		suiteMap.put(SuiteName.DIAMOND, 0);
		suiteMap.put(SuiteName.HEART, 0);
		for (Card card : hand) {
			if (card.wouldBeTrump(turnedSuite)) {
				int val = suiteMap.get(card.getSuite().getName()) + 1;
				suiteMap.put(turnedSuite.getName(), val);
			}
		}
		return suiteMap;
	}

	public Suite decideTrump(Suite turnedSuite, boolean anySuite) {

		Map<SuiteName, Integer> suiteMap = buildSuiteMap(turnedSuite);
		// if the player has 3 of one suite or 2 and is the dealer ( and
		// anySuite is false) then order it up
		// otherwise pass
		if (anySuite) {
			suiteMap.remove(turnedSuite);
			for (SuiteName key : suiteMap.keySet()) {
				if (suiteMap.get(key) > 2) {
					return EuchreGame.getSuite(key); // return trump with the
														// name of key
				}

			}
		} else {
			int numSuites = suiteMap.get(turnedSuite.getName());
			if (numSuites > 2 || isDealer() && numSuites > 1) {
				return turnedSuite;
			}
		}
		return null;
	}

	public enum Team {
		ONE, TWO;
	}

	public boolean equals(Object o) {
		if (o instanceof Player && ((Player) o).getId().equals(this.id)) {
			return true;
		}
		return false;
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
