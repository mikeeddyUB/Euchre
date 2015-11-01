package euchre.game.utilities;

import java.util.List;

public class Kitty {
	private List<Card> cards;
	
	private Card flippedCard = null;
	
	public Kitty(){
		
	}
	
	public Card flipCard(){
		flippedCard = cards.get(0);
		System.out.println(flippedCard.toString() + " was flipped");
		return flippedCard;
	}
	
	public Kitty(List<Card> cards){
		this.setCards(cards);
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card getFlippedCard(boolean remove) {
		if (remove){
			for (int i = 0; i < cards.size(); i++){
				if ( flippedCard.equals(cards.get(i))){
					cards.remove(i);
				}
			}
		}
		return flippedCard;
	}

	public void setFlippedCard(Card flippedCard) {
		this.flippedCard = flippedCard;
	}
}
