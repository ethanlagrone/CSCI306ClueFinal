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
		
		//Go through the deck and set the cards as random choices, but you need both a person and weapon card
		while (personCard == null || weaponCard == null) {
			int index;
			if(deck.size() == 0) {
				return null;
			} else {
				//get random index
				index = random.nextInt(0, deck.size());
			}
			currentCard = deck.get(index);
			//check if personCard is empty and if the random card is a person card, set person card
			if(personCard == null && currentCard.getCardType() == CardType.PERSON && !seen.contains(currentCard)) {
				personCard = currentCard;
			}
			//check if weaponCard is empty and if the random card is a weapon card, set weapon card
			if(weaponCard == null && currentCard.getCardType() == CardType.WEAPON && !seen.contains(currentCard)) {
				weaponCard = currentCard;
			}
			currentCard = null;

		}

		try {
			return new Solution(roomCard, personCard, weaponCard);
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		
		return null;

	}
	
	public BoardCell selectTarget(Set<BoardCell> targets) {
	    ArrayList<BoardCell> allRoomCells = new ArrayList<>();
	    for (BoardCell cell : targets) {
	        String roomName = cell.getRoom().getName();
	        if (!roomName.equals("Walkway") && !roomName.equals("Unused")) {
	            allRoomCells.add(cell);
	        } 
	        if(roomName.equals("Walkway") && cell.isOccupied()) {
	        	allRoomCells.remove(cell);
	        }
	    }
	    
	    ArrayList<BoardCell> unseenRooms = new ArrayList<>();
	    for (BoardCell roomCell : allRoomCells) {
	        boolean seenFlag = false;
	        for (Card c : seen) {
	            if (c.getCardName().equals(roomCell.getRoom().getName())) {
	                seenFlag = true;
	                break;
	            }
	        }
	        if (!seenFlag) {
	            unseenRooms.add(roomCell);
	        }
	    }
	    
	    if (!unseenRooms.isEmpty()) {
	        return unseenRooms.get((int)(Math.random() * unseenRooms.size()));
	    } else {
	        ArrayList<BoardCell> targetsList = new ArrayList<>(targets);
	        return targetsList.get((int)(Math.random() * targetsList.size()));
	    }
	}
}
