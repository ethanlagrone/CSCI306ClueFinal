package clueGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public boolean isHuman() {
		return false;
	}
	
	public Solution createSuggestion(Room room, ArrayList<Card> deck) {
		// room that the player is currently in (passed to this method)
		Card roomCard = new Card(room.getName(), CardType.ROOM);
		Card personCard = null;
		Card weaponCard = null;
		Card currentCard = null;
		Random random = new Random();
		while (personCard == null) {
			int index;
			if(deck.size() == 0) {
				return null;
			} else {
				index = random.nextInt(0, deck.size());
			}
			currentCard = deck.get(index);
			if(currentCard.getCardType() == CardType.PERSON && !seen.contains(currentCard)) {
				personCard = currentCard;
			}
		}
		currentCard = null;
		while (weaponCard == null) {
			int index;
			if(deck.size() == 0) {
				return null;
			} else {
				index = random.nextInt(0, deck.size());
			}
			currentCard = deck.get(index);
			if(currentCard.getCardType() == CardType.WEAPON && !seen.contains(currentCard)) {
				weaponCard = currentCard;
			}
		} 
		
		try {
			return new Solution(roomCard, personCard, weaponCard);
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		return null;

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
