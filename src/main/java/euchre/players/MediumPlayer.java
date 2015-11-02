package euchre.players;

import java.util.List;

import euchre.game.utilities.Card;
import euchre.game.utilities.EuchreUtils;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Team;

public class MediumPlayer extends BasicPlayer {

	public MediumPlayer(Long id, Team team) {
		super(id, team);
	}

	// public Card playCardOfSuite(List<Card> playedCards, Suite suiteLead,
	// Suite trump){
	// for ( Card card : getHand()){
	// if ( card.getSuite().getName() == trump.getName()){
	// return card;
	// }
	// }
	// return getHand().get(0);
	// }
	//
	public Card playAnyCard(List<Card> playedCards, Suite trump) {
		// meaning this play doesnt have any cards that match the suite lead

		// if playedCards.size() == 0
		// then play your highest (non trump?) card
		// unless you called it?
		if (playedCards.size() == 0) {
			Card highestNonTrumpCard = null;
			for (Card card : getHand()) {
				if (!card.getSuite().equals(trump) && !EuchreUtils.isLeft(card, trump)) {
					if ( highestNonTrumpCard != null || card.getValue().getValue() > highestNonTrumpCard.getValue().getValue()){
						highestNonTrumpCard = card;
					}
				}
			}
			return highestNonTrumpCard;
		}
		// if there have been cards 
		// if your trump is higher, trump it otherwise play your lowest (or short suite yourself?)

		Card highestCardInHand = null;
		Card lowestCardInHand = null;
		Card cardtoPlay = null;

		Card highestCardOnTable = null;
		Card lowestCardOnTable = null;

		// check to see if your card would be the highest card on the table
		// if it it, then play the highest card
		// if it isn't, play the lowest card

		// should also check if the highest card is your partners
		for (Card card : playedCards) {
			if (highestCardOnTable == null || card.getValue().getValue() > highestCardOnTable.getValue().getValue()) {
				highestCardOnTable = card;
			}
			if (lowestCardOnTable == null || card.getValue().getValue() < lowestCardOnTable.getValue().getValue()) {
				lowestCardOnTable = card;
			}
		}

		for (Card card : getHand()) {
			if (highestCardInHand == null || card.getValue().getValue() > highestCardInHand.getValue().getValue()) {
				highestCardInHand = card;
				cardtoPlay = card;
			}
			if (lowestCardInHand == null || card.getValue().getValue() < highestCardInHand.getValue().getValue()) {
				lowestCardInHand = card;
			}
		}
		if (highestCardOnTable != null && highestCardOnTable.getPlayedBy().getTeam() == getTeam()) {
			cardtoPlay = lowestCardInHand;
		}
		// if your card wont win it, then done play your highest card
		if (playedCards.size() > 0) {
			List<Card> playedCardsPlusOne = playedCards;
			playedCardsPlusOne.add(highestCardOnTable);
			if (highestCardInHand != EuchreUtils.getWinningCard(playedCardsPlusOne, trump,
					playedCards.get(0).getSuite())) {
				System.out.println(this.toString() + " WOULDNT WIN SO PLAYED LOWER CARD XXX");
				cardtoPlay = lowestCardInHand;
			}
		}

		if (cardtoPlay == null) {
			cardtoPlay = getHand().get(0);
		}

		return cardtoPlay;
	}

}
