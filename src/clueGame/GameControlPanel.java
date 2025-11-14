package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {

    private JTextField turnText;
    private JTextField rollText;
    private JTextField guessText;
    private JTextField guessResultText;
    
    public GameControlPanel() {
    	//constructor
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
    	JPanel MainPanel = new JPanel();
    	MainPanel.setLayout(new GridLayout(2,1));
    	
    	//Create the top panel in the big panel
    	JPanel topMainPanel = new JPanel();
    	JPanel top1Panel = new JPanel(new GridLayout(1,2));
    	JPanel top2Panel = new JPanel(new GridLayout(2,1));
    	JButton accusationButton = new JButton("Make Accusation");
    	JButton nextPlayerButton = new JButton("NEXT!");
    	
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
    	JPanel bottomMainPanel = new JPanel(new GridLayout(1,2));
    	JPanel bottomLeftPanel = new JPanel(new GridLayout(2,1));
    	JPanel bottomRightPanel = new JPanel(new GridLayout(2,1));
    	
    	//Add to bottomPanel
    	bottomLeftPanel.add(new JLabel("Guess"), BorderLayout.NORTH);
    	bottomLeftPanel.add(guessText);
    	
    	bottomRightPanel.add(new JLabel("Guess Result"), BorderLayout.NORTH);
    	bottomRightPanel.add(guessResultText);
    	
    	bottomMainPanel.add(bottomLeftPanel);
    	bottomMainPanel.add(bottomRightPanel);

    	
    	//Insert Bottom Piece
    	MainPanel.add(bottomMainPanel, BorderLayout.SOUTH);
    	
    	add(MainPanel);
    }
    
    
    
	public void setTurn(Player player, int turn) {
		turnText.setText(player.getName() + "'s");
		turnText.setBackground(player.getColorCode());
		rollText.setText(String.valueOf(turn));
	}
	
	public void setGuess(String guess) {
		guessText.setText(guess);
	}
	
	public void setGuessResult(String result) {
		guessResultText.setText(result);
	}
	
	public static void main(String[] args) {
		//THIS IS GARBAGE CODE FYI
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Joe Mama", "black", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}