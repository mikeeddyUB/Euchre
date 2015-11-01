package euchre.euchre;

import euchre.game.utilities.RoundResult;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Team;
import euchre.game.utilities.TrumpCall;

public class App {
	public static void main(String[] args) {
		EuchreGame game = new EuchreGame();
		int team1 = 0;
		int team2 = 0;
		int roundCounter = 0;
		
		// add rules so that a team must win be two
		
		while(team1 < 10 && team2 < 10){
			roundCounter++;
			game.initRound();
			TrumpCall trumpResult = game.decideTrump();
			Suite trump = trumpResult.getSuite();
			Team teamThatOrderedSuite = trumpResult.getCalledBy().getTeam();
			if (trump != null) {
				RoundResult result = game.doRound(trump);
				if ( result.getWinningTeam() == Team.ONE ){
					if (teamThatOrderedSuite == Team.ONE){
						team1 += result.getPointsWon();
					} else { // they got euchred
						team1 += 2;
						System.out.println(Team.ONE + " Euchred " + Team.TWO);
					}
					
				} else {
					if (teamThatOrderedSuite == Team.TWO){
						team2 += result.getPointsWon();
					} else { // they got euchred
						team2 += 2;
						System.out.println(Team.TWO + " Euchred " + Team.ONE);
					}
					team2 += result.getPointsWon();
				}
				System.out.println("Round " + roundCounter + " over");
				System.out.println("Team 1 : " + team1);
				System.out.println("Team 2 : " + team2 + "\n");
			}
		}
		

		System.out.println("Game over");
	}
}
