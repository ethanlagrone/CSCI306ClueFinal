package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	private ArrayList<Card> hand;
	private Set<Card> seen;
	
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		seen = new HashSet<>();
		hand = new ArrayList<>();
	}

	public abstract boolean isHuman();

	public void updateHand(Card card) {
		if (!hand.contains(card)) {
			hand.add(card);
			seen.add(card);
		}
	}
	
	public void updateSeen(Card card) {
		seen.add(card);
	}

	public Card disproveSuggestion(Solution suggestion) {
		//stub
		return new Card("John", CardType.PERSON);
	}
	
	//SETTERS AND GETTERS FOR TESTING
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getColumn() {
		return column;
	}


	public void setColumn(int column) {
		this.column = column;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

}
