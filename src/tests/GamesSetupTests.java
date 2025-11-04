package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

public class GamesSetupTests {
	private static Board board;
	
	/*@Test
	public void testAdjacenciesRooms()
	{
		// Larimer Lounge should only have one door
		Set<BoardCell> testList = board.getAdjList(21, 21);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(20, 19)));
		
		// Bluebird theater has 2 doors and one secret door
		testList = board.getAdjList(21, 9);
		assertEquals(3, testList.size());
		//secret door goes to where it will be
		assertTrue(testList.contains(board.getCell(2, 9)));
		//other doors
		assertTrue(testList.contains(board.getCell(18, 10)));
		assertTrue(testList.contains(board.getCell(18, 11)));
		
		// one more room, ball arena, two doors and a different secret door to test
		testList = board.getAdjList(20, 1);
		assertEquals(3, testList.size());
		//secret door
		assertTrue(testList.contains(board.getCell(6, 14)));
		//other doors
		assertTrue(testList.contains(board.getCell(19, 6)));
		assertTrue(testList.contains(board.getCell(16, 2)));
	}*/
	
	@BeforeAll
	public static void setUp() {
		// clueGame is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
		/*Setup Test
		So what do we need to be concerned with testing setup?   Here are some of our necessary concerns:

		People are loaded in
		Proper Human or Computer player is initialized based on people data
		Deck of all cards is created (composed of rooms, weapons, and people)
		The solution to the game is dealt
		The other cards are dealt to the players.*/
	    
		
	
	@Test
	public void testDeck() {
		//test deck length
		ArrayList<Card> deck = new ArrayList<Card>();
	    deck = board.getDeck();
		assertTrue(deck.size() == 21);
		
		//ADD MORE TESTS TO FLESH OUT DECK

	}
	
	@Test
	public void testDeltCards() {
		
	}
	
	@Test
	public void testPlayers() {
		//make sure player objects are correct
	}
}
