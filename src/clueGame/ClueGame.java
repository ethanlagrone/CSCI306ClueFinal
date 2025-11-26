package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
    
    public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
	    board.initialize();
	    board.prepareCards();
		board.setCurrentPlayer(board.getPlayers().get((int)(Math.random() * board.getPlayers().size())));

	    ClueCardsGUI clueCards = new ClueCardsGUI(board.getPlayers());
	    GameControlPanel gameControlPanel = new GameControlPanel();
		gameControlPanel.setTurn(board.getCurrentPlayer(), (int)(Math.random() * 6));
	    JFrame frame = new JFrame("Clue Game Board");

	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(900, 900);
	    frame.add(board, BorderLayout.CENTER);
	    frame.add(clueCards, BorderLayout.EAST);	    
	    frame.add(gameControlPanel, BorderLayout.SOUTH);
	    
	    frame.setVisible(true);

		JOptionPane.showMessageDialog(frame, 
			"You are " + board.getHumanPlayer().getName() + ".\nTry to find the solution before the other players!",
			"Welcome!",
			JOptionPane.INFORMATION_MESSAGE);
	}
}
