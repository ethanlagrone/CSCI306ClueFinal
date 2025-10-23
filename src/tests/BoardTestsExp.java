package tests;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTestsExp {
    
    private static Board board;
    
    @BeforeEach
    public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles(null, null);
		board.initialize();
    }

    @Test
    public void testAdjacency() {
    	//case 1
        Set<BoardCell> adjCells = board.getCell(0,0).getAdjList();
        assertEquals(2, adjCells.size());
        assertTrue(adjCells.contains(board.getCell(1, 0)));
        assertTrue(adjCells.contains(board.getCell(0, 1)));

      //case 2
        adjCells = board.getCell(3,0).getAdjList();
        assertEquals(2, adjCells.size());
        assertTrue(adjCells.contains(board.getCell(2, 0)));
        assertTrue(adjCells.contains(board.getCell(3, 1)));

      //case 3
        adjCells = board.getCell(0,3).getAdjList();
        assertEquals(2, adjCells.size());
        assertTrue(adjCells.contains(board.getCell(1, 3)));
        assertTrue(adjCells.contains(board.getCell(0, 2)));

      //case 4
        adjCells = board.getCell(3,3).getAdjList();
        assertEquals(2, adjCells.size());
        assertTrue(adjCells.contains(board.getCell(2, 3)));
        assertTrue(adjCells.contains(board.getCell(3, 2)));

      //case 5
        adjCells = board.getCell(1,1).getAdjList();
        assertEquals(4, adjCells.size());
        assertTrue(adjCells.contains(board.getCell(0, 1)));
        assertTrue(adjCells.contains(board.getCell(2, 1)));
        assertTrue(adjCells.contains(board.getCell(1, 0)));
        assertTrue(adjCells.contains(board.getCell(1, 2)));

      //case 6
        adjCells = board.getCell(0,1).getAdjList();
        assertEquals(3, adjCells.size());
        assertTrue(adjCells.contains(board.getCell(0, 2)));
        assertTrue(adjCells.contains(board.getCell(1, 1)));
        assertTrue(adjCells.contains(board.getCell(0, 0)));
    }

    @Test
    public void testTargetsNormal() {
    	//case 1
    	board.calcTargets(board.getCell(0, 0), 1);
        Set<BoardCell> targets = board.getTargets();
        assertEquals(2, targets.size());
        assertTrue(targets.contains(board.getCell(1, 0)));
        assertTrue(targets.contains(board.getCell(0,1)));
        
      //case 2
        board.calcTargets(board.getCell(0, 0), 2);
        targets = board.getTargets();
        assertEquals(3, targets.size());
        assertTrue(targets.contains(board.getCell(2, 0)));
        assertTrue(targets.contains(board.getCell(0,2)));
        assertTrue(targets.contains(board.getCell(1,1)));
        
      //case 3
        board.calcTargets(board.getCell(0, 0), 3);
        targets = board.getTargets();
        assertEquals(6, targets.size());
        assertTrue(targets.contains(board.getCell(0, 1)));
        assertTrue(targets.contains(board.getCell(0,3)));
        assertTrue(targets.contains(board.getCell(1,0)));
        assertTrue(targets.contains(board.getCell(1,2)));
        assertTrue(targets.contains(board.getCell(2,1)));
        assertTrue(targets.contains(board.getCell(3,0)));

      //case 4
        board.calcTargets(board.getCell(1, 1), 1);
        targets = board.getTargets();
        assertEquals(4, targets.size());
        assertTrue(targets.contains(board.getCell(0, 1)));
        assertTrue(targets.contains(board.getCell(2, 1)));
        assertTrue(targets.contains(board.getCell(1, 0)));
        assertTrue(targets.contains(board.getCell(1, 2)));

      //case 5
        board.calcTargets(board.getCell(1, 1), 2);
        targets = board.getTargets();
        assertEquals(6, targets.size());
        assertTrue(targets.contains(board.getCell(0, 0)));
        assertTrue(targets.contains(board.getCell(0, 2)));
        assertTrue(targets.contains(board.getCell(1, 3)));
        assertTrue(targets.contains(board.getCell(2, 0)));
        assertTrue(targets.contains(board.getCell(2, 2)));
        assertTrue(targets.contains(board.getCell(3, 1)));

      //case 6
        board.calcTargets(board.getCell(1, 1), 3);
        targets = board.getTargets();
        assertEquals(8, targets.size());
        assertTrue(targets.contains(board.getCell(0, 1)));
        assertTrue(targets.contains(board.getCell(1, 0)));
        assertTrue(targets.contains(board.getCell(0, 3)));
        assertTrue(targets.contains(board.getCell(1, 2)));
        assertTrue(targets.contains(board.getCell(2, 1)));
        assertTrue(targets.contains(board.getCell(2, 3)));
        assertTrue(targets.contains(board.getCell(3, 0)));
        assertTrue(targets.contains(board.getCell(3, 2)));

      //case 7
        board.calcTargets(board.getCell(1, 1), 4);
        targets = board.getTargets();
        assertEquals(7, targets.size());                       
        assertTrue(targets.contains(board.getCell(0, 0)));    
        assertTrue(targets.contains(board.getCell(0, 2)));
        assertTrue(targets.contains(board.getCell(1, 3)));
        assertTrue(targets.contains(board.getCell(2, 0)));
        assertTrue(targets.contains(board.getCell(2, 2)));
        assertTrue(targets.contains(board.getCell(3, 1)));
        assertTrue(targets.contains(board.getCell(3, 3)));
    }
    
    
    
    
    //Dont worry about this, I misunderstood the last assignment but it should still be useful I think lol
    //Delete this line before turn in
    /*
    @Test
    public void testTargetsRoom() {
    	//case 1
    	board.getCell(1, 1).setInRoom(true);
    	
    	board.calcTargets(board.getCell(4, 1), 3);
    	Set<BoardCell> targets = board.getTargets();
    	assertTrue(targets.contains(board.getCell(1, 1)));
    	assertTrue(!targets.contains(board.getCell(2, 1)));

    	
    	board.getCell(1, 1).setInRoom(false);
    	
    	
    	//case 2
    	board.getCell(2, 1).setInRoom(true);
    	
    	board.calcTargets(board.getCell(1, 1), 4);
    	targets = board.getTargets();
    	assertTrue(!targets.contains(board.getCell(2, 1))); //check not
    	assertTrue(targets.contains(board.getCell(1, 1)));  //check yes
    }
    
    
    @Test
    public void testTargetsOccupied() {
    	//case 1
    	board.getCell(1, 1).setOccupied(true);
    	board.calcTargets(board.getCell(0, 0), 2);
    	Set<BoardCell> targets = board.getTargets();
    	assertTrue(!targets.contains(board.getCell(0, 0)));
    	assertTrue(targets.contains(board.getCell(1, 1)));
    	
    	board.getCell(1, 1).setOccupied(false);
    	
    	
    	//case 2
    	board.getCell(1, 3).setOccupied(true);
    	board.calcTargets(board.getCell(1, 3), 3);
    	targets = board.getTargets();
    	assertTrue(!targets.contains(board.getCell(1, 2)));
    	assertTrue(targets.contains(board.getCell(1, 3)));
    }
    
    
    @Test
    public void testTargetsMixed() {
    	//case 1
    	board.getCell(1, 1).setInRoom(true);
    	board.getCell(1, 2).setOccupied(true);
    	
    	board.calcTargets(board.getCell(0, 0), 2);
    	Set<BoardCell> targets = board.getTargets();
    	assertTrue(targets.contains(board.getCell(1, 1)));  //room
    	assertTrue(targets.contains(board.getCell(1, 2)));  //occupied
    	assertTrue(targets.contains(board.getCell(1, 3)));	//neither
    	
    	
    	board.getCell(1, 1).setInRoom(false);
    	board.getCell(1, 2).setOccupied(false);
    	
    	
    	//case 2
    	board.getCell(2, 1).setInRoom(true);
    	board.getCell(2, 2).setOccupied(true);
    	
    	board.calcTargets(board.getCell(0, 0), 3);
    	targets = board.getTargets();
    	assertTrue(targets.contains(board.getCell(2, 1)));  //room
    	assertTrue(targets.contains(board.getCell(2, 2)));  //occupied
    	assertTrue(targets.contains(board.getCell(2, 3)));
    }
   
    */
}
