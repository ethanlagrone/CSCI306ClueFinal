package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class ClueCardsGUI extends JPanel{

	public ClueCardsGUI(Player player) {
		setLayout(new GridLayout(3,1));
		setBorder(new TitledBorder("Known Cards"));
		
		//Create three JPanels for People rooms and weapons
		JPanel people = new JPanel(new GridLayout(2,1));
		people.setBorder(new TitledBorder("People"));
		JPanel rooms = new JPanel(new GridLayout(2,1));
		rooms.setBorder(new TitledBorder("Rooms"));
		JPanel weapons = new JPanel(new GridLayout(2,1));
		weapons.setBorder(new TitledBorder("Weapons"));
		
		
		//Create two JPanels for each of those that has inHand and Seen
		JTextField hand = new JTextField("In Hand");
		JTextField seen = new JTextField("Seen");
		
		JPanel peopleHand = new JPanel();
		JPanel peopleSeen = new JPanel();
		JPanel roomsHand = new JPanel();
		JPanel roomsSeen = new JPanel();
		JPanel weaponHand = new JPanel();
		JPanel weaponSeen = new JPanel();
		
		peopleHand.add(hand, BorderLayout.NORTH);
		peopleSeen.add(seen);
		roomsHand.add(hand, BorderLayout.NORTH);
		roomsSeen.add(seen);
		weaponHand.add(hand, BorderLayout.NORTH);
		weaponSeen.add(seen);
		
		
		//go through 
		for(Card c : player.getSeen()) {
			//new JPanel that uses attributes of the card to create it
			//We could do isInHand boolean 
			JPanel handCardPanel = new JPanel();
			handCardPanel = cardPanel(c);
			if(c.getCardType() == CardType.PERSON) {
				if(player.isInHand(c)) {
					peopleHand.add(handCardPanel);
				} else {
					peopleSeen.add(handCardPanel);
				}
			} else if(c.getCardType() == CardType.ROOM) {
				if(player.isInHand(c)) {
					roomsHand.add(handCardPanel);
				} else {
					roomsSeen.add(handCardPanel);
				}
			} else if(c.getCardType() == CardType.WEAPON) {
				if(player.isInHand(c)) {
					weaponHand.add(handCardPanel);
				} else {
					weaponSeen.add(handCardPanel);
				}
			}
			
		}
		
		add(people, BorderLayout.NORTH);
		add(rooms);
		add(weapons);
	}
	
	public JPanel cardPanel(Card card) {
		//JUST THIS
		//Create JPanel From Card
		return null;
	}
	
	public static void main(String[] args) {
		Board board;
		
		ArrayList<Player> players = new ArrayList<Player>();
		// test filling in the data
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		board.prepareCards();
		players = board.getPlayers();


		ClueCardsGUI panel = new ClueCardsGUI(players.get(0));  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300, 800);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		
	}
}
