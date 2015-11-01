package euchre.game.utilities;

import euchre.players.Player;

public class Card {
	private Suite suite;
	private FaceValue value;
	private Player playedBy;

	public Card(Suite suite, FaceValue faceValue) {
		this.suite = suite;
		this.value = faceValue;
	}

	public boolean wouldBeTrump(Suite suite) {
		return isTrump(suite);
	}

	public Suite getSuite() {
		return suite;
	}

	public void setSuite(Suite suite) {
		this.suite = suite;
	}

	public String toString() {
		return getValue().name() + " of " + getSuite().name + "S";
	}

	public FaceValue getValue() {
		return value;
	}

	public void setValue(FaceValue value) {
		this.value = value;
	}

	public boolean isTrump(Suite suite) {
		// looks at the suite and color of the card and returns yes if the suite
		// matches or its the left
		return this.suite.equals(suite) || (this.value == FaceValue.JACK
				&& this.suite.getName().equals(suite.getName()) && this.suite.color == suite.color);
	}

	public enum FaceValue {
		NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

		private int value;

		FaceValue(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	public boolean equals(Object o) {
		return (o instanceof Card && ((Card)o).getSuite().equals(this.getSuite()) && ((Card)o).getValue() == this.getValue());
	}

	public Player getPlayedBy() {
		return playedBy;
	}

	public void setPlayedBy(Player playedBy) {
		this.playedBy = playedBy;
	}
}
