package euchre.euchre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import euchre.game.utilities.Card;
import euchre.game.utilities.Card.FaceValue;
import euchre.game.utilities.Deck;
import euchre.game.utilities.EuchreUtils;
import euchre.game.utilities.Kitty;
import euchre.game.utilities.RoundResult;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Suite.SuiteName;
import euchre.players.BasicPlayer;
import euchre.players.BasicPlayer.Team;
import euchre.players.Player;

public class EuchreGame {

	private Deck deck;
	private List<Card> discardPile;
	private Kitty kitty;
	private List<Player> players;
	private List<Team> teams;
	private Map<Team, Integer> scores;
	public static List<Suite> suites;

	private static final int NUMCARDSINHAND = 5;

	/**
	 * Initializes the game
	 */
	public EuchreGame() {
		deck = new Deck();
		kitty = new Kitty();
		discardPile = new ArrayList<Card>();
		players = new ArrayList<Player>();
		teams = new ArrayList<Team>();
		suites = new ArrayList<Suite>();
		scores = new HashMap<BasicPlayer.Team, Integer>();
		populateSuites();
		populatePlayers();
		populateDeck();
	}

	public void initRound() {
		// iterate over player list looking for the dealer
		Player dealer = null;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isDealer()) {
				players.get(i).setDealer(false);
				Player nextDealer = players.get((i + 1) % players.size());
				nextDealer.setDealer(true);
				dealer = nextDealer;
				break;
			}
		}
		if (dealer == null) {
			players.get(0).setDealer(true);
		}
		System.out.println("Player " + getDealer().getId() + " is the dealer");
		dealCards(dealer);
	}

	public void populateSuites() {
		for (SuiteName key : SuiteName.values()) {
			suites.add(new Suite(key));
		}
	}

	public void dealCards(Player dealer) {
		// shuffle deck
		EuchreUtils.shuffle(getDeck().getCards());
		Card card = null;
		int i = 0;
		while (i < ((NUMCARDSINHAND * players.size()) - 1)) {
			for (Player player : players) {
				card = getDeck().removeCard();
				player.addCard(card);
				i++;
			}
		}
		// populate the kitty
		kitty.setCards(deck.removeRemainingCards());
		kitty.flipCard();
		// print each players hand and the kitty
		for (Player player : players) {
			System.out.println("Player " + player.getId() + "'s hand : " + player.getHand());
		}
	}

	public Suite decideTrump() {
		int startingIndex = getDealer().getId().intValue();
		Suite trump = null;
		Card flippedCard = kitty.getFlippedCard(false);
		for (int i = startingIndex; i < startingIndex + 4; i++) {
			Player player = players.get(i % players.size());
			trump = player.decideTrump(flippedCard.getSuite(), false, false);
			if (trump != null) {
				System.out.println("Player " + player.getId() + (player.isDealer() ? " picked" : " ordered")
						+ " up the " + flippedCard.toString());
				getKitty().getCards().add(getDealer().discard(trump));
				getDealer().addCard(getKitty().getFlippedCard(true));
				break;
			} else {
				System.out.println("Player " + player.getId() + " has passed");
			}
		}
		if (trump == null) { // meaning we still havent picked trump
			for (int i = startingIndex; i < startingIndex + 4; i++) {
				Player player = players.get(i % players.size());
				trump = player.decideTrump(flippedCard.getSuite(), true, i == startingIndex + 3);
				if (trump != null) {
					System.out.println("Player " + player.getId() + (player.isDealer() ? " picked" : " ordered")
							+ " up the " + flippedCard.toString());
					break;
				} else {
					System.out.println("Player " + player.getId() + " has passed");
				}
			}
		}
		return trump;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public Kitty getKitty() {
		return kitty;
	}

	public void setKitty(Kitty kitty) {
		this.kitty = kitty;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Map<Team, Integer> getScores() {
		return scores;
	}

	public void setScores(Map<Team, Integer> scores) {
		this.scores = scores;
	}

	private void populatePlayers() {
		players.add(new BasicPlayer(1L, Team.ONE));
		players.add(new BasicPlayer(2L, Team.TWO));
		players.add(new BasicPlayer(3L, Team.ONE));
		players.add(new BasicPlayer(4L, Team.TWO));
	}

	public static Suite getSuite(SuiteName name) {
		for (Suite suite : suites) {
			if (suite.getName().equals(name)) {
				return suite;
			}
		}
		throw new RuntimeException("Couldnt get suite");
	}

	private void populateDeck() {
		// first wipe out old deck is there is one?
		this.deck.clear();
		int counter = 0;
		for (SuiteName name : SuiteName.values()) {
			for (FaceValue val : FaceValue.values()) {
				this.deck.addCard(new Card(getSuite(name), val));
				// System.out.println("Added card #" + counter++);
			}
		}

	}

	// this represents a round, not a hand
	public RoundResult doRound(Suite trump) { // return a score? or return the current
										// score for both teams?
		// make the id be an integer?
		int offset = getDealer().getId().intValue()/* + 1 */;
		int j = 0;
		Card winningCard = null;
		while (j++ < 5) {
			List<Card> playedCards = new ArrayList<Card>();
			// each time, after the first time, the offset will have to change
			// since the winner of the hand plays first
			if (winningCard != null) {
				offset = winningCard.getPlayedBy().getId().intValue() - 1;
			}
			for (int i = offset; i < players.size() + offset; i++) {
				Card playedCard = players.get(i % players.size()).playCard(playedCards, trump);
				discardPile.add(playedCard);
				playedCards.add(playedCard);
			}
			winningCard = EuchreUtils.getWinningCard(playedCards, trump, playedCards.get(0).getSuite());
			System.out.println("\nPlayer " + winningCard.getPlayedBy().getId() + " won the round with " + winningCard.toString() + "\n");
		}

		// add point to the round score tracker
		// also need to keep track of who ordered it up and how many were tacken
		// (for scoring purposes)
		deck = getDeck();
		deck.addAll(getDiscardPile());
		deck.addAll(getKitty().getCards());
		return new RoundResult(winningCard); // in the future also set the amount of points for winning
	}

	public void initialize() { // maybe make this initialize next round?

	}

	public Player getDealer() {
		for (Player player : players) {
			if (player.isDealer()) {
				return player;
			}
		}
		throw new RuntimeException("Tried to retrieve the dealer before it was assigned");
	}

	public List<Card> getDiscardPile() {
		return discardPile;
	}

	public void setDiscardPile(List<Card> discardPile) {
		this.discardPile = discardPile;
	}
}
