package BoardPackage;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
    
    private int row;
    private int column;
    private boolean inRoom = false;
    private boolean occupied = false;
    private Set<BoardCell> adjCells;

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
