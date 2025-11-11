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
		board.deal();
	}
	
	
	@Test
	public void checkAccusationTest() throws BadConfigFormatException {

		//correct accusation
		Solution accusation = new Solution(board.getSolution().getRoom(), board.getSolution().getPerson(), board.getSolution().getWeapon());
		assertTrue(board.checkAccusation(accusation));
		
		
		//incorrect accusation wrong weapon
		Solution incorrectAccusationWeapon = new Solution(board.getSolution().getRoom(), board.getSolution().getPerson(), new Card(null, CardType.WEAPON));
		assertTrue(!board.checkAccusation(incorrectAccusationWeapon));
		
		//incorrect accusation wrong person
		Solution incorrectAccusationPerson = new Solution(board.getSolution().getRoom(), new Card(null, CardType.PERSON), board.getSolution().getWeapon());
		assertTrue(!board.checkAccusation(incorrectAccusationPerson));
		
		//incorrect accusation wrong place
		Solution incorrectAccusationPlace = new Solution(new Card(null, CardType.ROOM), board.getSolution().getPerson(), board.getSolution().getWeapon());
		assertTrue(!board.checkAccusation(incorrectAccusationPlace));
	}
	
	@Test
	public void dissproveSuggestionTest() throws BadConfigFormatException {
		//Create garbage cards and a garbage player with three cards and a garbage solution
		Card dissprovedCard;
		ComputerPlayer cp = new ComputerPlayer("John", "Green", 1, 1);
		Card garbageCardPerson = new Card("John", CardType.PERSON);
		Card garbageCardRoom = new Card("Kansas", CardType.ROOM);
		Card garbageCardWeapon = new Card("Gun", CardType.WEAPON);
		Solution suggestion = new Solution(garbageCardRoom, garbageCardPerson, garbageCardWeapon);
		
		//No cards in hand
		dissprovedCard = cp.disproveSuggestion(suggestion);
		assertTrue(dissprovedCard == null);
		
		//Only one matching card in players hand
		cp.updateHand(garbageCardPerson);
		dissprovedCard = cp.disproveSuggestion(suggestion);
		assertTrue(dissprovedCard.equals(garbageCardPerson));
		
		//More than one card in hand
		cp.updateHand(garbageCardRoom);
		cp.updateHand(garbageCardWeapon);
		int roomCount = 0;
		int weaponCount = 0;
		int personCount = 0;
		
		for(int i = 0; i < 500; i++) {
			dissprovedCard = cp.disproveSuggestion(suggestion);
			if(dissprovedCard.getCardType() == CardType.PERSON) {
				personCount++;
			} else if(dissprovedCard.getCardType() == CardType.ROOM) {
				roomCount++;
			} else if(dissprovedCard.getCardType() == CardType.WEAPON) {
				weaponCount++;
			}
		}
		
		//Should be doled out randomly from the players hand. There should be exactly 500 outputs and each will likely get more than 1
		assertTrue(weaponCount >= 1);
		assertTrue(personCount >= 1);
		assertTrue(roomCount >= 1);
		assertTrue(weaponCount + personCount + roomCount == 500);
		
	}
	
	@Test
	public void handleSuggestionTest() {
		/*Suggestion no one can disprove returns null
		Suggestion only suggesting player can disprove returns null
		Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		Suggestion that two players can disprove, correct player 
		(based on starting with next player in list) returns answer*/
		
		
		
		
	}
	
}
