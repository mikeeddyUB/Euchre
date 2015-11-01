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

	public Card playCard(List<Card> playedCards, Suite trump) { 
																
		Card cardToPlay = null;
		Suite suiteLead;
		boolean followedSuite = false;
		if (playedCards.size() > 0) {
			// assuming the cards are in order...
			suiteLead = playedCards.get(0).getSuite();
			if(EuchreUtils.isLeft(playedCards.get(0), trump)){
				suiteLead = trump;
			}
			
			for (Card card : getHand()) {

				if (cardToPlay == null && card.getSuite().equals(suiteLead)) {
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

		cardToPlay.setPlayedBy(this);
		System.out.println(this.toString() + " played " + cardToPlay.toString()
				+ (followedSuite ? "(followed suite)" : "(random)"));
		return cardToPlay;
	}

	/**
	 * defines the order for the cards in your hand (not used at the moment)
	 */
	public List<Card> sortCards() {
		return null;
	}

	public Card discard(Suite trump) {

		Card card = EuchreUtils.getLosingCard(hand, trump, null);
		// remove card from hand and return it
		this.removeCard(card);
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

	/**
	 * currently being used to pick a suite (of the remaining suites) that has more than 1 card
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

	public boolean equals(Object o) {
		if (o instanceof Player && ((Player) o).getId().equals(this.id)) {
			return true;
		}
		return false;
	}

}
