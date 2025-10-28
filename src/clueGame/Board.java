package clueGame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    	//makes dummy 4x4 board for BoardTestsExp
    	if(layoutCsv == null || setupTxt == null) {
    		numRows = 4;
    		numCols = 4;
			grid = new BoardCell[numRows][numCols];
    		for(int i = 0; i < numRows; i++) {
    			for(int j = 0; j < numCols; j++) {
    				grid[i][j] = new BoardCell(i, j);
    			}
    		}
    		//putting in old adjacent calcs to keep test consistent
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
    	} else {
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
					BoardCell currentCell = grid[i][j];
					roomChar = cells[j].charAt(0);
					if (roomMap.containsKey(roomChar)) {
						currentCell.setInRoom(true);
						currentCell.setRoom(roomMap.get(roomChar));
						if(currentCell.getRoom().getName().equals("Walkway")) {
							currentCell.setInRoom(false);
						}
						if (cells[j].length() == 2) {
							specialChar = cells[j].charAt(1);
							if (roomMap.containsKey(specialChar)) {
								currentCell.setIsSecretPassage(true);
								currentCell.setSecretPassage(specialChar);
							}
							else {
								switch (specialChar) {
									case '*':
										currentCell.setRoomCenter(true);
										roomMap.get(roomChar).setCenterCell(grid[i][j]);
										break;
									case '#':
										currentCell.setLabel(true);
										roomMap.get(roomChar).setLabelCell(grid[i][j]);
										break;
									case '^':
										currentCell.setDoorway(true);
										currentCell.setDoorDirection(DoorDirection.UP);
										break;
									case '>':
										currentCell.setDoorway(true);
										currentCell.setDoorDirection(DoorDirection.RIGHT);
										break;
									case 'v':
										currentCell.setDoorway(true);
										currentCell.setDoorDirection(DoorDirection.DOWN);
										break;
									case '<':
										currentCell.setDoorway(true);
										currentCell.setDoorDirection(DoorDirection.LEFT);
										break;
								} 
							}
						}
					}
				}
			}
		}
		
		//find every adjacency of each cell, hint said to do it here
    	//made helper to split up
		try {
			calculateAdjacencies();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
	}

    private void calculateAdjacencies() throws BadConfigFormatException {
    	for(int i = 0; i < numRows; i++) {
    		for(int j = 0; j < numCols; j++) {
    			BoardCell cell = grid[i][j];
    			//I was still having nullptr issues
    			if (cell == null || cell.getRoom() == null) continue;
    			//just for the sake of avoiding problems lol
    			if(cell != null) {
	    			//not walkway cells
	    			if(!cell.getRoom().getName().equals("Walkway")) {
	    				//to handle here: room center, secretPassage
	    				if(cell.isSecretPassage()) {
	    					//cell needs to add adjacency of the secretPassage so we 
	    					Room targetRoom = roomMap.get(cell.getSecretPassage());
	                        if (targetRoom != null && targetRoom.getCenterCell() != null) {
	                        	BoardCell targetCenter = targetRoom.getCenterCell();
	                            cell.getRoom().getCenterCell().addAdjacency(targetCenter);
	                            targetCenter.addAdjacency(cell.getRoom().getCenterCell());
	                        }
	    				}
	    			//doorway cells
	    			} else if(cell.getRoom().getName().equals("Walkway") && cell.isDoorway()) {
	    				//to  handle here: doorways
	    				switch(cell.getDoorDirection()) {
	    					case UP:
	    						if (i-1 >= 0) {
	                                Room roomUp = grid[i-1][j].getRoom();
	                                if (roomUp.getCenterCell() != null) {
	                                    cell.addAdjacency(roomUp.getCenterCell());
	                                    roomUp.getCenterCell().addAdjacency(cell);
	                                }
	                            }
	    						break;
	    					case DOWN:
	    						if (i+1 < numRows) {
	                                Room roomDown = grid[i+1][j].getRoom();
	                                if (roomDown.getCenterCell() != null) {
	                                    cell.addAdjacency(roomDown.getCenterCell());
	                                    roomDown.getCenterCell().addAdjacency(cell);
	                                }
	                            }
	    						break;
	    					case RIGHT:
	    						if(j+1 < numCols) {
	                                Room roomRight = grid[i][j+1].getRoom();
	                                if (roomRight.getCenterCell() != null) {
	                                    cell.addAdjacency(roomRight.getCenterCell());
	                                    roomRight.getCenterCell().addAdjacency(cell);
	                                }
	                            }
	    						break;
	    					case LEFT:
	    						if(j-1 >= 0) {
	                                Room roomLeft = grid[i][j-1].getRoom();
	                                if (roomLeft.getCenterCell() != null) {
	                                    cell.addAdjacency(roomLeft.getCenterCell());
	                                    roomLeft.getCenterCell().addAdjacency(cell);
	                                }
	                            }
	    						break;
	    					case NONE:
	    						throw new BadConfigFormatException("Should not be handled here, only doorway cells should be.");
	    				}	
	    				
	    				//Exact same code as below, will refactor at another time, sorry grader
	    				//Find other adjacencies outside of rooms, was having nullptr errors, so threw in checks
	    				if(i-1 >= 0 && grid[i-1][j] != null && !grid[i-1][j].isInRoom()) {
	                        cell.addAdjacency(grid[i-1][j]);
	                    }
	                    if(i+1 < numRows && grid[i+1][j] != null && !grid[i+1][j].isInRoom()) {
	                        cell.addAdjacency(grid[i+1][j]);
	                    }
	                    if(j-1 >= 0 && grid[i][j-1] != null && !grid[i][j-1].isInRoom()) {
	                        cell.addAdjacency(grid[i][j-1]);
	                    }
	                    if(j+1 < numCols && grid[i][j+1] != null && !grid[i][j+1].isInRoom()) {
	                        cell.addAdjacency(grid[i][j+1]);
	                    }
	    			//walkway cells not doorway
	    			} else {
	    				//handle non-door walkways
	    				if(i-1 >= 0 && grid[i-1][j] != null && !grid[i-1][j].isInRoom()) {
	                        cell.addAdjacency(grid[i-1][j]);
	                    }
	                    if(i+1 < numRows && grid[i+1][j] != null && !grid[i+1][j].isInRoom()) {
	                        cell.addAdjacency(grid[i+1][j]);
	                    }
	                    if(j-1 >= 0 && grid[i][j-1] != null && !grid[i][j-1].isInRoom()) {
	                        cell.addAdjacency(grid[i][j-1]);
	                    }
	                    if(j+1 < numCols && grid[i][j+1] != null && !grid[i][j+1].isInRoom()) {
	                        cell.addAdjacency(grid[i][j+1]);
	                    }
	    			}
	    		}
    		}
    	}
    	
    	for (Room room : roomMap.values()) {
    	    BoardCell center = room.getCenterCell();
    	    if (center == null) continue;
    	    for(int i = 0; i < numRows; i++) {
    	        for (int j = 0; j < numCols; j++) {
    	            BoardCell cell = grid[i][j];
    	            if (cell.isDoorway() && cell.getRoom() == room) {
    	                center.addAdjacency(cell);
    	                cell.addAdjacency(center);
    	            }
    	        }
    	    }
    	}
    	
    	/*
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
		} original adj calcs
		*/
    	
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
				    boolean isExtension = false;
				    for (char c : validExtensions) {
				        if (specialChar == c) {
				            isExtension = true;
				            break;
				        }
				    }
				    if (!isExtension && !roomMap.containsKey(specialChar)) {
				        throw new BadConfigFormatException();
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
    	//I think it was clearing everytime lol
    	visited.clear();
    	targets.clear();
    	//this cell was being included in the file, thank god for debugger
    	visited.add(startCell);
    	//created this because things were clearing over and over again and idk how else to do it
		recursiveCalcTargets(startCell, numSteps);
    }
    
    public void recursiveCalcTargets(BoardCell startCell, int numSteps) {
    	//source: slides from clue path walkthrough
    	for(BoardCell adjCell : startCell.getAdjList()) {
    		if(visited.contains(adjCell)) {
    			//continue
    		} else if(adjCell.isOccupied() && !adjCell.isInRoom()){
    			//do nothing
    		} else {
    	        visited.add(adjCell);
        		if(adjCell.isInRoom() || numSteps == 1) {
        			targets.add(adjCell);
        		} else {
        			recursiveCalcTargets(adjCell, numSteps-1);
        		}
    			visited.remove(adjCell);
    	    }
    	}
    }

    public BoardCell getCell(int row, int column) {
    	return grid[row][column];
    }

    public Set<BoardCell> getTargets() {
		return targets;
    }
    
    public int getNumColumns() {
 		return numCols;
 	}

    public Set<BoardCell> getAdjList(int row, int col) {
        return grid[row][col].getAdjList();
    }

 	public int getNumRows() {
 		return numRows;
 	}

}
