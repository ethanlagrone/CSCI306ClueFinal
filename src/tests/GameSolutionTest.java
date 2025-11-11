package tests;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameSolutionTest {
	
	private static Board board;
	
	@BeforeEach
	public void setUp() {
		// clueGame is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	
	@Test
	public void checkAccusationTest() {
		board.deal();

		//correct accusation
		Solution accusation = new Solution(board.getSolution().getRoom(), board.getSolution().getPerson(), board.getSolution().getWeapon());
		assertTrue(board.checkAccusation(accusation));
		
		
		//incorrect accusation wrong weapon
		Solution incorrectAccusationWeapon = new Solution(board.getSolution().getRoom(), board.getSolution().getPerson(), null);
		assertTrue(!board.checkAccusation(incorrectAccusationWeapon));
		
		//incorrect accusation wrong person
		Solution incorrectAccusationPerson = new Solution(board.getSolution().getRoom(), null, board.getSolution().getWeapon());
		assertTrue(!board.checkAccusation(incorrectAccusationPerson));
		
		//incorrect accusation wrong place
		Solution incorrectAccusationPlace = new Solution(null, board.getSolution().getPerson(), board.getSolution().getWeapon());
		assertTrue(!board.checkAccusation(incorrectAccusationPlace));
	}
	
	@Test
	public void dissproveSuggestionTest() {
		//stub
	}
	
	@Test
	public void handleSuggestionTest() {
		//stub
	}
	
}
