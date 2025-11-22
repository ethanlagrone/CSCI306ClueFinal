package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.ClueCardsGUI;

public class ClueGame extends JFrame {
    
    public static void main(String[] args) {
        Board board = Board.getInstance();
        JFrame frame = new JFrame();
        ClueCardsGUI cardsPanel = new ClueCardsGUI(board.getPlayers());
        GameControlPanel controlPanel = new GameControlPanel();
        frame.add(cardsPanel);
        frame.add(controlPanel);
    }
}
