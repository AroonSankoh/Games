package BlackJack;

import java.util.ArrayList;
import support.cse131.ArgsProcessor;
import Cards.Card;
import Cards.Deck;
import Game.Player;

/**
 * 
 * @author Aroon Sankoh (aroonjsankoh@gmail.com)
 *
 */
public class BlackJackGame {
	
	private boolean dealerBust = false;
	private static final int dealerNumber = 0;
	private static final int maxPlayers = 7;
	private static final int minPlayers = 1;
	private static final int maxWager = 500;
	private static final int minWager = 2;
	private static final int faceCardValue = 10;
	private static final int aceValue1 = 1;
	private static final int aceValue11 = 11;
	private static final int blackJack = 21;
	private static final int seventeenCount = 17;
	private static final double winFactor = 1.5;
	
	Deck gameDeck = new Deck();
	Player dealer = new Player(dealerNumber);
	Player[] players;
	ArrayList<Player> standList = new ArrayList<Player>(); //All the players who stood and waited for the dealer to reveal
	ArrayList<Player> bustList = new ArrayList<Player>(); //All the players who went bust during their play
	ArrayList<Player> naturalList = new ArrayList<Player>(); //All the players who got naturals
	
	/**
	 * Constructs a new BlackJack game of "numPlayers" players.
	 * @param numPlayers the number of players in the game.
	 */
	public BlackJackGame(int numPlayers) {
		players = new Player[numPlayers];
		for (int i = 1; i < numPlayers + 1; ++i) {
			Player newPlayer = new Player(i);
			players[i-1] = newPlayer; 
		}
	}
	
	/**
	 * The beginning deal, all players are dealt two cards.
	 */
	public void cut() {  
		gameDeck.shuffle();
		for (Player player: players) {
			System.out.println("Player " + player.getNumber() + ": ");
			gameDeck.deal(player, 1, true);
			gameDeck.deal(player, 1, true);
			System.out.println();
		}
		System.out.println("Dealer: ");
		gameDeck.deal(dealer, 1, true);
		System.out.println("The dealers second card will remain hidden until the dealer's play.");
		gameDeck.deal(dealer, 1, false);
		System.out.println();
	}
	
	/**
	 * Calculates the card count of a player after the beginning deal.
	 * @param player The player whose card count is to be calculated. 
	 * @return the count. 
	 */
	public int initialCount(Player player) {  
		int count = 0;
		ArrayList<Card> Hand = player.reveal();
		for (int i = 0; i < Hand.size(); ++i) { 
			if (Hand.get(i).getValue() > faceCardValue ) { //Check for face cards
				Hand.get(i).value = faceCardValue;
			}
			if (Hand.get(i).getValue() == aceValue1) { //Check for an ace
				Hand.get(i).value = aceValue11;
			}
			count += Hand.get(i).getValue();
		}
		if (count > blackJack) { //****
			Hand.get(0).value = aceValue1;//In the unlikely case a player is dealt two aces, set one ace to a value of one
			count = Hand.get(0).getValue() + Hand.get(1).getValue();
		}
		if (player.getNumber() != dealerNumber && count == blackJack) {
			naturalList.add(player);
		}
		return count;		
	}
	
	/**
	 * Calculates the card count of a player after they hit.  
	 * @param player The player whose card count is to be calculated.
	 * @return the count.
	 */
	public int recalculateCount(Player player) { //Called everytime a player hits
		int count = 0;
		ArrayList<Card> Hand = player.reveal();
		Card newCard = Hand.get(Hand.size()- 1); //Gets the latest card placed into the players hand
		if (newCard.getValue() > faceCardValue) {
			newCard.value = faceCardValue;
		}
		if(count > blackJack) {
			for (Card card: Hand) {
				if (card.getValue() == aceValue11) {
					card.value = aceValue1;
				}
			}
		}
		for(int i = 0; i < Hand.size(); ++i) {
			count += Hand.get(i).getValue();
		}
		return count;
	}
	
