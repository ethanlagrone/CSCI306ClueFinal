package clueGame;

import java.util.Set;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	/* 
		this is probably awful practice but i didn't want to deal with typecasting in
		some of my logic so this is just here now
	*/
	public BoardCell selectTarget(Set<BoardCell> targets) {
		System.out.println("If you're seeing this, Morgan messed something up BAD at 2am while already home for break.");
		return null;
	}
	
	@Override
	public boolean isHuman() {
		return true;
	}
}