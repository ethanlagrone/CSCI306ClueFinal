package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
    
    private BoardCell[][] grid;
    private Set<BoardCell> targets;
    private Set<BoardCell> visited;
	private Map<Character, Room> roomMap;
	private ArrayList<String> rows;
    private int numRows;
    private int numCols;
	private static final char validExtensions[] = {'*', '#', '^', '>', 'v', '<'};
    private static Board theInstance = new Board();
    private String layoutCsv;
    private String setupTxt;
	private ArrayList<Card> deck = new ArrayList<Card>();
    private final String[] weaponCards = {"Guitar", "Piano", "Violin", "Drums", "Saxophone", "Bass"};
    private ArrayList<Player> players = new ArrayList<Player>();
	private Solution solution;
	private Player humanPlayer;
	private Player currentPlayer = null;
	private boolean clickFlag = false;
	private ClueCardsGUI cardGUI;
	private GameControlPanel controlPanel;
	private JFrame jFrame;
    
    
    public Board() {
    	super();
		rows = new ArrayList<>();
    	roomMap = new HashMap<>();
		targets = new HashSet<>();
		visited = new HashSet<>();
		
    }
    
    
    public static Board getInstance() {
    	return theInstance;
    }
    

    public void initialize() {
    	clearBoard();
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
			//load setup files
			try {
				loadSetupConfig();
				loadLayoutConfig();
			}
			catch (BadConfigFormatException e) {
				System.out.print(e);
			}

			// set up board from this setup/layout info
			boardSetup();
		}
		
		//find every adjacency of each cell, hint said to do it here
    	//made helper to split up
		try {
			calculateAdjacencies();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		//Throwing in a mouseListener here
		addMouseListener(this);

	}

	public void prepareCards() {
		//create the deck for the board
		createDeck();
		deal();
	}
    
    
    public boolean checkAccusation(Solution accusation){
    	if(accusation.getPerson() == solution.getPerson() && accusation.getRoom() == solution.getRoom() && accusation.getWeapon() == solution.getWeapon()) {
    		return true;
    	} else {
        	return false;
    	}
    }
    
    
    public Card handleSuggestion(Solution suggestion, Player player) {
    	Card shownCard;
    	int index = players.indexOf(player);
    	int startIndex = index;
    	int currentIndex;
    	if(index == players.size()-1) {
    		currentIndex = 0;
    	} else {
    		currentIndex = (index + 1);
    	}
    	 

    	while (currentIndex != startIndex) {
    	    Player nextPlayer = players.get(currentIndex);
    	    shownCard = nextPlayer.disproveSuggestion(suggestion);
    	    if (shownCard != null) {
    	        return shownCard;
    	    }
    	    
    	    currentIndex ++;
    	    if(currentIndex == players.size()) {
    	    	currentIndex = 0;
    	    }
    	}
    	return null;
    }
    
    
    public void createDeck() {
    	//add all the weapons to the deck
    	for(String weapon : weaponCards) {
    		deck.add(new Card(weapon, CardType.WEAPON));
    	}
    	
    	//add all the rooms to the deck
    	Set<String> roomNames = new HashSet<>();
        for (Room room : roomMap.values()) {
            String name = room.getName();
            if (!name.equals("Walkway") && !name.equals("Unused")) {
                roomNames.add(name);
            }
        }

        for (String name : roomNames) {
            deck.add(new Card(name, CardType.ROOM));
        }
        
        //ADD PEOPLE CARDS
        for (Player player : players) {
        	deck.add(new Card(player.getName(), CardType.PERSON));
        }
    }
    
    
    public void setSolution(Solution solution) {
		this.solution = solution;
	}


	public void deal() {
    	Card solutionRoom = null;
		Card solutionPerson = null;
		Card solutionWeapon = null;
		Card currentCard = null;
		boolean solutionMade = false;
		while (!solutionMade) {
			if (solutionRoom != null && solutionPerson != null && solutionWeapon != null) {
				solutionMade = true;
			}
			else {
				currentCard = deck.get((int)(Math.random() * deck.size()));
				switch (currentCard.getCardType()) {
					case CardType.ROOM:
						if (solutionRoom == null) {
							solutionRoom = currentCard;
						}
						break;
					case CardType.PERSON:
						if (solutionPerson == null) {
							solutionPerson = currentCard;
						}
						break;
					case CardType.WEAPON:
						if (solutionWeapon == null) {
							solutionWeapon = currentCard;
						}
						break;
				}
			}
		}
		try {
			solution = new Solution(solutionRoom, solutionPerson, solutionWeapon);
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		Collections.shuffle(deck);
		int currentPlayer = 0;
		//int loop = 0;
		for (Card c : deck) {
			//loop++;
			//System.out.println("Loop: " + loop + ", Player: " + currentPlayer);
			if (c.equals(solutionRoom) || c.equals(solutionPerson) || c.equals(solutionWeapon)) {
				continue;
			}
			else if (players.get(currentPlayer).getHand().size() < 3) {
				players.get(currentPlayer).updateHand(c);
			}
			else {
				currentPlayer++;
				players.get(currentPlayer).updateHand(c);
			}
		}
    }
    

    private void calculateAdjacencies() throws BadConfigFormatException {
    	for(int i = 0; i < numRows; i++) {
    		for(int j = 0; j < numCols; j++) {
    			BoardCell cell = grid[i][j];
    			//null checks
    			if(cell != null && cell.getRoom() != null) {
	    			//ROOM LOGIC
	    			if(!isWalkway(cell)) {
	    				//ONLY HANDLING SECRET PASSAGE
	    				if(cell.isSecretPassage()) {
	    					//CONNECTS ROOM CENTERS
	    					Room targetRoom = roomMap.get(cell.getSecretPassage());
	                        if(targetRoom != null && targetRoom.getCenterCell() != null) {
	                        	BoardCell targetCenter = targetRoom.getCenterCell();
	                            cell.getRoom().getCenterCell().addAdjacency(targetCenter);
	                            targetCenter.addAdjacency(cell.getRoom().getCenterCell());
	                        }
	    				}
	    			//DOORWAY CELL LOGIC
	    			} else if(isWalkway(cell) && cell.isDoorway()) {
	    				switch(cell.getDoorDirection()) {
	    				//CHECK DOOR DIRECTION AND SET THE ADJACENT CELL TO ROOM CENTER
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
	    						break;
	    				}	
	    				//after door direction found, find the other adjacency of the cell
	    				findWalkwayAdjacency(cell, i, j);
	    				
	    			//WALKWAY CELLS THAT ARENT DOORWAYS
	    			} else {
	    				findWalkwayAdjacency(cell, i, j);
	    			}
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
			// set up line string for reading txt line by line
			String line;
			while ((line = reader.readLine()) != null) {
				// ignore if line begins with //
				if (line.charAt(0) == '/') {
					continue;
				}
				else {
					// split with delimiter ", " to get type, name, and label from an entry
					String[] setupInfo = line.split(", ");
					String type = setupInfo[0];

					// if valid type of room (proper room or space)
					if (type.equals("Room") || type.equals("Space")) {
						// create new room, set name, add to room map
						String name = setupInfo[1];
						char label = setupInfo[2].charAt(0);
						
						Room room = new Room();
						room.setName(name);
						roomMap.put(label, room);
					} else if(type.equals("Player")) {
						//setup players
						String name = setupInfo[1];
						String color = setupInfo[2];
						int xCoordinate = Integer.parseInt(setupInfo[3]);
						int yCoordinate = Integer.parseInt(setupInfo[4]);
						if(name.equals("D'Angelo")) {
							humanPlayer = new HumanPlayer(name, color, xCoordinate, yCoordinate);
							players.add(humanPlayer);
						} else {
							players.add(new ComputerPlayer(name, color, xCoordinate, yCoordinate));
						}
						
					}
					// throw exception if not valid type of room
					else {
						throw new BadConfigFormatException("Invalid room type found in setup txt file: " + type);
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

		// set up line string for reading csv line by line
		String line;
		try (BufferedReader reader = new BufferedReader(new FileReader(layoutCsv))) {
			// add line from the csv to the ArrayList of rows
			while ((line = reader.readLine()) != null) {
				rows.add(line);
			}
		}
		catch (IOException e) {
			System.out.println(e);
		}

		// get the first row and add each of its individual spaces to an array
		// the length of this array becomes the expected length of every subsequent array
		String regex = ",";
		String cells[] = rows.get(0).split(regex);
		int expectedNumCols = cells.length;
		char roomChar;
		char specialChar;
		for (String row : rows) {
			cells = row.split(regex);
			// check if row has the correct number of spaces
			if (cells.length != expectedNumCols) {
				throw new BadConfigFormatException("Found row in layout csv file containing more columns than expected.");
			}
			for (String cell : cells) {
				roomChar = cell.charAt(0);
				// if cell doesn't correspond to one of the rooms/spaces in the setup file, throw an exception
				if (!roomMap.containsKey(roomChar)) {
					throw new BadConfigFormatException("Found cell in layout csv file corresponding to invalid room: " + cell);
				}
				// incorrect special space handling
				else if (cell.length() == 2) {
				    specialChar = cell.charAt(1);
				    boolean isExtension = false;
				    // check if the extension is valid (door, room center, room label)
					for (char c : validExtensions) {
				        if (specialChar == c) {
				            isExtension = true;
				            break;
				        }
				    }
				    // if extension is not valid (or another room for a secret passage), throw an exception
					if (!isExtension && !roomMap.containsKey(specialChar)) {
				        throw new BadConfigFormatException("Found cell in layout csv file with an invalid extension: " + cell);
				    }
				}
				// if number of characters in the cell is not 1 or 2, throw an exception
				else if (cell.length() > 2 || cell.length() < 1) {
					throw new BadConfigFormatException("Invalid cell format found in layout csv file: " + cell);
				}
				// normal cell, no action needed
				else {
					continue;
				}
			}
			
			// set number of rows and columns accordingly
			numRows = rows.size();
	        numCols = expectedNumCols;
		}
    }

	public void boardSetup() {
		grid = new BoardCell[numRows][numCols];
		
		//setup board with cells
		char roomChar;
		char specialChar;
		String cells[];
		for(int i = 0; i < numRows; i++) {
			cells = rows.get(i).split(",");
			for(int j = 0; j < numCols; j++) {
				grid[i][j] = new BoardCell(i, j);
				BoardCell currentCell = grid[i][j];
				String currentCellLabel = cells[j];
				roomChar = currentCellLabel.charAt(0);
				//finds if cell is a room and sets it accordingly
				if (roomMap.containsKey(roomChar)) {
					currentCell.setInRoom(true);
					currentCell.setRoom(roomMap.get(roomChar));
					if(isWalkway(currentCell)) {
						currentCell.setInRoom(false);
					}
					//check that there is two characters in a cell to know that the room is special
					if (cells[j].length() == 2) {
						specialChar = currentCellLabel.charAt(1);
						//checks initial char to see if it is another letter making it a secret passage
						if (roomMap.containsKey(specialChar)) {
							currentCell.setIsSecretPassage(true);
							currentCell.setSecretPassage(specialChar);
						}
						else {
							//CHECK SPECIAL CHARACTER AND SET ACCORDINGLY
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
    			//do nothing fix for ADJtest when room is occupied.
    		} else {
    	        visited.add(adjCell);
        		if(adjCell.isInRoom() || numSteps == 1) {
        			targets.add(adjCell);
					adjCell.setIsTarget(true);
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
 	
 	public boolean isWalkway(BoardCell cell) {
 		if(cell != null && cell.getRoom().getName().equals("Walkway")) {
 			return true;
 		} else {
 			return false;
 		}
 	}
 	
 	public boolean isAdjacentWalkway(BoardCell cell) {
 		if(cell != null && !cell.isInRoom()) {
 			return true;
 		} else {
 	 		return false;
 		}
 	}
 	
 	public void findWalkwayAdjacency(BoardCell cell, int i, int j) {
 		if(i-1 >= 0 && isAdjacentWalkway(grid[i-1][j])) {
            cell.addAdjacency(grid[i-1][j]);
        }
        if(i+1 < numRows && isAdjacentWalkway(grid[i+1][j])) {
            cell.addAdjacency(grid[i+1][j]);
        }
        if(j-1 >= 0 && isAdjacentWalkway(grid[i][j-1])) {
            cell.addAdjacency(grid[i][j-1]);
        }
        if(j+1 < numCols && isAdjacentWalkway(grid[i][j+1])) {
            cell.addAdjacency(grid[i][j+1]);
        }
 	}
 	
 	public ArrayList<Card> getDeck() {
		return deck;
	}


	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}


	public ArrayList<Player> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}


	public String[] getWeaponCards() {
		return weaponCards;
	}

	public Solution getSolution() {
		return solution;
	}
 	
 	public void clearBoard() {
 	    roomMap.clear();
 	    rows.clear();
 	    targets.clear();
 	    visited.clear();
 	    grid = null;
 	    numRows = 0;
 	    numCols = 0;
 	    deck.clear();
		players.clear();
		solution = null;
 	}

	public Player getHumanPlayer() {
		return humanPlayer;
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean turnProgressable() {
		if (currentPlayer.isHuman()) {
			if (!currentPlayer.getTurnDone()) {
				JOptionPane.showMessageDialog(null, 
				"Please finish your turn first.",
				"Error",
				JOptionPane.WARNING_MESSAGE);
				return false;
			}
			else {
				return true;
			}	
		}
		else {
			if(currentPlayer.getSeen().size() == 18) {
				JOptionPane.showMessageDialog(null, 
						currentPlayer.getName() + " has accused correctly and won the game!\nThe solution was " 
							+ solution.getPerson().getCardName() + " in " + solution.getRoom().getCardName() + " with the " + solution.getWeapon().getCardName(),
						"Game Over!",
						JOptionPane.INFORMATION_MESSAGE);
						jFrame.dispose();
			}
			return true;
		}
	}

	public void progressTurn() {
		if (players.getLast().getName().equals(currentPlayer.getName())) {
			currentPlayer.setTurnDone(false);
			currentPlayer = players.getFirst();
		}
		else {
			currentPlayer.setTurnDone(false);
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).getName().equals(currentPlayer.getName())) {
					currentPlayer = players.get(i + 1);
					break;
				}
			}
		}
	}

	public void movePlayer(int roll) {
		//clean up Targets, was having problems with every player's light blue squares showing up
		for (int i = 0; i < numRows; i++) {
		    for (int j = 0; j < numCols; j++) {
		        grid[i][j].setIsTarget(false);
		    }
		}

		targets.clear();

		int playerRow = currentPlayer.getRow();
		int playerColumn = currentPlayer.getColumn();
		calcTargets(grid[playerRow][playerColumn], roll);
		if(currentPlayer.isMoved()) {
			targets.add(getCell(currentPlayer.getRow(),currentPlayer.getColumn()));
			currentPlayer.setMoved(false);
		}
		//if player is a human, set all their targets to true and repaint, 
		//then set clickFlag to true to open up the listener
		if (currentPlayer.isHuman()) {
			for(BoardCell cell: targets) {
				cell.setIsTarget(true);
			}
			repaint();
			clickFlag = true;
		} else {
			if (deck.size() - currentPlayer.getSeen().size() == 3) {
				JOptionPane.showMessageDialog(null, 
				currentPlayer.getName() + " has accused correctly and won the game!\nThe solution was " 
					+ solution.getPerson().getCardName() + " in " + solution.getRoom().getCardName() + " with the " + solution.getWeapon().getCardName(),
				"Game Over!",
				JOptionPane.INFORMATION_MESSAGE);
				jFrame.dispose();
			}
			
			// this looks really dumb, we could probably just change selectTarget to be a void method, but then all the old tests fail lol
			
			BoardCell newCell = currentPlayer.selectTarget(targets);
			targets.clear();
			currentPlayer.setRow(newCell.getRow());
			currentPlayer.setColumn(newCell.getColumn());
			
			//create cpu suggestion, and disprove it if it can be disproved
			if (newCell.isInRoom()) {
				Solution cpuSuggestion = currentPlayer.createSuggestion(newCell.getRoom(), deck);
				controlPanel.setGuess(cpuSuggestion, currentPlayer);
				for(Player p : players) {
					if(p.getName().equals(cpuSuggestion.getPerson().getCardName())) {
						p.setColumn(currentPlayer.getColumn());
						p.setRow(currentPlayer.getRow());
						p.setMoved(true);
					}
				}
				//disprove suggestion starting at currentPlayer index, not sure how to do that loop
				int currentIndex = players.indexOf(currentPlayer);
                boolean cardShown = false;

                for (int i = 1; i < players.size(); i++) {
                    int nextPlayerIndex = (currentIndex + i) % players.size();
                    Player nextPlayer = players.get(nextPlayerIndex);
                    // Don't have AI disprove its own suggestion
                    if (nextPlayer != currentPlayer) {
                        Card disprovedCard = nextPlayer.disproveSuggestion(cpuSuggestion);
                        if (disprovedCard != null) {
                            currentPlayer.updateSeen(disprovedCard);
                            cardShown = true;
                            controlPanel.setGuessResult(nextPlayer);
                            break;
                        }
                    }
                }
                
                currentPlayer.setTurnDone(true);
                if(!cardShown) {
                	
                }
			} else {
				//System.out.println("not in room");
				currentPlayer.setTurnDone(true);
			}
				
			repaint();
		}
	}

	// DRAWING
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		
		// calculate important values
		int width = getWidth();
		int height = getHeight();
		int cellWidth = width / numRows;
		int cellHeight = height / numCols;
		int xOffset;
		int yOffset;
		
		// draw cells
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				xOffset = j * cellWidth;
				yOffset = i * cellHeight;
				if (currentPlayer == null || !currentPlayer.isHuman()) {
					grid[i][j].draw(xOffset, yOffset, cellWidth, cellHeight, g, false);
				}
				else {
					grid[i][j].draw(xOffset, yOffset, cellWidth, cellHeight, g, true);
				}
			}
		}

		// loop again for text labels to ensure they are in the foreground
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (grid[i][j].isLabel()) {
					grid[i][j].drawLabel(j * cellWidth, i * cellHeight, cellWidth, cellHeight, g);
				}
			}
		}

		// draw players
		for (int i = 0; i < players.size(); i++) {
			int playerRow = players.get(i).getRow();
			int playerColumn = players.get(i).getColumn();
			xOffset = playerColumn * cellWidth;
			yOffset = playerRow * cellHeight;
			g.setColor(players.get(i).getColorCode());
			g.fillOval(xOffset, yOffset, cellWidth, cellHeight);
			g.setColor(Color.BLACK);
			g.drawOval(xOffset, yOffset, cellWidth, cellHeight);
		}
    }


	@Override
	public void mouseClicked(MouseEvent e) {
		int cellWidth = getWidth() / numRows;
		int cellHeight = getHeight() / numCols;
		int clickedX = e.getX();
		int clickedY = e.getY();
		
		//check if currentPlayer is human and if the board is accepting clicks, otherwise we shouldn't worry about it
		if((getCurrentPlayer().isHuman()) && (clickFlag)) {
			
			int cellClickedColumn = clickedX / cellWidth;
			int cellClickedRow = clickedY / cellHeight;
			BoardCell clicked = getCell(cellClickedRow, cellClickedColumn);
			//check if targets contains the cell being clicked, else throw an error message
			if(clicked.isOccupied() && clicked.getRoom().getName().equals("Walkway")) {
				JOptionPane.showMessageDialog(null, "Please click an unoccupied square!", "Error", JOptionPane.INFORMATION_MESSAGE);
			} else if(targets.contains(getCell(cellClickedRow, cellClickedColumn))) {
				currentPlayer.setColumn(cellClickedColumn);
				currentPlayer.setRow(cellClickedRow);
				if (clicked.isInRoom()) {
					String roomName = clicked.getRoom().getName();
			        suggestionGUI(roomName, currentPlayer);
			    	currentPlayer.setTurnDone(true);
			        clickFlag = false;
			    }
			} else {
				//Copied from ClueGame.java, shows error message if you click the wrong one.
				JOptionPane.showMessageDialog(null, "You should click a light blue square!", "Error", JOptionPane.INFORMATION_MESSAGE);
			}
			
			 if(clicked.isOccupied() && clicked.getRoom().getName().equals("Walkway")) {
		    	//wait 
		    } else if(!targets.contains(getCell(cellClickedRow, cellClickedColumn))){
		    	//wait
		    } else {
		    	currentPlayer.setTurnDone(true);
		        clickFlag = false;
		    }
			
			

		} 
		repaint();
	}

	
	public void suggestionGUI(String roomName, Player player) {
		//idk yet
		//System.out.println("Human makes suggestion");
		
		JDialog dialog = new JDialog();
	    dialog.setTitle("Make a Suggestion");
	    dialog.setModal(true);
	    
		JPanel panel = new JPanel(new GridLayout(4,1));
		
		ArrayList<String> rooms = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i<deck.size(); i++) {
			Card current = deck.get(i);
			if(current.getCardType() == CardType.ROOM) {
				rooms.add(current.getCardName());
			} else if (current.getCardType() == CardType.PERSON) {
				names.add(current.getCardName());
			}
		}
		
		//must convert arrayList to array for JComboBoxes
		String[] roomsArray = rooms.toArray(new String[0]);
		String[] namesArray = names.toArray(new String[0]);
		
		//Make JCombo Boxes
		JComboBox<String> weaponBox = new JComboBox<String>(weaponCards);
		JComboBox<String> roomBox = new JComboBox<String>(roomsArray);
		JComboBox<String> nameBox = new JComboBox<String>(namesArray);
		
		panel.add(new JLabel("Current Room"));
		panel.add(new JLabel(roomName));
		panel.add(new JLabel("Weapon"));
		panel.add(weaponBox);
		panel.add(new JLabel("People: "));
		panel.add(nameBox);
		
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel"); 
        
        submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedWeapon = (String) weaponBox.getSelectedItem();
				String selectedPerson = (String) nameBox.getSelectedItem();
				
				Card weapon = null;
				Card person = null;
				Card room = null;
				
				for(int i = 0; i < deck.size(); i++) {
					Card current = deck.get(i);
					if(current.getCardName().equals(selectedWeapon)) {
						weapon = current;
					} else if (current.getCardName().equals(selectedPerson)) {
						person = current;
						for(Player p : players) {
							if(p.getName().equals(selectedPerson)) {
								p.setColumn(currentPlayer.getColumn());
								p.setRow(currentPlayer.getRow());
							}
						}
					} else if (current.getCardName().equals(roomName)) {
						room = current;
					}
				}
				
				
				
				Solution suggestion = null;
				try {
					suggestion = new Solution(room, person, weapon);
				} catch (BadConfigFormatException e1) {
					e1.printStackTrace();
				}
				if(suggestion != null) {
					int currentIndex = players.indexOf(currentPlayer);
					controlPanel.setGuess(suggestion, currentPlayer);

	                for (int i = 1; i < players.size(); i++) {
	                    int nextPlayerIndex = (currentIndex + i) % players.size();
	                    Player nextPlayer = players.get(nextPlayerIndex);
	                    if (nextPlayer != player) {
	                        Card disprovedCard = nextPlayer.disproveSuggestion(suggestion);
	                        if (disprovedCard != null) {
	                            player.updateSeen(disprovedCard);
	                            controlPanel.setGuessResult(nextPlayer);
	                            break;
	                        }
	                    }
	                }
				}
	
				
				player.setTurnDone(true);
			    dialog.dispose();
			    cardGUI.reMakeGUI(players);
			}
        });
        
        cancel.addActionListener(e -> dialog.dispose());
        panel.add(submit);
        panel.add(cancel);

		
        dialog.getContentPane().add(panel, BorderLayout.CENTER);
        
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
	}
	
	public void accusationGUI(Player player) {

		JDialog dialog = new JDialog();
	    dialog.setTitle("Make Accusation");
	    dialog.setModal(true);
	    
		JPanel panel = new JPanel(new GridLayout(4,1));
		
		ArrayList<String> rooms = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i<deck.size(); i++) {
			Card current = deck.get(i);
			if(current.getCardType() == CardType.ROOM) {
				rooms.add(current.getCardName());
			} else if (current.getCardType() == CardType.PERSON) {
				names.add(current.getCardName());
			}
		}
		
		//must convert arrayList to array for JComboBoxes
		String[] roomsArray = rooms.toArray(new String[0]);
		String[] namesArray = names.toArray(new String[0]);
		
		//Make JCombo Boxes
		JComboBox<String> weaponBox = new JComboBox<String>(weaponCards);
		JComboBox<String> roomBox = new JComboBox<String>(roomsArray);
		JComboBox<String> nameBox = new JComboBox<String>(namesArray);
		
		panel.add(new JLabel("Room: "));
		panel.add(roomBox);
		panel.add(new JLabel("Weapon: "));
		panel.add(weaponBox);
		panel.add(new JLabel("People: "));
		panel.add(nameBox);
		
        JButton submit = new JButton("Submit");
        JButton cancel = new JButton("Cancel"); 
        
        submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedWeapon = (String) weaponBox.getSelectedItem();
				String selectedPerson = (String) nameBox.getSelectedItem();
				String selectedRoom = (String) roomBox.getSelectedItem();
				
				Card weapon = null;
				Card person = null;
				Card room = null;
				
				for(int i = 0; i < deck.size(); i++) {
					Card current = deck.get(i);
					if(current.getCardName().equals(selectedWeapon)) {
						weapon = current;
					} else if (current.getCardName().equals(selectedPerson)) {
						person = current;
					} else if (current.getCardName().equals(selectedRoom)) {
						room = current;
					}
				}
				
				Solution accusation = null;
				try {
					accusation = new Solution(room, person, weapon);
				} catch (BadConfigFormatException e1) {
					e1.printStackTrace();
				}
				if(accusation != null) {
					if(solution.equals(accusation)) {
						JOptionPane.showMessageDialog(null, "You won!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
						jFrame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "That was wrong, you lose!", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
						jFrame.dispose();
					}
				}
	
				
				player.setTurnDone(true);
			    dialog.dispose();
			}
        });
        
        cancel.addActionListener(e -> dialog.dispose());
        panel.add(submit);
        panel.add(cancel);

		
        dialog.getContentPane().add(panel, BorderLayout.CENTER);
        
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
	}
	
	public void setCardGUI(ClueCardsGUI newCardGUI) {
		cardGUI = newCardGUI;
	}
	
	public void setControlPanelGUI(GameControlPanel newControlPanel) {
		controlPanel = newControlPanel;
	}
	
	public void setJFrame(JFrame newJFrame) {
		jFrame = newJFrame;
	}
	
	// unneeded MouseListener functions
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
