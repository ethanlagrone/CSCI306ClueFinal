package clueGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
    
    private BoardCell[][] grid;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
	private Map<Character, Room> roomMap;
	private ArrayList<Character> validSpaces;
	private ArrayList<String> rows;
    private int numRows;
    private int numCols;
	private static final char validExtensions[] = {'*', '#', '^', '>', 'v', '<'};
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
    	try {
			loadSetupConfig();
			loadLayoutConfig();
		}
		catch (BadConfigFormatException e) {
			System.out.print(e);
		}
    	targets = new HashSet<>();
    	visited = new HashSet<>();
    	grid = new BoardCell[numRows][numCols];
    	
    	//setup board with cells
    	for(int i = 0; i < numRows; i++) {
    		String cells[] = rows.get(i).split(",");
			for(int j = 0; j < numCols; j++) {
    			grid[i][j] = new BoardCell(i, j);
				if (roomMap.containsKey(cells[j].charAt(0))) {
					grid[i][j].setInRoom(true);
					if (cells[j].length() == 2) {
						if (cells[j].charAt(1) == '*') {
							grid[i][j].setRoomCenter(true);
						}
						else {
							grid[i][j].setLabel(true);
						}
					}
				}
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

	public void loadSetupConfig() throws BadConfigFormatException {
		try (BufferedReader reader = new BufferedReader(new FileReader(setupTxt))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.substring(0, 3).equals("Room")) {
					String[] roomInfo = line.split(", ");
					Room room = new Room();
					room.setName(roomInfo[1]);
					roomMap.put(roomInfo[2].charAt(0), room);
				}
				
				else if (line.substring(0, 4).equals("Space")) {
					String[] spaceInfo = line.split(", ");
					validSpaces.add(spaceInfo[2].charAt(0));
				}

				else if (line.charAt(0) == '/') {
					// ignore
				}

				else {
					throw new BadConfigFormatException();
				}
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}
    }
    
    public void loadLayoutConfig() throws BadConfigFormatException {
    	//couldn't figure how to split up a csv properly, so this worked:
		//source: https://labex.io/tutorials/java-how-to-split-csv-lines-correctly-421487
		String line;
		rows = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(layoutCsv))) {
			while ((line = reader.readLine()) != null) {
				rows.add(line);
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}

		String cells[] = rows.get(0).split(",");
		int expectedNumCols = cells.length;
		for (String row : rows) {
			cells = row.split(",");
			if (cells.length != expectedNumCols) {
				throw new BadConfigFormatException();
			}
			for (String cell : cells) {
				if (!roomMap.containsKey(cell.charAt(0)) && !validSpaces.contains(cell.charAt(0))) {
					throw new BadConfigFormatException();
				}
				else if (cell.length() == 2 && Arrays.asList(validExtensions).contains(cell.charAt(1))) {
					throw new BadConfigFormatException();
				}
				else if (cell.length() > 2 || cell.length() < 1) {
					throw new BadConfigFormatException();
				}
				else {
					continue;
				}
			}
		}


		
		
		/* int rowNum = 1;
		int colNum = 1;
		String line;
		String csvSplit = ",";
		
    	try (BufferedReader br = new BufferedReader(new FileReader(layoutCsv))) {
    		while((line = br.readLine()) != null) {
    			String[] data = line.split(csvSplit);
    			if(rowNum == 1) {
    				colNum = data.length + 1;
    			}
    			rowNum ++;
    		}
    	} 
		catch (IOException e) {
    		System.out.println("Error: " + e);
    		//just doing this for original tests
    		colNum = 4;
    		rowNum = 4;
    	}
    	
    	numRows = rowNum;
    	numCols = colNum;
		*/
    }
    
    public Room getRoom(char room) {
    	return new Room();
    	//stub
    }
    
    public Room getRoom(BoardCell cell) {
    	return new Room();
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
