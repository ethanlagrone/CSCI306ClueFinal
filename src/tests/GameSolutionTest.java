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
		//stub
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
