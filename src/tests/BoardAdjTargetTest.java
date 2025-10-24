package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest {
	private static Board board;
	
	//DONE
	@BeforeAll
	public static void setUp() {
		//setup board
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");		
		board.initialize();
	}

	//DONE
	//Purple on the spreadsheet, tests room adjacencies from the center
	@Test
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
		testList = board.getAdjList(20, 2);
		assertEquals(3, testList.size());
		//secret door
		assertTrue(testList.contains(board.getCell(4, 18)));
		//other doors
		assertTrue(testList.contains(board.getCell(19, 6)));
		assertTrue(testList.contains(board.getCell(16, 2)));
	}

	
	//DONE
	// Ensure door locations include their rooms and also additional walkways
	// These cells are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getAdjList(5, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(5, 2)));
		assertTrue(testList.contains(board.getCell(5, 4)));
		assertTrue(testList.contains(board.getCell(6, 3)));
		

		testList = board.getAdjList(18, 10);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(21, 9)));
		assertTrue(testList.contains(board.getCell(18, 11)));
		assertTrue(testList.contains(board.getCell(18, 9)));
		assertTrue(testList.contains(board.getCell(17, 10)));

		
		testList = board.getAdjList(19, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(20, 1)));
		assertTrue(testList.contains(board.getCell(18, 6)));
		assertTrue(testList.contains(board.getCell(20, 6)));
		assertTrue(testList.contains(board.getCell(19, 7)));
	}
	
	

	//DONE
	// Test a variety of walkway scenarios
	// These tests are DARK ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(22, 6);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(21, 6)));
		assertTrue(testList.contains(board.getCell(22, 7)));

		
		// Test near a door but not adjacent
		testList = board.getAdjList(15, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(15, 2)));
		assertTrue(testList.contains(board.getCell(15, 4)));
		assertTrue(testList.contains(board.getCell(14, 3)));
		assertTrue(testList.contains(board.getCell(16, 3)));

		
		// Test adjacent to doorways
		testList = board.getAdjList(15, 2);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(14, 2)));
		assertTrue(testList.contains(board.getCell(16, 2)));
		assertTrue(testList.contains(board.getCell(15, 3)));
		assertTrue(testList.contains(board.getCell(15, 1)));

		// Test next to closet
		testList = board.getAdjList(12,14);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(11, 14)));
		assertTrue(testList.contains(board.getCell(13, 14)));
		assertTrue(testList.contains(board.getCell(12, 15)));
	
	}
	
	
	
	// DONE
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInMarquisTheater() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 1), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 1), 3);
		targets= board.getTargets();
		//Not 100% sure on how targets work, so this may be an issue, however, the logic is solid.
		assertEquals(10, targets.size());
		// a few random cells that should be in it
		assertTrue(targets.contains(board.getCell(9, 5)));
		assertTrue(targets.contains(board.getCell(10, 6)));	
		assertTrue(targets.contains(board.getCell(11, 7)));
		assertTrue(targets.contains(board.getCell(12, 6)));	
		assertTrue(targets.contains(board.getCell(13, 5)));	
		assertTrue(targets.contains(board.getCell(16, 2)));	

		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 1), 4);
		targets= board.getTargets();
		//Not 100% sure on how targets work, so this may be an issue
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(9, 6)));	
		assertTrue(targets.contains(board.getCell(10, 7)));
		assertTrue(targets.contains(board.getCell(20, 1)));	
	}
	
	
	// CELL CHOSEN ON SPREADSHEET BUT NOT DONE
	@Test
	public void testTargetsInFidlersGreen() {
		// test a roll of 1
		board.calcTargets(board.getCell(20, 19), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertTrue(targets.contains(board.getCell(2, 2)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(20, 19), 3);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(17, 20)));
		assertTrue(targets.contains(board.getCell(16, 19)));	
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(2, 2)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(20, 19), 4);
		targets= board.getTargets();
		assertEquals(9, targets.size());
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(18, 16)));	
		assertTrue(targets.contains(board.getCell(16, 16)));
		assertTrue(targets.contains(board.getCell(2, 2)));	
	}

	
	// CELL CHOSEN ON SPREADSHEET BUT NOT DONE
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(8, 17), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 18)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(8, 17), 3);
		targets= board.getTargets();
		assertEquals(12, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(7, 19)));
		assertTrue(targets.contains(board.getCell(9, 15)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(8, 17), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(5, 16)));	
	}

	
	
	// CELL CHOSEN ON SPREADSHEET BUT NOT DONE
	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(11, 2), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(11, 1)));
		assertTrue(targets.contains(board.getCell(11, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 2), 3);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 2), 4);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(8, 2)));
		assertTrue(targets.contains(board.getCell(11, 6)));	
	}

	
	
	// CELL CHOSEN ON SPREADSHEET BUT NOT DONE
	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(13, 7), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(12, 7)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 7), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(15, 6)));
		assertTrue(targets.contains(board.getCell(14, 7)));
		assertTrue(targets.contains(board.getCell(11, 8)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(13, 7), 4);
		targets= board.getTargets();
		assertEquals(15, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
	}

	
	
	//NOT DONE AND CELLS NOT CHOSEN
	@Test
	// test to make sure occupied locations do not cause problems
	// marked as RED on spreadsheet
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(15, 7).setOccupied(true);
		board.calcTargets(board.getCell(13, 7), 4);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(15, 9)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		assertFalse( targets.contains( board.getCell(15, 7))) ;
		assertFalse( targets.contains( board.getCell(17, 7))) ;
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(12, 20).setOccupied(true);
		board.getCell(8, 18).setOccupied(true);
		board.calcTargets(board.getCell(8, 17), 1);
		board.getCell(12, 20).setOccupied(false);
		board.getCell(8, 18).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(7, 17)));	
		assertTrue(targets.contains(board.getCell(8, 16)));	
		assertTrue(targets.contains(board.getCell(12, 20)));	
		
		// check leaving a room with a blocked doorway
		board.getCell(12, 15).setOccupied(true);
		board.calcTargets(board.getCell(12, 20), 3);
		board.getCell(12, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(6, 17)));
		assertTrue(targets.contains(board.getCell(8, 19)));	
		assertTrue(targets.contains(board.getCell(8, 15)));

	}
}
