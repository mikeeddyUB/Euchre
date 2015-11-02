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
import euchre.game.utilities.Team;

public class BasicPlayer extends AbstractPlayer implements Player {
	
	public BasicPlayer() {
		this.hand = new ArrayList<Card>();
	}

	public BasicPlayer(Long id, Team team) {
		this();
		setId(id);
		setTeam(team);
	}
	
	public Card playCardOfSuite(List<Card> playedCards, Suite suiteLead, Suite trump){
		for ( Card card : getHand()){
			if ( card.getSuite().equals(suiteLead) || EuchreUtils.isLeft(card, trump)){
				return card;
			}
		}
		return getHand().get(0);
	}
	
	public Card playAnyCard(List<Card> playedCards, Suite trump){
		return getHand().get(0);
	}
	
	public Card chooseDiscard(Suite trump){
		return EuchreUtils.getLosingCard(hand, trump, null);
	}

	/**
	 * defines the order for the cards in your hand (not used at the moment)
	 */
	public List<Card> sortCards() {
		return null;
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

	/**
	 * currently being used to pick a suite (of the remaining suites) that has
	 * more than 1 card
	 * 
	 * @param turnedSuite
	 * @return
	 */
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
					return EuchreGame.getSuite(key);
				}
			}
		} else {
			int numSuites = suiteMap.get(turnedSuite.getName());
			if (numSuites > 2 || isDealer() && numSuites > 1) {
				return turnedSuite;
			}
		}
		// if they're the last person to decide trump (the dealer) then they
		// must call it
		if (mustCallTrump) {
			suiteMap = buildSuiteMap(turnedSuite);
			for (SuiteName key : suiteMap.keySet()) {
				if (suiteMap.get(key) > 1) {
					System.out.println("Player " + getId() + " was screwed and called " + key + "S");
					return EuchreGame.getSuite(key);
				}
			}
		}
		return null;
	}

	public boolean equals(Object o) {
		if (o instanceof Player && ((Player) o).getId().equals(this.id)) {
			return true;
		}
		return false;
	}
}
