package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GamesSetupTests {
	private static Board board;
	
	
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
		The solution to the game is dealt
		The other cards are dealt to the players.
		
		Load people and weapons from ClueSetup.txt and ensure the data was loaded properly.
		Create Player class with human and computer child classes.   
		Use people data to instantiate 6 players (1 human and 5 computer)
		Create complete deck of cards (weapons, people and rooms)
		Deal cards to the Answer and the players 
		(all cards dealt, players have roughly same # of cards, no card dealt twice)*/
	    
		
	
	@Test
	public void testDeck() throws BadConfigFormatException {
		//test deck length
		ArrayList<Card> deck = new ArrayList<Card>();
	    deck = board.getDeck();
		assertTrue(deck.size() == 21);
		
		int weaponCount = 0;
		int roomCount = 0;
		int personCount = 0;
		for (Card c : deck) {
	        switch (c.getCardType()) {
	            case WEAPON:
	                weaponCount++;
	                break;
	            case ROOM:
	                roomCount++;
	                break;
	            case PERSON:
	                personCount++;
	                break;
	            default:
	                throw new BadConfigFormatException("Card of unknown type.");
	        }
	    }
		
		//check deck for right card counts
		assertEquals(6, weaponCount);
		assertEquals(9, roomCount);
		assertEquals(6, personCount);

	}
	
	
	@Test
	public void testPlayers() {
		//make sure player objects are correct
		ArrayList<Player> players = new ArrayList<Player>();
		players = board.getPlayers();
		assertTrue(players.size() == 6);
		assertEquals(players.get(0).getName(), "D'Angelo");
		assertTrue(players.get(0).isHuman());
		assertEquals(players.get(3).getName(), "Marvin Gaye");
		assertTrue(!players.get(3).isHuman());
		
		int humanCount = 0;
		int roboCount = 0;
		for(Player player: players) {
			if(player.isHuman()) {
				humanCount++;
			} else {
				roboCount++;
			}
		}
		
		assertEquals(1, humanCount);
		assertEquals(5, roboCount);
		
		
	}
}
