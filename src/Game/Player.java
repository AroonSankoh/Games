package Game;

import java.util.ArrayList;
import Cards.Deck;
import Cards.Card;

/**
 * 
 * @author Aroon Sankoh (aroonjsankoh@gmail.com)
 *
 */

public class Player {
	
	int number;
	ArrayList<Card> Hand = new ArrayList<Card>();
	
	
	/**
	 * Constructs a new Player 
	 * @param numberInput the number associated to the player (for easy reference). 
	 */
	public Player(int numberInput) {
		this.number = numberInput;
	}
	
	public int getNumber() {
		return number;
	}
	
	/**
	 * Peaks at each card in a players hand.
	 */
	public void bleed() {
		for (Card card: Hand) {
			card.peak();
		}
	}
	
	/**
	 * @return the players hand. 
	 */
	public ArrayList<Card> reveal() {
		return Hand;
	}
	
	public void addToHand(Card card) {
		Hand.add(card);
	}
	
	/**
	 * Removes card from players hand and adds it to the discard pile of its associated deck.
	 * @param card The card to remove from a player's hand. 
	 * @param deck The deck of the discard pile the card will be put in. 
	 */
	public void discardCard(Card card, Deck deck) {
		Hand.remove(card);
		discard(deck, card); 
	}
	
	/**
	 * Removes all the cards from a players hand and adds it to the discard pile of their associated deck.
	 * @param deck The deck of the discard pile the cards will be put in. 
	 */
	public void discardHand(Deck deck) {
		while (!Hand.isEmpty() ){
			Card card = Hand.get(0); 
			Hand.remove(card);
			discard(deck, card);
		}
	}
	
	public void discard(Deck deck, Card card) {
		deck.getPile().add(card);
	}

}
