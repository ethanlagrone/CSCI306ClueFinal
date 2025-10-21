package clueGame;

public class Room {
	String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	
	public Room() {
		super();
	}
	
	
	//getters and setters
	public BoardCell getCenterCell() {
		//stub
		return new BoardCell(0,0);
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
