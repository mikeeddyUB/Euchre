package euchre.game.utilities;

import euchre.players.Player;

public class TrumpCall {
	private Suite suite;
	private Player calledBy;
	
	public TrumpCall(Suite suite, Player calledBy){
		this.setSuite(suite);
		this.setCalledBy(calledBy);
	}

	public Player getCalledBy() {
		return calledBy;
	}

	public void setCalledBy(Player calledBy) {
		this.calledBy = calledBy;
	}

	public Suite getSuite() {
		return suite;
	}

	public void setSuite(Suite suite) {
		this.suite = suite;
	}
}
