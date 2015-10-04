package euchre.euchre;

import euchre.game.utilities.Suite;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EuchreGame game = new EuchreGame();
        // while both scores < 10
        game.initRound();
        Suite trump = game.decideTrump();
        
        // implement sorting
        // doRound()
    }
}
