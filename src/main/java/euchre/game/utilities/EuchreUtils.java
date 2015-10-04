package euchre.game.utilities;

import java.util.Collections;
import java.util.List;

public class EuchreUtils {

	public static Card getWinningCard(List<Card> cards) {
		return EuchreUtils.sortCards(cards).get(cards.size() - 1);
	}

	public static Card getLosingCard(List<Card> cards) {
		return EuchreUtils.sortCards(cards).get(0);
	}

	private static List<Card> sortCards(List<Card> cards) {
		boolean notDone = true;
		boolean swapped;
		
		while(notDone){
			notDone = false;
			do{
				swapped = false;
				
				for ( int i = 0; i < cards.size(); i++){
					
					Card card1 = cards.get(0);
					Card card2 = cards.get(1);

					
					
				}
				
				
				
				
				
				
				
				
				
				
			} while(true);
				
			
			
			
			
		}
		

		return cards;
	}

	public static List<? extends Object> shuffle(List<? extends Object> array) {
		// probably already a library for this -- CollectionUtils?
		Collections.shuffle(array);
		return array;
	}
}
