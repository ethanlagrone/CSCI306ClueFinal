package clueGame;

import javax.swing.JFrame;

import clueGame.Board;
import clueGame.ClueCardsGUI;
import clueGame.GameControlPanel;

public class ClueGame extends JFrame {
    
    public static void main(String[] args) {
        Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	    board.initialize();
	    board.prepareCards();


	    ClueCardsGUI clueCards = new ClueCardsGUI(board.getPlayers());
	    GameControlPanel gameControlPanel = new GameControlPanel();
	    JFrame frame = new JFrame("Clue Game Board");

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(900, 900);
	    frame.add(board, BorderLayout.CENTER);
	    frame.add(clueCards, BorderLayout.EAST);	    
	    frame.add(gameControlPanel, BorderLayout.SOUTH);
	    
	    frame.setVisible(true);
    }
}
