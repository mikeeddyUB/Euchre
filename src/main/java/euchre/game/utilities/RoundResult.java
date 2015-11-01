package euchre.game.utilities;

public class RoundResult {
	private Team winningTeam;
	private int pointsWon;
	
	public RoundResult(int team1Score, int team2Score){
		if ( team1Score > team2Score){
			winningTeam = Team.ONE;
		} else{
			winningTeam = Team.TWO;
		}
		if ( team1Score == 5 || team2Score == 5){
			pointsWon = 2;
		} else {
			pointsWon = 1;
		}
		System.out.println("Team " + winningTeam + " won the round " + team1Score + " to " + team2Score +" and got " + pointsWon + " points");
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
