package clueGame;

public class Room {
	String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	
	public Room() {
		super();
	}
	
	public Room(String name) {
		this.name = name;
	}
	
	//getters and setters
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public BoardCell getLabelCell() {
		return labelCell;
	}

	
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	
	

	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

}
