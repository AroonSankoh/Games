package StreetCraps;

import support.cse131.ArgsProcessor;

/**
 * 
 * @author Aroon Sankoh (aroonjsankoh@gmail.com)
 *
 */
public class MainGame {  
	
	private static final int max = 7, min = 1;
	private static final String pass = "firstRoundWin", pointUp = "Win", crap = "firstRoundLoss", sevenOut = "Loss";
	static int Turn = 0;
	int die1, die2, dieSum, point;
	static int playerCount, shooter;
	static double wager, losingBets;
	
	
	/**
	 * Executes a game over script when a player rolls an outcome (passes, pointsUp, crapsOut, sevensOut).
	 * @param Outcome the outcome of the game.
	 */
	public void GameOver(String Outcome) { 
		 if (playerCount == 1) {
			 switch (Outcome) {
				 case pass:
					 System.out.println("You passed! You won " + wager + " dollars!");
					 break;
				 case pointUp:
					 System.out.println("You pointed up! You won " + wager + " dollars!");
					 break;
				 case crap:
					 System.out.println("You crapped out! You lost " + wager + " dollars!");
					 break;
				 case sevenOut:
					 System.out.println("You sevened out! You lost " + wager + " dollars!");
					 break;
			 }
		 } 
		 else {
			 switch (Outcome) {
				 case pass:
					 System.out.println("Player " + shooter + " passed! They won " + wager + " dollars! You lost " + losingBets + " dollars!");
					 break;
				 case pointUp:
					 System.out.println("Player " + shooter + " pointed up! They won " + wager + " dollars! You lost " + losingBets + " dollars!");
					 break;
				 case crap:
					 System.out.println("Player " + shooter + " crapped out! They lost " + wager + " dollars! You won " + losingBets + " dollars!");
					 break;
				 case sevenOut:
					 System.out.println("Player " + shooter  + " sevened out! They lost " + wager + " dollars! You won " + losingBets + " dollars!");
					 break;
			 }
		 }
	}
	
	/**
	 * simulates the rolling two die.
	 * @return an array holding the values of the two rolled die and their sum. 
	 */
	public int[] Throw() {
		int[] dieValues = new int[3]; 
		double firstDieValue = Math.random() * (max-min) + min;
		double secondDieValue = Math.random() * (max-min) + min;
		dieSum = ((int) firstDieValue + (int) secondDieValue);
		dieValues[0] = (int) firstDieValue;
		dieValues[1] = (int) secondDieValue; 
		dieValues[2] = dieSum;
		return dieValues;
	}
	
	/**
	 * Exectues a script for the first role of the game.
	 */
	
	public void firstRole() {
		Turn = 1;
		int dieValues[] = Throw(); 
		die1 = dieValues[0];  
		die2 = dieValues[1];
		dieSum = dieValues[2];
		if (Turn == 1) {
			if (dieSum == 7) {
				System.out.println(Turn +  ": " + die1 + " and " + die2);
				GameOver(pass);
			}
			else if (dieSum == 2 || dieSum == 3 || dieSum == 12) {  
				System.out.println(Turn +  ": " + die1 + " and " + die2);
				GameOver(crap);
			}
			else {
				point = dieSum;
				System.out.println(Turn +  ": " + die1 + " and " + die2);
				System.out.println("The point is " + point + "!");	
				Role();
			}	
		}
	}
	
	/**
	 * Called in firstRole() and executes a script for every succeding role 
	 */
	public void Role() {
		++Turn;
		int dieValues[] = Throw();
		die1 = dieValues[0];
		die2 = dieValues[1];
		dieSum = dieValues[2];
		if (dieSum == point) {
			System.out.println(Turn +  ": " + die1 + " and " + die2);
			GameOver(pointUp);
			
		} 
		else if ( dieSum == 7) {
			System.out.println(Turn +  ": " + die1 + " and " + die2);
			GameOver(sevenOut);
		} 
		else {
			System.out.println(Turn +  ": " + die1 + " and " + die2);
			Role();
		}
	}
	
	/**
	 * Calculates how much each player needs to bet to match the shooters wager
	 * @param wagerAmount how much the shooter wagered 
	 * @param numPlayers the number of players
	 * @return each player's match 
	 */
	public static double match(double wagerAmount, int numPlayers) {
		return ( ( (double) ( (int)  (  ( wagerAmount / (numPlayers -1) ) * 100 ) ) ) / 100);
	}
	
	public static void main(String[] args) {
		
		System.out.println("Let's play craps!");
		
		ArgsProcessor ap = new ArgsProcessor(args);
		
		while (playerCount < 1) {
			playerCount = ap.nextInt("How many people are playing?");
		}
		
	
		shooter = (int) (Math.random() * playerCount);
		if (playerCount == 1) {
			while (wager < 1 ) {
				wager = ap.nextInt("You're the shooter for this match! How much will you wager?");
				System.out.println("You wagered " + wager + " dollars.");
				}
		}
		else {
			wager = (int) (Math.random() * 100);
			System.out.println("Player " + shooter + " is the shooter! They wager " + wager + " dollars.");
			losingBets = match(wager, playerCount); 
		}
		
		MainGame game = new MainGame();
		game.firstRole();
	}

}
