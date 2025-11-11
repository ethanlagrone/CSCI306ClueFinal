package clueGame;

public class Solution {
	private Card room;
	private Card person;
	private Card weapon;
	
	public Solution(Card room, Card person, Card weapon) throws BadConfigFormatException {
		super();
		if(room.getCardType() != CardType.ROOM || person.getCardType() != CardType.PERSON || weapon.getCardType() != CardType.WEAPON) {
			throw new BadConfigFormatException("CardType not right");
		}
		this.room = room;
		this.person = person;
		this.weapon = weapon;
	}

	public Card getRoom() {
		return room;
	}

	public Card getPerson() {
		return person;
	}

	public Card getWeapon() {
		return weapon;
	}
}
