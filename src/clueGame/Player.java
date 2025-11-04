package clueGame;

public abstract class Player {
	private String name;
	private String color;
	private int row, column;
	
	
	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
	}


	public void updateHand(Card card) {
		//stub
	}



}
