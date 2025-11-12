package tests;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

import static org.junit.jupiter.api.Assertions.*;

public class ComputerAITest {

	private static Board board;
	
	@BeforeEach
	public void setUp() {
		// clueGame is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void selectTargetsTestNoRoom() {
		// set up sample computer player at (7, 9) and have it roll a number that gives it no room targets
		ComputerPlayer testPlayer = new ComputerPlayer(null, null, 7, 9);
		board.calcTargets(board.getCell(testPlayer.getRow(), testPlayer.getColumn()), 2);
		Set<BoardCell> targets = board.getTargets();
		System.out.println(targets.size());
		
		// have it select the target 200 times
		ArrayList<BoardCell> selections = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			selections.add(testPlayer.selectTarget(targets));
		}
		
		// ensure that every possible option is selected at least once
		int count59 = 0, count68 = 0, count610 = 0, count77 = 0, count711 = 0, count88 = 0, count810 = 0, count99 = 0;
		for (BoardCell cell : selections) {
			if (cell.getRow() == 5 && cell.getColumn() == 9) count59++;
			else if (cell.getRow() == 6 && cell.getColumn() == 8) count68++;
			else if (cell.getRow() == 6 && cell.getColumn() == 10) count610++;
			else if (cell.getRow() == 7 && cell.getColumn() == 7) count77++;
			else if (cell.getRow() == 7 && cell.getColumn() == 11) count711++;
			else if (cell.getRow() == 8 && cell.getColumn() == 8) count88++;
			else if (cell.getRow() == 8 && cell.getColumn() == 10) count810++;
			else if (cell.getRow() == 9 && cell.getColumn() == 9) count99++;
			else continue;
		}
		assertFalse(count59 == 0);
		assertFalse(count68 == 0);
		assertFalse(count610 == 0);
		assertFalse(count77 == 0);
		assertFalse(count711 == 0);
		assertFalse(count88 == 0);
		assertFalse(count810 == 0);
		assertFalse(count99 == 0);
		
		// ensure that the amount that each target is chosen sums to 200
		int sum = count59 + count68 + count610 + count77 + count711 + count88 + count810 + count99;
		assertEquals(200, sum);
	}

	@Test
	public void selectTargetsTestUnseenRoom() {
		// set up sample computer player at (6, 10), in range of two (unseen) rooms with a roll of 3
		ComputerPlayer testPlayer = new ComputerPlayer(null, null, 6, 10);
		board.calcTargets(board.getCell(testPlayer.getRow(), testPlayer.getColumn()), 3);
		Set<BoardCell> targets = board.getTargets();

		// have it select the target 200 times
		ArrayList<BoardCell> selections = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			selections.add(testPlayer.selectTarget(targets));
		}

		// ensure that the computer only ever goes in the rooms
		int inMarquis = 0, inFidlers = 0, outsideRoom = 0;
		for (BoardCell cell : selections) {
			if (cell.getRow() == 2 && cell.getColumn() == 9) inMarquis++;
			else if (cell.getRow() == 6 && cell.getColumn() == 14) inFidlers++;
			else outsideRoom++;
		}
		assertFalse(inMarquis == 0);
		assertFalse(inFidlers == 0);
		assertTrue(outsideRoom == 0);

		// ensure that the amount that each target is chosen sums to 200
		int sum = inMarquis + inFidlers;
		assertEquals(200, sum);
	}

	@Test
	public void selectTargetsTestSeenRoom() {
		// set up computer player just outside Larimer Lounge, add it to seen
		ComputerPlayer testPlayer = new ComputerPlayer(null, null, 20, 19);
		Card larimer = new Card("Larimer Lounge", CardType.ROOM);
		testPlayer.updateSeen(larimer);
		board.calcTargets(board.getCell(testPlayer.getRow(), testPlayer.getColumn()), 1);
		Set<BoardCell> targets = board.getTargets();

		// have it select the target 10 times
		ArrayList<BoardCell> selections = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			selections.add(testPlayer.selectTarget(targets));
		}

		// ensure that the computer selects going in the room and not
		int inRoom = 0, outsideRoom = 0;
		for (BoardCell cell : selections) {
			if (cell.getRow() == 21 && cell.getColumn() == 21) inRoom++;
			else outsideRoom++;
		}
		assertFalse(inRoom == 0);
		assertTrue(outsideRoom == 0);

		// ensure that the amount each target is chosen sums to 10
		int sum = inRoom + outsideRoom;
		assertEquals(10, sum);
	}
	
	@Test 
	public void createSuggestionsTest() {
		/*If only one weapon not seen, it's selected
		If only one person not seen, it's selected (can be same test as weapon)*/
		
		// create a test player, place them in Larimer Lounge, assume they just moved there
		ComputerPlayer testPlayer = new ComputerPlayer(null, null, 21, 21);
		Room currentRoom = board.getCell(testPlayer.getRow(), testPlayer.getColumn()).getRoom();
		board.prepareCards();
		
		
		//Multiple weapons not seen
		Solution suggestion = testPlayer.createSuggestion(currentRoom, board.getDeck());
		
		// ensure that the room in the suggestion is the one entered
		assertTrue(suggestion.getRoom().getCardName().equals(currentRoom.getName()));
		
		// ensure that the person and weapon in the suggestion are not in the computer's seen cards
		assertFalse(testPlayer.getSeen().contains(suggestion.getPerson().getCardName()));
		assertFalse(testPlayer.getSeen().contains(suggestion.getWeapon().getCardName()));
		
		//ensure that it is picked randomly
		int dAngeloCount = 0;
		for(int i = 0; i < 500; i++) {
			suggestion = testPlayer.createSuggestion(currentRoom, board.getDeck());
			if(suggestion.getPerson().getCardName().equals("D'Angelo")) {
				dAngeloCount++;
			}
		}
		assertTrue(dAngeloCount != 0);
		assertTrue(dAngeloCount > 0);
		
		int guitarCount = 0;
		for(int i = 0; i < 500; i++) {
			suggestion = testPlayer.createSuggestion(currentRoom, board.getDeck());
			if(suggestion.getWeapon().getCardName().equals("Guitar")) {
				guitarCount++;
			}
		}
		
		assertTrue(guitarCount != 0);
		assertTrue(guitarCount > 0);
		
		for(Card c : board.getDeck()) {
			testPlayer.updateSeen(c);
		}
		
		for(Card c : board.getDeck()) {
			if(c.getCardName().equals("D'Angelo")) {
				testPlayer.removeSeen(c);
			} else if(c.getCardName().equals("Guitar")) {
				testPlayer.removeSeen(c);
			}
		}
		
		suggestion = testPlayer.createSuggestion(currentRoom, board.getDeck());
		//assertTrue(suggestion.getPerson().getCardName().equals("D'Angelo"));
		assertTrue(suggestion.getWeapon().getCardName().equals("Guitar"));
	}
}
