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

    private JTextField turnField;
    private JTextField rollField;
    private JTextField guessField;
    private JTextField guessResultField;
    
    public GameControlPanel() {
    	//constructor
    	setLayout(new BorderLayout());
    	
    	//Big Panel two rows
    	JPanel MainPanel = new JPanel();
    	MainPanel.setLayout(new GridLayout(2,1));
    	
    	//Create the top panel in the big panel
    	JPanel topMainPanel = new JPanel();
    	JPanel top1Panel = new JPanel(new GridLayout(2,1));
    	JPanel top2Panel = new JPanel();
    	JButton nextPlayerButton = new JButton("NEXT!");
    	JButton accusationButton = new JButton("Make Accusation");
    	
    	top1Panel.add(new JLabel("WAHH"), BorderLayout.NORTH);
    	top1Panel.add(new JTextField(10));
    	
    	top2Panel.add(new JLabel("YOWZA"));
    	top2Panel.add(new JTextField(10));
    	
    	//Insert top piece
    	topMainPanel.add(top1Panel);
    	topMainPanel.add(top2Panel);
    	topMainPanel.add(nextPlayerButton);
    	topMainPanel.add(accusationButton);
    	MainPanel.add(topMainPanel, BorderLayout.NORTH);
    	
    	//Create the bottom panel in the big panel
    	JPanel bottomMainPanel = new JPanel(new GridLayout(1,2));
    	JPanel bottomLeftPanel = new JPanel(new GridLayout(2,1));
    	JPanel bottomRightPanel = new JPanel(new GridLayout(2,1));
    	
    	//Add to bottomPanel
    	bottomLeftPanel.add(new JLabel("YOINKS"), BorderLayout.NORTH);
    	bottomLeftPanel.add(new JTextField(10));
    	
    	bottomRightPanel.add(new JLabel("Yowza"), BorderLayout.NORTH);
    	bottomRightPanel.add(new JTextField(10));
    	
    	bottomMainPanel.add(bottomLeftPanel);
    	bottomMainPanel.add(bottomRightPanel);

    	
    	//Insert Bottom Piece
    	MainPanel.add(bottomMainPanel, BorderLayout.SOUTH);
    	
    	add(MainPanel);
    }
    
    
    
	public void setTurn(Player player, int turn) {
		//stub
	}
	
	public void setGuess(String guess) {
		//stub
	}
	
	public void setGuessResult(String result) {
		//stub
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
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}