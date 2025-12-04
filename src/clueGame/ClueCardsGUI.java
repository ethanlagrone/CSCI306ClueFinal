package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class ClueCardsGUI extends JPanel{
	Player humanPlayer;

	public ClueCardsGUI(ArrayList<Player> players) {
		this.removeAll();
		reMakeGUI(players);
	}
	
	
	public void reMakeGUI(ArrayList<Player> players) {
		this.removeAll();
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
		
		//adding text to panels
		peopleHand.add(peopleHandLabel, BorderLayout.NORTH);
		peopleSeen.add(peopleSeenLabel);
		roomsHand.add(roomsHandLabel, BorderLayout.NORTH);
		roomsSeen.add(roomsSeenLabel);
		weaponHand.add(weaponHandLabel, BorderLayout.NORTH);
		weaponSeen.add(weaponSeenLabel);
		
		//adding panels
		people.add(peopleHand);
		people.add(peopleSeen);
		rooms.add(roomsHand);
		rooms.add(roomsSeen);
		weapons.add(weaponHand);
		weapons.add(weaponSeen);
		
		//go through human player's seen cards and see if it is in its hand or another players
		for(Card c : humanPlayer.getSeen()) {
			//new JPanel that uses attributes of the card to create it
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
		
		//revalidate and repaint to delete and remake
		revalidate();
		repaint();
	}
	
	
	public JTextField cardText(Card card, ArrayList<Player> players) {
		//get card name and color of player that has it and return textField
		JTextField cardText = new JTextField(card.getCardName());

		for(Player p : players) {
			if(p.getHand().contains(card)) {
				cardText.setBackground(p.getColorCode());
			}
		}
		
		return cardText;
		
	}
}
