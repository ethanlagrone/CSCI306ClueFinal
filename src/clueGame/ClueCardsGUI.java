package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;


public class ClueCardsGUI extends JPanel{
	Player humanPlayer;

	public ClueCardsGUI(ArrayList<Player> players) {
		setLayout(new GridLayout(3,1));
		setBorder(new TitledBorder("Known Cards"));
		for(Player p: players) {
			if(p.isHuman()) {
				humanPlayer = p;
			}
		}
		
		//Create three JPanels for People rooms and weapons
		JPanel people = new JPanel(new GridLayout(2,1));
		people.setBorder(new TitledBorder("People"));
		JPanel rooms = new JPanel(new GridLayout(2,1));
		rooms.setBorder(new TitledBorder("Rooms"));
		JPanel weapons = new JPanel(new GridLayout(2,1));
		weapons.setBorder(new TitledBorder("Weapons"));
		
		
		//Create two JPanels for each of those that has inHand and Seen
		JTextField peopleHandLabel = new JTextField("In Hand");
		JTextField peopleSeenLabel = new JTextField("Seen");
		JTextField roomsHandLabel = new JTextField("In Hand");
		JTextField roomsSeenLabel = new JTextField("Seen");
		JTextField weaponHandLabel = new JTextField("In Hand");
		JTextField weaponSeenLabel = new JTextField("Seen");
		
		JPanel peopleHand = new JPanel(new GridLayout(3,1));
		JPanel peopleSeen = new JPanel(new GridLayout(6,1));
		JPanel roomsHand = new JPanel(new GridLayout(3,1));
		JPanel roomsSeen = new JPanel(new GridLayout(9,1));
		JPanel weaponHand = new JPanel(new GridLayout(3,1));
		JPanel weaponSeen = new JPanel(new GridLayout(9,1));
		
		peopleHand.add(peopleHandLabel, BorderLayout.NORTH);
		peopleSeen.add(peopleSeenLabel);
		roomsHand.add(roomsHandLabel, BorderLayout.NORTH);
		roomsSeen.add(roomsSeenLabel);
		weaponHand.add(weaponHandLabel, BorderLayout.NORTH);
		weaponSeen.add(weaponSeenLabel);
		
		people.add(peopleHand);
		people.add(peopleSeen);
		rooms.add(roomsHand);
		rooms.add(roomsSeen);
		weapons.add(weaponHand);
		weapons.add(weaponSeen);
		
		//go through 
		for(Card c : humanPlayer.getSeen()) {
			//new JPanel that uses attributes of the card to create it
			//We could do isInHand boolean 
			JTextField handCardText = new JTextField();
			handCardText = cardText(c, players);
			if(c.getCardType() == CardType.PERSON) {
				if(humanPlayer.isInHand(c)) {
					peopleHand.add(handCardText);
				} else {
					peopleSeen.add(handCardText);
				}
			} else if(c.getCardType() == CardType.ROOM) {
				if(humanPlayer.isInHand(c)) {
					roomsHand.add(handCardText);
				} else {
					roomsSeen.add(handCardText);
				}
			} else if(c.getCardType() == CardType.WEAPON) {
				if(humanPlayer.isInHand(c)) {
					weaponHand.add(handCardText);
				} else {
					weaponSeen.add(handCardText);
				}
			}
			
		}
		
		add(people, BorderLayout.NORTH);
		add(rooms);
		add(weapons);
	}
	
	public JTextField cardText(Card card, ArrayList<Player> players) {
		JTextField cardText = new JTextField(card.getCardName());

		for(Player p : players) {
			if(p.getHand().contains(card)) {
				cardText.setBackground(p.getColorCode());
			}
		}
		
		return cardText;
		
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
		for(Card c : players.get(2).getHand()) {
			players.get(0).updateSeen(c);
		}


		ClueCardsGUI panel = new ClueCardsGUI(players);  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(300, 800);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		
	}
}
