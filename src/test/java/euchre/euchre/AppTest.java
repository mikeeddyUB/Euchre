package euchre.euchre;

import java.util.Arrays;
import java.util.List;

import euchre.game.utilities.Card;
import euchre.game.utilities.EuchreUtils;
import euchre.game.utilities.Suite;
import euchre.game.utilities.Card.FaceValue;
import euchre.game.utilities.Suite.SuiteName;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}
	
	public void testGetWinningCard(){
		List<Card> cards = Arrays.asList(new Card(hearts, FaceValue.TEN), new Card(hearts, FaceValue.NINE), new Card(hearts, FaceValue.KING), new Card(clubs, FaceValue.KING));
		Card card = EuchreUtils.getWinningCard(cards, spades, hearts);
		assertEquals(FaceValue.KING, card.getValue());
		assertEquals(SuiteName.HEART, card.getSuite().getName());
	}
	
	Suite clubs = new Suite(SuiteName.CLUB);
	Suite spades = new Suite(SuiteName.SPADE);
	Suite hearts = new Suite(SuiteName.HEART);
	Suite diamonds = new Suite(SuiteName.DIAMOND);

	public void testCompareCards(){
		
		
		// trump cant be null
		assertTrue(EuchreUtils.compareCards(new Card(clubs, FaceValue.TEN), new Card(clubs, FaceValue.NINE), clubs, clubs));
		// test left vs ace of trump
		assertTrue(EuchreUtils.compareCards(new Card(spades, FaceValue.JACK), new Card(clubs, FaceValue.ACE), clubs, clubs));
		// test right vs ace of trump
		assertTrue(EuchreUtils.compareCards(new Card(clubs, FaceValue.JACK), new Card(clubs, FaceValue.ACE), clubs, clubs));
		// test right vs left
		assertTrue(EuchreUtils.compareCards(new Card(clubs, FaceValue.JACK), new Card(spades, FaceValue.JACK), clubs, clubs));
		assertFalse(EuchreUtils.compareCards(new Card(clubs, FaceValue.NINE), new Card(clubs, FaceValue.TEN), clubs, clubs));
		assertFalse(EuchreUtils.compareCards(new Card(clubs, FaceValue.TEN), new Card(clubs, FaceValue.NINE), hearts, hearts));
		
		assertFalse(EuchreUtils.compareCards(new Card(spades, FaceValue.TEN), new Card(spades, FaceValue.KING), clubs, spades));
	
//		assertTrue(EuchreUtils.compareCards(new Card(clubs, FaceValue.NINE), new Card(clubs, FaceValue.TEN), clubs, clubs));
//		assertTrue(EuchreUtils.compareCards(new Card(clubs, FaceValue.NINE), new Card(clubs, FaceValue.TEN), clubs, clubs));
//		assertTrue(EuchreUtils.compareCards(new Card(clubs, FaceValue.NINE), new Card(clubs, FaceValue.TEN), clubs, clubs));
//		assertTrue(EuchreUtils.compareCards(new Card(clubs, FaceValue.NINE), new Card(clubs, FaceValue.TEN), clubs, clubs));
	}
}
