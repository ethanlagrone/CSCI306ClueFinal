package clueGame;

import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
    
    private int row;
    private int column;
    private boolean inRoom = false;
    private boolean occupied = false;
    private Set<BoardCell> adjCells;
    private boolean isDoorway;
    private boolean isLabel;
    private boolean isRoomCenter;

    public BoardCell(int row, int column) {
        this.row = row;
        this.column = column;
        //not needing sort so may as well use hashset
        adjCells = new HashSet<BoardCell>();
    }
    
   
    public DoorDirection getDoorDirection() {
    	return DoorDirection.UP;
    	//stub
    }

	public char getSecretPassage(){
		return 'C';
		//stub
	}
	
	
    public void addAdjacency(BoardCell cell) {
        adjCells.add(cell);
    }

    public Set<BoardCell> getAdjList() {
        return adjCells;
    }

    
    
    //getters and setters
    public boolean isLabel() {
		return isLabel;
	}

	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	
	
    public void setDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}



	public void setLabel(boolean isLabel) {
		this.isLabel = isLabel;
	}



	public void setRoomCenter(boolean isRoomCenter) {
		this.isRoomCenter = isRoomCenter;
	}



	public boolean isDoorway() {
    	return isDoorway;
    }
    
    public void setInRoom(boolean b) {
        inRoom = b;
    }

    public boolean isInRoom() {
        return inRoom;
    }

    public void setOccupied(boolean b) {
        occupied = b;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
