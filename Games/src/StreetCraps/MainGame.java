package StreetCraps;

import java.util.Arrays;

import support.cse131.ArgsProcessor;

public class MainGame {
	
	static int Turn = 0;
	int die1, die2;
	int dieSum;
	int point;
	static int playerCount;
	static int shooter;
	static double wager;
	static double match;
	
	public void GameOver(String Outcome) { //This needs to check whose the shooter along with the outcome of the game
		 if (shooter == 0 || shooter == 1) {
			if (Outcome.equals("Win") ) {
				System.out.println("You passed! You won " + wager + " dollars!");
			}
			if (Outcome.equals("Win1") ) {
				System.out.println("You pointed up! You won " + wager + " dollars!");
			}
			if (Outcome.equals("Lose") ) {
				System.out.println("You crapped out! You lost " + wager + " dollars!");
			}
			if (Outcome.equals("Lose1") ) {
				System.out.println("You sevened out! You lost " + wager + " dollars!");
			}
		 } 
		 else {
			if (Outcome.equals("Win") ) {
				System.out.println("Player " + shooter + " passed! They won " + wager + " dollars! You lost " + match + " dollars!");
			}
			if (Outcome.equals("Win1") ) {
				System.out.println("Player " + shooter + " pointed up! They won " + wager + " dollars! You lost " + match + " dollars!");
			}
			if (Outcome.equals("Lose") ) {
				System.out.println("Player " + shooter + " crapped out! They lost " + wager + " dollars! You won " + match + " dollars!");
			}
			if (Outcome.equals("Lose1") ) {
				System.out.println("Player " + shooter  + " sevened out! They lost " + wager + " dollars! You won " + match + " dollars!");
			}
		 }	
	}
	
	public int[] Throw() {
		int[] dieValues = new int[3]; 
		double firstDieValue = Math.random() * (7-1) + 1;
		double secondDieValue = Math.random() * (7-1) + 1;
		dieSum = ((int) firstDieValue + (int) secondDieValue);
		dieValues[0] = (int) firstDieValue;
		dieValues[1] = (int) secondDieValue; 
		dieValues[2] = dieSum;
		return dieValues;
	}
	
	public void FirstRole() {
		Turn = 1;
		int dieValues[] = Throw(); 
		die1 = dieValues[0];  
		die2 = dieValues[1];
		dieSum = dieValues[2];
		if (Turn == 1) {
			if (dieSum == 7) {
				System.out.println(Turn +  ": " + die1 + " and " + die2);
				GameOver("Win");
			}
			else if (dieSum == 2 || dieSum == 3 || dieSum == 12) { //Something wrong with this or statement 
				System.out.println(Turn +  ": " + die1 + " and " + die2);
				GameOver("Lose");
			}
			else {
				point = dieSum;
				System.out.println(Turn +  ": " + die1 + " and " + die2);
				System.out.println("The point is " + point + "!");	
				Role();
			}	
		}
	}
	
	public void Role() {
		++Turn;
		int dieValues[] = Throw();
		die1 = dieValues[0];
		die2 = dieValues[1];
		dieSum = dieValues[2];
		if (dieSum == point) {
			System.out.println(Turn +  ": " + die1 + " and " + die2);
			GameOver("Win1");
			
		} 
		else if ( dieSum == 7) {
			System.out.println(Turn +  ": " + die1 + " and " + die2);
			GameOver("Lose1");
		} 
		else {
			System.out.println(Turn +  ": " + die1 + " and " + die2);
			Role();
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("Let's play craps!");
		
		ArgsProcessor ap = new ArgsProcessor(args);
		
		while (playerCount < 1) {
			playerCount = ap.nextInt("How many people are playing?");
		}
		
		if (playerCount > 0) {
			shooter = (int) (Math.random() * playerCount);
			if (shooter == 0 || shooter == 1) {
				while (wager < 1 ) {
				wager = ap.nextInt("You're the shooter for this match! How much will you wager?");
				System.out.println("You wagered " + wager + " dollars.");
				}
			} 
			else {
				wager = (int) (Math.random() * 100);
				System.out.println("Player " + shooter + " is the shooter! They wager " + wager + " dollars.");
				match = ( (double) ( (int)  (  ( wager / (playerCount -1) ) * 100 ) ) ) / 100; //Truncates decimal places after 100ths place
			}
		}
		
		MainGame game = new MainGame();
		game.FirstRole();
	}

}
