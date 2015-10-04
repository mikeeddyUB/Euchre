package euchre.game.utilities;

import java.util.ArrayList;
import java.util.List;

public class Deck {
	private List<Card> cards;

	public Deck(){
		this.cards = new ArrayList<Card>();
	}
	
	public List<Card> removeRemainingCards(){
		List<Card> listCopy = new ArrayList<Card>(this.cards);
		this.cards.removeAll(this.cards);
		return listCopy;
	}
	public Card removeCard(){
		return this.cards.remove(this.cards.size() - 1);
	}
	
	public void addCard(Card card){
		cards.add(card);
	}
	
	public void clear(){
		this.cards = new ArrayList<Card>();
	}
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
}
