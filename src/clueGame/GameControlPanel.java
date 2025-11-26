package clueGame;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel implements ActionListener {

	private JTextField turnText;
    private JTextField rollText;
    private JTextField guessText;
    private JTextField guessResultText;
	private JPanel MainPanel;
	private JPanel topMainPanel;
    private JPanel top1Panel;
    private JPanel top2Panel;
    private JButton accusationButton;
    private JButton nextPlayerButton;
	private JPanel bottomMainPanel;
    private	JPanel bottomLeftPanel;
    private	JPanel bottomRightPanel;

	private static Board board;
    
    public GameControlPanel() {
    	//constructor
		board = Board.getInstance();

    	setLayout(new BorderLayout());
    	
    	turnText = new JTextField(15);
        rollText = new JTextField(5);
        guessText = new JTextField(20);
        guessResultText = new JTextField(20);
        
        turnText.setEditable(false);
        rollText.setEditable(false);
        guessText.setEditable(false);
        guessResultText.setEditable(false);
    	
    	//Big Panel two rows
    	MainPanel = new JPanel();
    	MainPanel.setLayout(new GridLayout(2,1));
    	
    	//Create the top panel in the big panel
    	topMainPanel = new JPanel();
    	top1Panel = new JPanel(new GridLayout(1,2));
    	top2Panel = new JPanel(new GridLayout(2,1));
    	accusationButton = new JButton("Make Accusation");
    	nextPlayerButton = new JButton("NEXT!");
		nextPlayerButton.addActionListener(this);
    	
    	top2Panel.add(new JLabel("Whose turn?"), BorderLayout.NORTH);
    	top2Panel.add(turnText);
    	
    	top1Panel.add(new JLabel("Roll:"), BorderLayout.WEST);
    	top1Panel.add(rollText);
    	
    	//Insert top piece
    	topMainPanel.add(top2Panel);
    	topMainPanel.add(top1Panel);
    	topMainPanel.add(nextPlayerButton);
    	topMainPanel.add(accusationButton);
    	MainPanel.add(topMainPanel, BorderLayout.NORTH);
    	
    	//Create the bottom panel in the big panel
    	bottomMainPanel = new JPanel(new GridLayout(1,2));
    	bottomLeftPanel = new JPanel(new GridLayout(2,1));
    	bottomRightPanel = new JPanel(new GridLayout(2,1));
    	
    	//Add to bottomPanel
    	bottomLeftPanel.setBorder(new TitledBorder("Guess"));
    	bottomLeftPanel.add(guessText);
    	
    	bottomRightPanel.setBorder(new TitledBorder("Guess Result"));
    	bottomRightPanel.add(guessResultText);
    	
    	bottomMainPanel.add(bottomLeftPanel);
    	bottomMainPanel.add(bottomRightPanel);

    	
    	//Insert Bottom Piece
    	MainPanel.add(bottomMainPanel, BorderLayout.SOUTH);
    	
    	add(MainPanel);
    }
    
    
    
	public void setTurn(Player player, int roll) {
		//get player name and color to set who's turn it is
		turnText.setText(player.getName() + "'s");
		turnText.setBackground(player.getColorCode());
		rollText.setText(String.valueOf(roll));
	}
	
	public void setGuess(String guess) {
		guessText.setText(guess);
	}
	
	public void setGuessResult(String result) {
		guessResultText.setText(result);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextPlayerButton) {
			int roll = (int)(Math.random() * 6);
			if (board.getCurrentPlayer() == null) {
				board.setCurrentPlayer(board.getPlayers().get((int)(Math.random() * board.getPlayers().size())));
				setTurn(board.getCurrentPlayer(), roll);
			}
			if (board.turnProgressable()) {
				board.progressTurn();
				setTurn(board.getCurrentPlayer(), roll);
				board.movePlayer(roll);
			}
		}
	}
}
