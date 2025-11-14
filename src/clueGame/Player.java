package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	private ArrayList<Card> hand;
	protected Set<Card> seen;
	
	
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
	
	public void removeSeen(Card card) {
		seen.remove(card);
	}

	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> possibleCards = new ArrayList<Card>();
		for(Card c : hand) {
			if(c.equals(suggestion.getPerson())) {
				possibleCards.add(c);
			} else if(c.equals(suggestion.getRoom())) {
				possibleCards.add(c);
			} else if(c.equals(suggestion.getWeapon())) {
				possibleCards.add(c);
			}
		}
		
		Random random = new Random();
		
		if(possibleCards.size() == 0) {
			return null;
		} else if (possibleCards.size() == 1){
			return possibleCards.get(0);
		} else {
			int chosenCard = random.nextInt(0, possibleCards.size());
			return possibleCards.get(chosenCard);
		}
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

	public Set<Card> getSeen() {
		return seen;
	}
	
	public boolean isInHand(Card c) {
		if(hand.contains(c)) {
			return true;
		}
		return false;
	}
	
	public Color getColorCode() {
		/*Player, D'Angelo, Brown, 2, 17, 
		Player, Bob Dylan, White, 10, 20, 
		Player, Rivers Cuomo, Blue, 17, 7, 
		Player, Marvin Gaye, Red, 9, 12, 
		Player, Kendrick Lamar, Green, 7, 5, 
		Player, Robert Plant, Black, 10, 19, */
		if(color == null) {
			return Color.WHITE;
		}
		String newColor = color.toLowerCase();
		switch(newColor) {
			case "white":
				return Color.WHITE;
			case "brown":
				//had to look up rgb value
				return new Color(181, 101, 29);
			case "blue":
				return Color.BLUE;
			case "red":
				return Color.RED;
			case "green":
				return Color.GREEN;
			case "black":
				return Color.GRAY;
			default:
				return Color.WHITE;
		}
	}

}
