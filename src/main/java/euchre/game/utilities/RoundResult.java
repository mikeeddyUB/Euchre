package euchre.game.utilities;

import euchre.players.BasicPlayer.Team;

public class RoundResult {
	private Team winningTeam;
	private int pointsWon;
	
	public RoundResult(Card winningCard){
		setWinningTeam(winningCard.getPlayedBy().getTeam());
		pointsWon = 1; // hard code 1 for now
	}

	public Team getWinningTeam() {
		return winningTeam;
	}

	public void setWinningTeam(Team winningTeam) {
		this.winningTeam = winningTeam;
	}

	public int getPointsWon() {
		return pointsWon;
	}

	public void setPointsWon(int pointsWon) {
		this.pointsWon = pointsWon;
	}
}
