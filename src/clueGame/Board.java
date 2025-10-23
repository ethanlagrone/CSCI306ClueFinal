package clueGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {
    
    private BoardCell[][] grid;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
	private Map<Character, Room> roomMap;
	private ArrayList<String> rows;
    private int numRows;
    private int numCols;
	private static final char validExtensions[] = {'*', '#', '^', '>', 'v', '<'};
	//private static final char doors[] = {'^', '>', 'v', '<'};
    private static Board theInstance = new Board();
    private String layoutCsv;
    private String setupTxt;
    
    public Board() {
    	super();
    	roomMap = new HashMap<>();
		targets = new HashSet<>();
		visited = new HashSet<>();
    }
    
    
    public static Board getInstance() {
    	return theInstance;
    }
    
    
    public void initialize() {
    	//makes dummy 4x4 board for tests
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}
		catch (BadConfigFormatException e) {
			System.out.print(e);
		}

		grid = new BoardCell[numRows][numCols];
		
		//setup board with cells
		char roomChar;
		char specialChar;
		for(int i = 0; i < numRows; i++) {
			String cells[] = rows.get(i).split(",");
			for(int j = 0; j < numCols; j++) {
				grid[i][j] = new BoardCell(i, j);
				roomChar = cells[j].charAt(0);
				if (roomMap.containsKey(roomChar)) {
					grid[i][j].setInRoom(true);
					grid[i][j].setRoom(roomMap.get(roomChar));
					
					if (cells[j].length() == 2) {
						specialChar = cells[j].charAt(1);
						if (roomMap.containsKey(specialChar)) {
							grid[i][j].setIsSecretPassage(true);
							grid[i][j].setSecretPassage(specialChar);
						}
						else {
							switch (specialChar) {
								case '*':
									grid[i][j].setRoomCenter(true);
									roomMap.get(roomChar).setCenterCell(grid[i][j]);
									break;
								case '#':
									grid[i][j].setLabel(true);
									roomMap.get(roomChar).setLabelCell(grid[i][j]);
									break;
								case '^':
									grid[i][j].setDoorway(true);
									grid[i][j].setDoorDirection(DoorDirection.UP);
									break;
								case '>':
									grid[i][j].setDoorway(true);
									grid[i][j].setDoorDirection(DoorDirection.RIGHT);
									break;
								case 'v':
									grid[i][j].setDoorway(true);
									grid[i][j].setDoorDirection(DoorDirection.DOWN);
									break;
								case '<':
									grid[i][j].setDoorway(true);
									grid[i][j].setDoorDirection(DoorDirection.LEFT);
									break;
							} 
						}
					}
				}
			}
		}
		
		//find every adjacency of each cell, hint said to do it here
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				BoardCell cell = grid[i][j];
				
				if(i-1 >= 0) {
					cell.addAdjacency(grid[i-1][j]);
				}
				if(i+1 < numRows) {
					cell.addAdjacency(grid[i+1][j]);
				}
				if(j-1 >= 0) {
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
				if (line.charAt(0) == '/') {
					continue;
				}
				else {
					String[] spaceInfo = line.split(", ");
					String type = spaceInfo[0];
					String name = spaceInfo[1];
					char label = spaceInfo[2].charAt(0);
					if (type.equals("Room") || type.equals("Space")) {
						Room room = new Room();
						room.setName(name);
						roomMap.put(label, room);
					}
					else {
						throw new BadConfigFormatException();
					}
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

		String regex = ",";
		String firstRow[] = rows.get(0).split(regex);
		String cells[];
		int expectedNumCols = firstRow.length;
		char roomChar;
		char specialChar;
		boolean isExtension = false;
		for (String row : rows) {
			cells = row.split(regex);
			if (cells.length != expectedNumCols) {
				throw new BadConfigFormatException();
			}
			for (String cell : cells) {
				roomChar = cell.charAt(0);
				if (!roomMap.containsKey(roomChar)) {
					throw new BadConfigFormatException();
				}
				else if (cell.length() == 2) {
					specialChar = cell.charAt(1);
					for (char c : validExtensions) {
						if (specialChar == c) {
							isExtension = true;
							break;
						}
					}
					if (!isExtension) {
						throw new BadConfigFormatException();
					}
					else if (!roomMap.containsKey(specialChar)) {
						throw new BadConfigFormatException();
					}
					else {
						continue;
					}
				}
				else if (cell.length() > 2 || cell.length() < 1) {
					throw new BadConfigFormatException();
				}
				else {
					continue;
				}
			}
			numRows = rows.size();
	        numCols = expectedNumCols;
		}
    }
    
    public Room getRoom(char room) {
    	return roomMap.get(room);
    }
    
    public Room getRoom(BoardCell cell) {
    	return cell.getRoom();
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
