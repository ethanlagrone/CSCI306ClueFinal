package clueGame;

import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public boolean isHuman() {
		return false;
	}
	
	public Solution createSuggestion(Room room) {
		return null;
		//stub
	}
	
	public BoardCell selectTarget(Set<BoardCell> targets) {
		ArrayList<BoardCell> unseenRooms = new ArrayList<>();
		for (BoardCell cell : targets) {
			if (!cell.getRoom().getName().equals("Walkway") && !seen.contains(cell.getRoom().getName())) {
				unseenRooms.add(cell);
			}
		}
		for (BoardCell cell : unseenRooms) {
			System.out.println(cell.getRoom().getName());
		}

		if (unseenRooms.size() != 0) {
			return unseenRooms.get((int)(Math.random() * unseenRooms.size()));
		}
		else {
			ArrayList<BoardCell> targetsList = new ArrayList<>(targets);
			return targetsList.get((int)(Math.random() * targets.size()));
		}
	}
}
