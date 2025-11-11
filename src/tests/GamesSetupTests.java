package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.*;

public class GamesSetupTests {
	private static Board board;
	
	
	@BeforeAll
	public void setUp() {
		// clueGame is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	    
		
	
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
		//check correct count
		assertTrue(players.size() == 6);
		//check that the names loaded in in the right order
		assertEquals(players.get(0).getName(), "D'Angelo");
		assertTrue(players.get(0).isHuman());
		assertEquals(players.get(3).getName(), "Marvin Gaye");
		assertFalse(players.get(3).isHuman());
		
		int humanCount = 0;
		int roboCount = 0;
		for(Player player: players) {
			if(player.isHuman()) {
				humanCount++;
			} else {
				roboCount++;
			}
		}
		//check right amount of humans and robots
		assertEquals(1, humanCount);
		assertEquals(5, roboCount);
	}
	
	@Test
	public void testDealing() {
		// make sure cards are dealt as expected
		board.deal();
		Solution solution = board.getSolution();
		ArrayList<Player> players = board.getPlayers();
		for (Player p : players) {
			// ensure each player has 3 cards (assuming 6 player game)
			assertEquals(3, p.getHand().size());
			// ensure that each player doesn't have a solution card in their hand
			for (Card c : p.getHand()) {
				assertFalse(c.equals(solution.getRoom()));
				assertFalse(c.equals(solution.getRoom()));
				assertFalse(c.equals(solution.getWeapon()));
			}
		}

		// check to make sure that a card has not been dealt to >1 player
		for (int i = 0; i < players.size() - 1; i++) {
			for (Card c1 : players.get(i).getHand()) {
				for (Card c2: players.get(i + 1).getHand()) {
					assertFalse(c1.equals(c2));
				}
			}
		}
	}
	
	@Test public void testSolution() {
		board.deal();
		Solution solution = board.getSolution();
		// ensure the solution properly contains a room, person, and weapon card
		assertTrue(solution.getRoom().getCardType() == CardType.ROOM);
		assertTrue(solution.getPerson().getCardType() == CardType.PERSON);
		assertTrue(solution.getWeapon().getCardType() == CardType.WEAPON);
	}
	
	
}
