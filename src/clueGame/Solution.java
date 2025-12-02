package clueGame;

import java.util.Objects;

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

	@Override
	public String toString() {
		return "Solution [room=" + room + ", person=" + person + ", weapon=" + weapon + "]";
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(person, room, weapon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solution other = (Solution) obj;
		return Objects.equals(person, other.person) && Objects.equals(room, other.room)
				&& Objects.equals(weapon, other.weapon);
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
