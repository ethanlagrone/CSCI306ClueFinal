package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private boolean hasDoor;
	private DoorDirection doorDirection;
	
	public Room() {
		super();
	}
	
	public Room(String name) {
		this.name = name;
	}
	
	//getters and setters
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}
	
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

	
	public boolean hasDoor() {
        return hasDoor;
    }

    public void setHasDoor(boolean hasDoor) {
        this.hasDoor = hasDoor;
    }

	
	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
}
