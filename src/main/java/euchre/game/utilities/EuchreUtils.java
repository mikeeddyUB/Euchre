package euchre.game.utilities;

import java.util.Collections;
import java.util.List;

import euchre.game.utilities.Card.FaceValue;

public class EuchreUtils {

	public static Card getWinningCard(List<Card> cards, Suite trump, Suite leadSuite) {
		return EuchreUtils.sortCards(cards, trump, leadSuite).get(cards.size() - 1);
	}

	public static Card getLosingCard(List<Card> cards, Suite trump, Suite leadSuite) {
		return EuchreUtils.sortCards(cards, trump, leadSuite).get(0);
	}

	/**
	 * returns true of card1 is high than card2
	 * return null if the cards are of equal value
	 */
	public static Boolean compareCards(Card card1, Card card2, Suite trump, Suite leadSuite){ // should unit test this method
		
		boolean card1IsTrumpSuite = card1.getSuite().equals(trump);
		boolean card2IsTrumpSuite = card2.getSuite().equals(trump);
		
		boolean card1IsJack = card1.getValue() == FaceValue.JACK;
		boolean card2IsJack = card2.getValue() == FaceValue.JACK;
		
		boolean card1IsLeft = card1IsJack && !card1IsTrumpSuite && card1.getSuite().getColor().equals(trump.getColor());
		boolean card2IsLeft = card2IsJack && !card2IsTrumpSuite && card2.getSuite().getColor().equals(trump.getColor());
		
		boolean card1IsRight = card1IsJack && card1IsTrumpSuite;
		boolean card2IsRight = card2IsJack && card2IsTrumpSuite;
		
		boolean card1IsLeadSuite = card1.getSuite().equals(leadSuite);
		boolean card2IsLeadSuite = card2.getSuite().equals(leadSuite);
		
		// includes left bowers
		boolean card1IsTrump = card1IsTrumpSuite || card1IsLeft;
		boolean card2IsTrump = card2IsTrumpSuite || card2IsLeft;
		
		boolean neitherTrump = !card1IsTrump && !card2IsTrump;
		boolean cond1 = card1IsLeadSuite && !card2IsLeadSuite;
		boolean condb = card1IsLeadSuite && card2IsLeadSuite && card1.getValue().getValue() > card2.getValue().getValue();
		boolean cond2 = card1IsTrump && !card2IsTrump;
		boolean bothTrump = card1IsTrump && card2IsTrump;
		boolean cond5 = card1IsLeft && !card2IsRight; // card1 is left card2 is right
		boolean cond6 = card1.getValue().getValue() > card2.getValue().getValue() && !card2IsJack;
		
		if ( (neitherTrump && (cond1 || condb)) || cond2 || (bothTrump && (card1IsRight || cond5 || cond6))){
			return true;
		} else { 
			return false;
		}
		// return null if values are the same?
	}

	private static List<Card> sortCards(List<Card> cards, Suite trump, Suite leadSuite) {
		boolean notDone = true;
		boolean swapped;
		while (notDone) {
			notDone = false;
			do {
				swapped = false;
				for (int i = 0; i < cards.size() - 1; i++) {
					Card card1 = cards.get(i);
					Card card2 = cards.get(i + 1);

					if (compareCards(card1, card2, trump, leadSuite) == true) {
						// might need to use a temp variable?
						cards.set(i, card2);
						cards.set(i + 1, card1);
						swapped = true;
						notDone = true;
					}
				}

			} while (swapped);
		}

		return cards;
	}

	public static List<? extends Object> shuffle(List<? extends Object> array) {
		// probably already a library for this -- CollectionUtils?
		Collections.shuffle(array);
		return array;
	}
}
