package clueGame;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
    
    private int row;
    private int column;
    private boolean inRoom = false;
    private boolean occupied = false;
    private Set<BoardCell> adjCells;
    private boolean isDoorway = false;
    private boolean isLabel = false;
    private boolean isRoomCenter = false;
    private boolean isSecretPassage = false;
    private DoorDirection doorDirection = DoorDirection.NONE;
    private Room room;
    private char secretPassage;


	public BoardCell(int row, int column) {
        this.row = row;
        this.column = column;
        //not needing sort so may as well use hashset
        adjCells = new HashSet<BoardCell>();
    }
	
    public void addAdjacency(BoardCell cell) {
        adjCells.add(cell);
    }

    public Set<BoardCell> getAdjList() {
        return adjCells;
    }

    
    
    //getters and setters
    public Room getRoom() {
		return room;
	}


	public void setRoom(Room room) {
		this.room = room;
	}
	
    public DoorDirection getDoorDirection() {
    	return doorDirection;
    }
    
    public void setDoorDirection(DoorDirection doorDirection) {
    	this.doorDirection = doorDirection;
    }
    
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

    public void setSecretPassage(char secretPassage) {
        this.secretPassage = secretPassage;
    }
    
    public char getSecretPassage() {
		return secretPassage;
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

    public void setIsSecretPassage(boolean b) {
        isSecretPassage = b;
    }

    public boolean isSecretPassage() {
        return isSecretPassage;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
