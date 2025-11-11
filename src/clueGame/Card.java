package clueGame;

public class Card {
	String cardName;
	CardType cardType;
	
	public Card(String cardName, CardType cardType) {
		super();
		this.cardName = cardName;
		this.cardType = cardType;
	}
	
	public boolean equals(CardType cardType, String cardName) {
		return cardType == this.cardType && cardName == this.cardName;
	}

	
	
	//GETTERS AND SETTERS FOR TESTING
	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
}
