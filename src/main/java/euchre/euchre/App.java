package euchre.euchre;

import euchre.game.utilities.RoundResult;
import euchre.game.utilities.Suite;
import euchre.players.BasicPlayer.Team;

public class App {
	public static void main(String[] args) {
		EuchreGame game = new EuchreGame();
		// while both scores < 10
		int team1 = 0;
		int team2 = 0;
		int roundCounter = 0;
		while(team1 < 10 && team2 < 10){
			roundCounter++;
			game.initRound();
			Suite trump = game.decideTrump();
			if (trump != null) {
				RoundResult result = game.doRound(trump);
				if ( result.getWinningTeam() == Team.ONE){
					team1++;
				} else {
					team2++;
				}
				System.out.println("Round " + roundCounter + " over");
				System.out.println("Team 1 : " + team1);
				System.out.println("Team 2 : " + team2 + "\n");
			}
		}
		

		System.out.println("Game over");
	}
}
