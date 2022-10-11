package Cards;

import Game.Player;
import java.util.ArrayList;

/**
 * 
 * @author Aroon Sankoh (aroonjsankoh@gmail.com)
 *
 */

public class Deck {
	
	private static final int kingValue = 13;
	private static final int aceValue = 1;  
	
	ArrayList<Card> mainDeck = new ArrayList<Card>(); 
	ArrayList<Card> discardPile = new ArrayList<Card>();
	Card error = new Card(-1, ""); //An "error" card which is only used to tell the user the deck is empty. 
	
	/** 
	 * Constructs a Deck that implements an ArrayList "mainDeck" made up of 52 cards. 
	 * mainDeck represents a fresh standard deck (except jokers).    
	 */
	
	public Deck() {	
		for ( int i = aceValue; i < kingValue + 1; ++i) {
			 Card card = new Card(i, "Spade"); 
			 mainDeck.add(card);
		 }
		 for (int i = aceValue; i < kingValue + 1; ++i) {
			 Card card = new Card(i, "Diamond");
			 mainDeck.add(card);		
		 }
		 for (int i = kingValue; i > aceValue - 1; --i) {
			 Card card = new Card(i, "Club");
			 mainDeck.add(card);		
		 }
		 for (int i = kingValue; i > aceValue - 1; --i) { 
			 Card card = new Card(i, "Heart");
			 mainDeck.add(card);		
		 }
	}
	
	public ArrayList<Card> getDeck() {
		return mainDeck;
	}
	
	public ArrayList<Card> getPile() {
		return discardPile;
	}
	
	public int getDeckSize() {
		return mainDeck.size();
	}
	
	public int getPileSize() {
		return discardPile.size();
	}
	
	public void viewDeck() {
		for (Card card: getDeck() ) {
			card.peak();
		}
	}
	
	public void viewPile() {
		for (Card card: getPile() ) {
			card.peak();
		}
	}
	
	
	/** 
	 * Takes one card from the main deck and then puts into the discard pile.
	 * @return the card drawn.
	 */
	public Card drawOne() {
		if (getDeckSize() > 0) {
			Card card = mainDeck.get(0);
			mainDeck.remove(0);
			discardPile.add(card);
			card.peak();
			return card;
		} 
		else {
			error.peak();
			return error;
		}
	}
	
	/**
	 * Draws multiple cards.
	 * @parem cards the number of cards to draw.
	 * @return an array of the drawn cards if the mainDeck is not empty, otherwise the error card.
	 */
	
	public Card[] draw (int cards) {
		if (!mainDeck.isEmpty()) {
			if (cards > getDeckSize()) {
				cards = getDeckSize();
			}
			Card[] drawncards = new Card[cards];
			for ( int i = 0; i < cards; ++i ) {
				Card card = mainDeck.get(0);
				drawncards[i] = card;
				mainDeck.remove(0);
				discardPile.add(card);	
		}
		for ( int i = 0; i < drawncards.length; ++i ) {
			drawncards[i].peak();
		}
		return drawncards;
		}
		else {
			Card[] errorA = {error};
			errorA[0].peak();
			return errorA;
		}
	}
	
	/**
	 * Takes card(s) from the main deck and then puts them into a players hand.
	 * @param player the player to deal to.
	 * @param cards the number of cards to deal.
	 * @return an array of the drawn cards if the mainDeck is not empty, otherwise the error card.
	 */
	
	public Card[] deal(Player player, int cards) {
		if (!mainDeck.isEmpty()) {
			if (cards > getDeckSize()) {
				cards = getDeckSize();
			}
			Card[] drawncards = new Card[cards];
			for ( int i = 0; i < cards; ++i ) {
				Card card = mainDeck.get(0);
				drawncards[i] = card;
				mainDeck.remove(0);
				player.addToHand(card);	
		}
		for ( int i = 0; i < drawncards.length; ++i ) {
			drawncards[i].peak();
		}
		return drawncards;
		}
		else {
			Card[] errorA = {error};
			errorA[0].peak();
			return errorA;
		}
	}
	
	public void shuffle() {
		if (!mainDeck.isEmpty()){
			int deckSize = getDeckSize(); 
			for (int i = 0; i < deckSize; ++i) {
				discardPile.add(mainDeck.get(0));
				mainDeck.remove(0);
			}
		}
		int pileSize = getPileSize();
		while (!discardPile.isEmpty() ) {	
			Card randomCard = discardPile.get(randomNumber(0, getPileSize()) ); 
			discardPile.remove(randomCard);
			mainDeck.add(randomCard);
			pileSize--;
		}
	}
	
	public int randomNumber(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
	
	public static void main(String[] args) {
	}
}
