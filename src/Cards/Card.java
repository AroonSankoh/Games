package Cards;

/**
 * 
 * @author Aroon Sankoh (aroonjsankoh@gmail.com)
 *
 */

public class Card {
	
	int value;
	private static final int aceValue = 1; 
	private static final int jackValue = 11;
	private static final int queenValue = 12;
	private static final int kingValue = 13;
	private static final int emptyDeck = -1;
	
	String suit;
	
	/**
	 * Constructs a Card that represents a physcical card.
	 * @param value the value of the card (Ace through King).
	 * @param suit the suit of the card (spades, diamonds, clubs, hearts).
	 */
	public Card(int value, String suit) {
		this.value = value;
		this.suit = suit;
	}
	
	public int getValue() { 
		return value;
	}
	
	public String getSuit() { 
		return suit;
	}
	
	/**
	 * Prints out the value and suite of a card. 
	 */
	
	public void peak() {
		if (value > aceValue && value < jackValue) {
			System.out.println(value + " of " + suit + "s" );
		}
		else {
			switch (value) {
				case emptyDeck: 
					System.out.println("This deck is empty!");
					break;
				case aceValue:	
					System.out.println("Ace of " + suit + "s" );
					break;
				case jackValue:
					System.out.println("Jack of " + suit + "s" );
					break;
				case queenValue: 
					System.out.println("Queen of " + suit + "s" );
					break;
				case kingValue: 
					System.out.println("King of " + suit + "s" );
					break;	
			}
		}
	}
}