	public static void main(String[] args) {
	
		ArgsProcessor ap = new ArgsProcessor(args);
		
		int playerCount = 0;
		int wager = 0;
		
		while (playerCount < minPlayers || playerCount > maxPlayers ) {
			playerCount = ap.nextInt("How many people are playing?");
		}
		
		BlackJackGame game = new BlackJackGame(playerCount);  
			
		/**
		 * PLACING BETS 
		 */
		for (Player player: game.players) {
			wager = ap.nextInt("How much would player " + player.getNumber() + " like to wager? It must be between $2 and $500.");
			if (wager < minWager) {
				wager = minWager;
			} else if (wager > maxWager){
				wager = maxWager;
			}
			player.setWager(wager);
		}
	
		/**
		 * THE SHUFFLE AND CUT
		 */
		game.cut(); 
		for (Player player: game.players) { //Calculate the intial counts for each player
			playerCount = game.initialCount(player);
		}	
		
		/**
		 * CHECK NATURALS
		 */
		int dealerCount = game.initialCount(game.dealer);
		if (dealerCount == blackJack && game.naturalList.size() > 0 ) {
				for (Player player: game.naturalList) {
					System.out.println("Player " + player.getNumber() + " won " + player.getWager() + " dollars! They tied with the dealer!");
				}
				System.out.println("Everyone else lost there wagers!");
				System.exit(0);
		} 
		else if (dealerCount == blackJack) {
				System.out.println("The dealer got a Black Jack! Everyone loses their wagers!");
				System.exit(0);
		} 
		else if (game.naturalList.size() > 0) {
			for (Player player: game.naturalList) {
				player.setWager( ((double) player.getWager() * winFactor) );
				System.out.println("Player " + player.getNumber() + " got a natural! They won " + player.getWager() + " dollars!");
			}
			System.exit(0); 
		}
		
		
		/**
		 * THE PLAY
		 */
		for (Player player: game.players) {
			int count = 0;
			boolean bust = false;
			boolean stand = false;
			System.out.println();
			while (!bust && !stand) {
				String hit = ap.nextString("Player " + player.getNumber() + ", would you like to hit? If so type 'Hit'. If you would like to stand, type anything else.");
				if (hit.equals("Hit") || hit.equals("hit")) {
					hit = "";
					System.out.println("Player " + player.getNumber() + ": ");
					game.gameDeck.deal(player, 1, true);
					count = game.recalculateCount(player);
				} else {
					stand = true;
					System.out.println("Player " + player.getNumber() + " stood!");
					game.standList.add(player);
				}
				if (count > blackJack) {
					bust = true;
					System.out.println("Player " + player.getNumber() + " went bust! They lost " + player.getWager() + " dollars!");
				}
				if ( count == blackJack) {
					stand = true;
					System.out.println("Player " + player.getNumber() + " got BlackJack!");
				}
			}	
		}
		
		/**
		 * THE DEALER'S PLAY
		 */
		Card faceDownCard = game.dealer.reveal().get(1);
		System.out.println();
		System.out.println("The dealer will now flip over their second card:");
		faceDownCard.peak();
		boolean seventeenUp;
		if (dealerCount < seventeenCount) {
			seventeenUp = false;
			System.out.println("Dealer's Play: ");
		} else {
			seventeenUp = true;
		}
		
		while (!seventeenUp) {
			game.gameDeck.deal(game.dealer, 1, true);
			dealerCount = game.recalculateCount(game.dealer);
			if (dealerCount > blackJack) {
				System.out.println("The dealer went bust!");
				game.dealerBust = true;
				seventeenUp = true;
			} else if (dealerCount > seventeenCount) {
				System.out.println("The dealer will stand, their count is " + dealerCount + ".");
				seventeenUp = true;
			}
		}
		
		/**
		 * THE SETTLEMENT
		 */
		System.out.println();
		if (game.bustList.size() == playerCount) {
			System.out.println("Everyone went bust! The dealer collects everyone's wagers!");
		} 
		for (Player player: game.standList) {
			if (game.dealerBust) {
				System.out.println("Player " + player.getNumber() + " won " + player.getWager() * 2 + " dollars.");
			} else if (dealerCount > game.recalculateCount(player)) {
				System.out.println("The dealer has a higher total. Player " + player.getNumber() + " loses " + player.getWager() + " dollars.");
			} else if (dealerCount == game.recalculateCount(player)) {
				System.out.println("Player " + player.getNumber() + " and the dealer tied. Nothing will be paid out or collected.");
			} else {
				System.out.println("Player " + player.getNumber() + " has a higher total than the dealer! They won " + player.getWager() * 2 + " dollars.");
			}
		}
	} 
}


