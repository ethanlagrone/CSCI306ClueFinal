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
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(14, 2)));
		assertTrue(targets.contains(board.getCell(11, 5)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 1), 3);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(10, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(9, 5)));
		assertTrue(targets.contains(board.getCell(10, 6)));	
		assertTrue(targets.contains(board.getCell(11, 7)));
		assertTrue(targets.contains(board.getCell(12, 6)));	
		assertTrue(targets.contains(board.getCell(13, 5)));
		assertTrue(targets.contains(board.getCell(14, 0)));	
		assertTrue(targets.contains(board.getCell(15, 1)));	
		assertTrue(targets.contains(board.getCell(16, 2)));	
		assertTrue(targets.contains(board.getCell(15, 3)));	
		assertTrue(targets.contains(board.getCell(14, 4)));	

		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 1), 4);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(14, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(9, 6)));	
		assertTrue(targets.contains(board.getCell(10, 7)));
		assertTrue(targets.contains(board.getCell(11, 6)));
		assertTrue(targets.contains(board.getCell(11, 8)));
		assertTrue(targets.contains(board.getCell(12, 7)));
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(14, 5)));
		assertTrue(targets.contains(board.getCell(15, 0)));
		assertTrue(targets.contains(board.getCell(15, 2)));
		assertTrue(targets.contains(board.getCell(15, 4)));
		assertTrue(targets.contains(board.getCell(16, 1)));
		assertTrue(targets.contains(board.getCell(16, 3)));										
		assertTrue(targets.contains(board.getCell(20, 1)));	
	}
	
	// DONE
	@Test
	public void testTargetsInFidlersGreen() {
		// test a roll of 1
		board.calcTargets(board.getCell(6, 14), 1);
		Set<BoardCell> targets = board.getTargets();
		// number of cells that should be included
		assertEquals(3, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(6, 12)));
		assertTrue(targets.contains(board.getCell(8, 15)));
		assertTrue(targets.contains(board.getCell(8, 16)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(6, 14), 3);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(16, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(4, 12)));
		assertTrue(targets.contains(board.getCell(5, 11)));	
		assertTrue(targets.contains(board.getCell(6, 10)));
		assertTrue(targets.contains(board.getCell(7, 11)));
		assertTrue(targets.contains(board.getCell(8, 12)));	
		assertTrue(targets.contains(board.getCell(8, 13)));	
		assertTrue(targets.contains(board.getCell(8, 14)));	
		assertTrue(targets.contains(board.getCell(8, 17)));	
		assertTrue(targets.contains(board.getCell(8, 18)));	
		assertTrue(targets.contains(board.getCell(9, 14)));	
		assertTrue(targets.contains(board.getCell(9, 15)));	
		assertTrue(targets.contains(board.getCell(9, 16)));	
		assertTrue(targets.contains(board.getCell(9, 17)));	
		assertTrue(targets.contains(board.getCell(10, 15)));	
		assertTrue(targets.contains(board.getCell(10, 16)));	
		assertTrue(targets.contains(board.getCell(20, 1)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(6, 14), 4);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(25, targets.size());
		// jesus christ man
		assertTrue(targets.contains(board.getCell(3, 12)));
		assertTrue(targets.contains(board.getCell(4, 11)));	
		assertTrue(targets.contains(board.getCell(5, 10)));
		assertTrue(targets.contains(board.getCell(6, 9)));
		assertTrue(targets.contains(board.getCell(6, 11)));
		assertTrue(targets.contains(board.getCell(7, 10)));
		assertTrue(targets.contains(board.getCell(8, 11)));
		assertTrue(targets.contains(board.getCell(8, 12)));
		assertTrue(targets.contains(board.getCell(8, 13)));
		assertTrue(targets.contains(board.getCell(8, 18)));
		assertTrue(targets.contains(board.getCell(8, 19)));
		assertTrue(targets.contains(board.getCell(9, 12)));
		assertTrue(targets.contains(board.getCell(9, 13)));
		assertTrue(targets.contains(board.getCell(9, 14)));
		assertTrue(targets.contains(board.getCell(9, 15)));
		assertTrue(targets.contains(board.getCell(9, 16)));
		assertTrue(targets.contains(board.getCell(9, 17)));
		assertTrue(targets.contains(board.getCell(9, 18)));
		assertTrue(targets.contains(board.getCell(10, 14)));
		assertTrue(targets.contains(board.getCell(10, 15)));
		assertTrue(targets.contains(board.getCell(10, 16)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(20, 1)));
	}

	// DONE
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(11, 5), 1);
		Set<BoardCell> targets = board.getTargets();
		// number of cells that should be included
		assertEquals(4, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(10, 5)));
		assertTrue(targets.contains(board.getCell(11, 1)));	
		assertTrue(targets.contains(board.getCell(11, 6)));
		assertTrue(targets.contains(board.getCell(12, 5)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(11, 5), 3);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(9, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(9, 6)));
		assertTrue(targets.contains(board.getCell(10, 7)));
		assertTrue(targets.contains(board.getCell(11, 1)));	
		assertTrue(targets.contains(board.getCell(11, 6)));
		assertTrue(targets.contains(board.getCell(11, 8)));
		assertTrue(targets.contains(board.getCell(12, 7)));
		assertTrue(targets.contains(board.getCell(13, 6)));
		assertTrue(targets.contains(board.getCell(14, 5)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(11, 5), 4);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(16, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(8, 4)));
		assertTrue(targets.contains(board.getCell(8, 6)));	
		assertTrue(targets.contains(board.getCell(9, 5)));
		assertTrue(targets.contains(board.getCell(9, 7)));
		assertTrue(targets.contains(board.getCell(10, 6)));
		assertTrue(targets.contains(board.getCell(10, 8)));
		assertTrue(targets.contains(board.getCell(11, 1)));
		assertTrue(targets.contains(board.getCell(11, 7)));
		assertTrue(targets.contains(board.getCell(12, 6)));
		assertTrue(targets.contains(board.getCell(12, 8)));
		assertTrue(targets.contains(board.getCell(13, 5)));
		assertTrue(targets.contains(board.getCell(13, 7)));
		assertTrue(targets.contains(board.getCell(14, 4)));
		assertTrue(targets.contains(board.getCell(14, 6)));
		assertTrue(targets.contains(board.getCell(15, 5)));	
	}

	// DONE
	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(12, 17), 1);
		Set<BoardCell> targets = board.getTargets();
		// number of cells that should be included
		assertEquals(4, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(11, 17)));
		assertTrue(targets.contains(board.getCell(12, 16)));
		assertTrue(targets.contains(board.getCell(12, 18)));
		assertTrue(targets.contains(board.getCell(13, 17)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 17), 3);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(16, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(9, 17)));
		assertTrue(targets.contains(board.getCell(10, 16)));
		assertTrue(targets.contains(board.getCell(10, 18)));
		assertTrue(targets.contains(board.getCell(11, 15)));
		assertTrue(targets.contains(board.getCell(11, 17)));
		assertTrue(targets.contains(board.getCell(11, 19)));
		assertTrue(targets.contains(board.getCell(12, 14)));
		assertTrue(targets.contains(board.getCell(12, 16)));
		assertTrue(targets.contains(board.getCell(12, 18)));
		assertTrue(targets.contains(board.getCell(12, 20)));
		assertTrue(targets.contains(board.getCell(13, 15)));
		assertTrue(targets.contains(board.getCell(13, 17)));
		assertTrue(targets.contains(board.getCell(13, 19)));
		assertTrue(targets.contains(board.getCell(14, 16)));
		assertTrue(targets.contains(board.getCell(14, 18)));
		assertTrue(targets.contains(board.getCell(15, 17)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(12, 17), 4);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(22, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(8, 17)));
		assertTrue(targets.contains(board.getCell(9, 16)));
		assertTrue(targets.contains(board.getCell(9, 18)));
		assertTrue(targets.contains(board.getCell(10, 15)));
		assertTrue(targets.contains(board.getCell(10, 17)));
		assertTrue(targets.contains(board.getCell(10, 19)));
		assertTrue(targets.contains(board.getCell(11, 14)));
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(11, 18)));
		assertTrue(targets.contains(board.getCell(11, 20)));
		assertTrue(targets.contains(board.getCell(12, 15)));
		assertTrue(targets.contains(board.getCell(12, 19)));
		assertTrue(targets.contains(board.getCell(13, 14)));
		assertTrue(targets.contains(board.getCell(13, 16)));
		assertTrue(targets.contains(board.getCell(13, 18)));
		assertTrue(targets.contains(board.getCell(13, 20)));
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(14, 17)));
		assertTrue(targets.contains(board.getCell(14, 19)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(15, 18)));
		assertTrue(targets.contains(board.getCell(16, 17)));	
	}

	// DONE
	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(17, 16), 1);
		Set<BoardCell> targets = board.getTargets();
		// number of cells that should be included
		assertEquals(4, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(16, 16)));
		assertTrue(targets.contains(board.getCell(17, 15)));
		assertTrue(targets.contains(board.getCell(17, 17)));
		assertTrue(targets.contains(board.getCell(18, 16)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(17, 16), 3);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(16, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(14, 16)));
		assertTrue(targets.contains(board.getCell(15, 15)));
		assertTrue(targets.contains(board.getCell(15, 17)));
		assertTrue(targets.contains(board.getCell(16, 14)));
		assertTrue(targets.contains(board.getCell(16, 16)));
		assertTrue(targets.contains(board.getCell(16, 18)));
		assertTrue(targets.contains(board.getCell(17, 13)));
		assertTrue(targets.contains(board.getCell(17, 15)));
		assertTrue(targets.contains(board.getCell(17, 17)));
		assertTrue(targets.contains(board.getCell(17, 19)));
		assertTrue(targets.contains(board.getCell(18, 14)));
		assertTrue(targets.contains(board.getCell(18, 16)));
		assertTrue(targets.contains(board.getCell(18,18)));
		assertTrue(targets.contains(board.getCell(19, 15)));
		assertTrue(targets.contains(board.getCell(19, 17)));
		assertTrue(targets.contains(board.getCell(20, 16)));
		
		// test a roll of 4
		board.calcTargets(board.getCell(17, 16), 4);
		targets = board.getTargets();
		// number of cells that should be included
		assertEquals(24, targets.size());
		assertTrue(targets.contains(board.getCell(13, 16)));
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(14, 17)));
		assertTrue(targets.contains(board.getCell(15, 14)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(15, 18)));
		assertTrue(targets.contains(board.getCell(16, 13)));
		assertTrue(targets.contains(board.getCell(16, 15)));
		assertTrue(targets.contains(board.getCell(16, 17)));
		assertTrue(targets.contains(board.getCell(16, 19)));
		assertTrue(targets.contains(board.getCell(17, 12)));
		assertTrue(targets.contains(board.getCell(17, 14)));
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertTrue(targets.contains(board.getCell(17, 20)));
		assertTrue(targets.contains(board.getCell(18, 13)));
		assertTrue(targets.contains(board.getCell(18, 15)));
		assertTrue(targets.contains(board.getCell(18, 17)));
		assertTrue(targets.contains(board.getCell(18, 19)));
		assertTrue(targets.contains(board.getCell(19, 14)));
		assertTrue(targets.contains(board.getCell(19, 16)));
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(20, 15)));
		assertTrue(targets.contains(board.getCell(20, 17)));
		assertTrue(targets.contains(board.getCell(21, 16)));
	}
	
	// DONE
	@Test
	// test to make sure occupied locations do not cause problems
	// marked as RED on spreadsheet
	public void testTargetsOccupied() {
		// test a roll of 4, set 2 target cells to occupied
		board.getCell(9, 7).setOccupied(true);
		board.getCell(11, 7).setOccupied(true);
		board.calcTargets(board.getCell(13, 7), 4);
		board.getCell(9, 7).setOccupied(false);
		board.getCell(11, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		// number of cells that should be included
		assertEquals(14, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(10, 6)));
		assertTrue(targets.contains(board.getCell(10, 8)));
		assertTrue(targets.contains(board.getCell(11, 5)));
		assertTrue(targets.contains(board.getCell(12, 6)));
		assertTrue(targets.contains(board.getCell(12, 8)));
		assertTrue(targets.contains(board.getCell(13, 5)));
		assertTrue(targets.contains(board.getCell(14, 4)));
		assertTrue(targets.contains(board.getCell(14, 6)));
		assertTrue(targets.contains(board.getCell(14, 8)));
		assertTrue(targets.contains(board.getCell(15, 5)));
		assertTrue(targets.contains(board.getCell(15, 7)));
		assertTrue(targets.contains(board.getCell(16, 6)));
		assertTrue(targets.contains(board.getCell(16, 8)));
		assertTrue(targets.contains(board.getCell(17, 7)));
		// ensure occupied cells are not included
		assertFalse(targets.contains(board.getCell(9, 7)));
		assertFalse(targets.contains(board.getCell(11, 7)));
	
		// test a roll of 1, make sure a room can be entered even if occupied
		board.getCell(2, 21).setOccupied(true);
		board.calcTargets(board.getCell(4, 21), 1);
		board.getCell(2, 21).setOccupied(false);
		targets = board.getTargets();
		// number of targets that should be included
		assertEquals(4, targets.size());
		// all cells that should be included
		assertTrue(targets.contains(board.getCell(2, 21)));	// occupied room
		assertTrue(targets.contains(board.getCell(4, 20)));	
		assertTrue(targets.contains(board.getCell(4, 22)));
		assertTrue(targets.contains(board.getCell(5, 21)));	
		
		// test a roll of 1, make sure a room cannot be left if the doorway is occupied (blocked)
		board.getCell(14, 19).setOccupied(true);
		board.calcTargets(board.getCell(15, 21), 1);
		board.getCell(14, 19).setOccupied(false);
		targets = board.getTargets();
		// number of targets that should be included
		assertEquals(0, targets.size());
		// should be redundant, but ensure targets does not included the blocked doorway
		assertFalse(targets.contains(board.getCell(14, 19)));
	}
}
