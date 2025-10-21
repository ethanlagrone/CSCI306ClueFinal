package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Board {
    
    private BoardCell[][] grid;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
    private int numRows;
    private int numCols;
    private static Board theInstance = new Board();
    private String layoutCsv;
    private String setupTxt;
    
    public Board() {
    	super();
    }
    
    
    public static Board getInstance() {
    	return theInstance;
    }
    
    
    public void initialize() {
    	targets = new HashSet<>();
    	visited = new HashSet<>();
    	grid = new BoardCell[numRows][numCols];
    	
    	//setup board with cells
    	for(int i = 0; i < numRows; i++) {
    		for(int j = 0; j < numCols; j++) {
    			grid[i][j] = new BoardCell(i, j);
    		}
    	}
    	
    	//find every adjacency of each cell, hint said to do it here
    	for(int i = 0; i < numRows; i++) {
    		for(int j = 0; j < numCols; j++) {
    			BoardCell cell = grid[i][j];
    			
    			if(i - 1 >= 0) {
    				cell.addAdjacency(grid[i-1][j]);
    			}
    			if(i + 1 < numRows) {
    				cell.addAdjacency(grid[i+1][j]);
    			}
    			if(j - 1 >= 0) {
    				cell.addAdjacency(grid[i][j-1]);
    			}
    			if(j+1 < numCols) {
    				cell.addAdjacency(grid[i][j+1]);
    			}
    		}
    	}
    }
    
    
    public void setConfigFiles(String csv, String setup) {
    	layoutCsv = csv;
    	setupTxt = setup; 
    }

	public void loadSetupConfig() {
    	//stub
    }
    
    public void loadLayoutConfig() {
    	//stub
    }
    
    public Room getRoom(char room) {
    	return null;
    	//stub
    }
    
    public Room getRoom(BoardCell cell) {
    	return null;
    	//stub
    }
    
    
    public void calcTargets(BoardCell startCell, int numSteps) {
    	//source: slides from clue path walkthrough
		for(BoardCell adjCell : startCell.getAdjList()) {
    		if(visited.contains(adjCell)) {
    			//do nothing
    		} else {
    			visited.add(adjCell);
    			if(numSteps == 1) {
					targets.add(adjCell);
    			} else {
    				calcTargets(adjCell, numSteps-1);
    			}
				visited.remove(adjCell);
    		}
    	}
    }

    public BoardCell getCell(int row, int column) {
    	return grid[row][column];
    }

    public Set<BoardCell> getTargets(BoardCell startCell, int numSteps) {
		targets.clear();
		visited.clear();
		visited.add(startCell);
		calcTargets(startCell, numSteps);
		return targets;
    }
    
    public int getNumColumns() {
 		return numCols;
 	}


 	public int getNumRows() {
 		return numRows;
 	}

}
