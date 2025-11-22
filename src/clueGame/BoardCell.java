package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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

    // DRAWING
    public void draw(int xOffset, int yOffset, int height, int width, Graphics g) {
        // draw each individual cell
        if (room.getName().equals("Walkway")) {
            g.setColor(Color.YELLOW);
            g.fillRect(xOffset, yOffset, height, width);
            g.setColor(Color.BLACK);
            g.drawRect(xOffset, yOffset, width, height);
        }
        else if (room.getName().equals("Unused")) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(xOffset, yOffset, height, width);
        }
        else {
            g.setColor(Color.GREEN);
            g.fillRect(xOffset, yOffset, height, width);
        }

        // handle doors
        g.setColor(Color.BLUE);
        switch (doorDirection) {
            case DoorDirection.NONE:
                break;
            case DoorDirection.LEFT:
                g.fillRect(xOffset, yOffset, width / 6, height);
                break;
            case DoorDirection.UP:
                g.fillRect(xOffset, yOffset, width, height / 6);
                break;
            case DoorDirection.RIGHT:
                g.fillRect(xOffset + (5 / 6) * width, yOffset, width, height);
                break;
            case DoorDirection.DOWN:
                g.fillRect(xOffset, yOffset + (5 / 6) * height, width, height);
                break;
        }

        // handle room labels
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 12));
        if (isLabel) {
            g.drawString(room.getName(), xOffset, yOffset);
        }
    }
}
