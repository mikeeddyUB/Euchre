package euchre.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import euchre.euchre.EuchreGame;
import euchre.game.utilities.Card;
import euchre.game.utilities.EuchreUtils;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Card.FaceValue;
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
		// System.out.println("Player " + getId() + " received a " +
		// card.toString());
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

	public Card playCard(List<Card> playedCards, Suite trump) { // write unit
																// tests
		Card cardToPlay = null;
		Suite suiteLead;
		boolean followedSuite = false;
		if (playedCards.size() > 0) { // if the left is lead, everything breaks
			// assuming the cards are in order...
			suiteLead = playedCards.get(0).getSuite(); // check here if the
														// suite lead was
														// actually the left
														// bower and change it
														// to the correct suite
			if (playedCards.get(0).getSuite().getColor() == trump.getColor() && 
					playedCards.get(0).getValue() == FaceValue.JACK && 
					playedCards.get(0).getSuite().getName() != trump.getName()){
				suiteLead = trump;
			}
			
			
			if (playedCards.get(0).getValue() == FaceValue.JACK
					&& playedCards.get(0).getSuite().getColor() == trump.getColor()
					&& playedCards.get(0).getSuite().getName() != trump.getName()) {
				suiteLead = trump;
			}
//			System.out.println("Suite lead was " + suiteLead.getName().toString());
			for (Card card : getHand()) {
				boolean cardIsLeft = (card.getValue() == FaceValue.JACK) && !(card.getSuite().equals(trump))
						&& card.getSuite().getColor().equals(trump.getColor());

				if (cardToPlay == null && card.getSuite().equals(suiteLead)
						/*|| (suiteLead.equals(trump) || card.getSuite().equals(trump) && cardIsLeft)*/) {
					followedSuite = true;
					cardToPlay = card;
					getHand().remove(card);
					break;
				}
			}
		}

		if (cardToPlay == null) { // for now if you dont have to follow suite
									// just play a random card
			cardToPlay = getHand().get(0);
			getHand().remove(cardToPlay);
		}

		// for now just play a legal card
		cardToPlay.setPlayedBy(this);
		System.out.println("Player " + getId() + " played " + cardToPlay.toString()
				+ (followedSuite ? "(followed suite)" : "(random)"));
		return cardToPlay;
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

	private Map<SuiteName, Integer> buildSuiteMapForTrump(Suite turnedSuite) {
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

	private Map<SuiteName, Integer> buildSuiteMap(Suite turnedSuite) {
		Map<SuiteName, Integer> suiteMap = new HashMap<SuiteName, Integer>();
		suiteMap.put(SuiteName.SPADE, 0);
		suiteMap.put(SuiteName.CLUB, 0);
		suiteMap.put(SuiteName.DIAMOND, 0);
		suiteMap.put(SuiteName.HEART, 0);
		for (Suite suite : EuchreGame.suites) {
			if (suite != turnedSuite) {
				for (Card card : hand) {
					if (card.wouldBeTrump(suite)) {
						int val = suiteMap.get(card.getSuite().getName()) + 1;
						suiteMap.put(suite.getName(), val);
					}
				}
			}
		}

		return suiteMap;
	}

	public Suite decideTrump(Suite turnedSuite, boolean anySuite, boolean mustCallTrump) {

		Map<SuiteName, Integer> suiteMap = buildSuiteMapForTrump(turnedSuite);
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
		// if they're the last person to decide trump (the dealer) then they
		// have to pick one
		if (mustCallTrump) {
			suiteMap = buildSuiteMap(turnedSuite);
			for (SuiteName key : suiteMap.keySet()) {
				if (suiteMap.get(key) > 1) {
					System.out.println("Player " + getId() + " was screwed and called " + key + "S");
					return EuchreGame.getSuite(key); // return trump with the
														// name of key
				}

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
