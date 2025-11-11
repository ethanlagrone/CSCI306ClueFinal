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
		// room that the player is currently in (passed to this method)
		Card roomCard = new Card(room.getName(), CardType.ROOM);
		Card personCard = null;
		Card weaponCard = null;
		Card currentCard = null;
		while (personCard == null || weaponCard == null) {
			curre
		}

	}
	
	public BoardCell selectTarget(Set<BoardCell> targets) {
		// get all rooms in the target (if any) that are unseen to the player
		ArrayList<BoardCell> unseenRooms = new ArrayList<>();
		for (BoardCell cell : targets) {
			if (!cell.getRoom().getName().equals("Walkway") && !seen.contains(cell.getRoom().getName())) {
				unseenRooms.add(cell);
			}
		}

		// if there are any unseen rooms in the target, pick a random one out of that list
		if (unseenRooms.size() != 0) {
			return unseenRooms.get((int)(Math.random() * unseenRooms.size()));
		}
		// if there are no unseen rooms in the target, pick a random target
		else {
			ArrayList<BoardCell> targetsList = new ArrayList<>(targets);
			return targetsList.get((int)(Math.random() * targets.size()));
		}
	}
}
