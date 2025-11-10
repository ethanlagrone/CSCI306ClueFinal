package clueGame;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
	}
	
	@Override
	public boolean isHuman() {
		return false;
	}
	
	public Solution createSuggestion(Solution suggestion) {
		return null;
		//stub
	}
	
	public BoardCell selectTarget(BoardCell cell) {
		return null;
		//stub
	}
}
