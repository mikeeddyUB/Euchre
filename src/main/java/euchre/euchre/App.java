package euchre.euchre;

import euchre.game.utilities.Suite;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		EuchreGame game = new EuchreGame();
		// while both scores < 10
		int team1 = 0;
		int team2 = 0;
		while(team1 < 10 && team2 < 10){
			game.initRound();
			Suite trump = game.decideTrump();
			if (trump != null) {
				game.doRound(trump);
				System.out.println("Round " + team1 + " over");
				team1++;
			}
		}
		

		System.out.println("Game over");
		// implement sorting
		// doRound()
	}
}
