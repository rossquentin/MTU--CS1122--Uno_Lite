import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.util.Collections;

/**
 * A class which just hosts the main method for this game.
 *
 * This should read in the deck from a file called deck.txt!
 * The format is in the assignment description, and an example is provided for testing.
 *
 * Each turn, a player should play the first legal card in their hand.
 *
 * This will try to play the game. It is text based. Try to type the card you want to play
 * based on the number preceding the card.
 */

public class PlayGame{

	/*
	 * This map is used for mapping characters to strings for the sake of ease.
	 */
	private static final HashMap<Character, String> cardConvert = new HashMap<Character, String>();

	// Static code runs at the program's startup. I'm using this to setup the map above.
	static{
		cardConvert.put('w', "Wild");
		cardConvert.put('r', "Red");
		cardConvert.put('y', "Yellow");
		cardConvert.put('b', "Blue");
		cardConvert.put('g', "Green");
	}

	/**
	 * A method which prompts for input from stdin after printing options.
	 *
	 * @param player 	the index of the current player
	 * @param card		the top card of the uno game
	 * @param hand		the hand of the current player
	 * @param stdin		a scanner pointing to standard input
	 * @return 		a legal option selected by the player
	 */
	public static int promptInput(int player, UnoCard card, List<UnoCard> hand, Scanner stdin){
		System.out.printf("--------- Player %d's turn ---------%n", player);
		System.out.println("Current top card is a " + card);
		if (card instanceof UnoWildCard){
			System.out.println("   The wild was set to " + card.getColor());
		}
		System.out.println("What card would you like to play? Enter the index of the card below.");
		for(int i = 0; i < hand.size(); i++){
			System.out.printf("%d: %s%n", i, hand.get(i));
		}

		//prompt for input until you get something legal
		int retval = -1;
		while(retval < 0 || retval >= hand.size()){
			System.out.print("What card would you like to play (illegal choice = +2 cards): ");
			try{
				retval = stdin.nextInt();
			} 
			catch (NoSuchElementException e) {} //Don't do anything if I fail. Just try again.
		}

		return retval;
	}

	//Change this variable to load a different deck!
	public static final String myDeckList = "3_player_small_deck.txt";
	
	public static void main(String[] args){
		Scanner stdin = new Scanner(System.in);

		// Read in the deck file into a list here.
		ArrayList<UnoCard> deck = new ArrayList<UnoCard>();
		File inFile = new File(myDeckList);
		Scanner scanner;
		try{
			scanner = new Scanner(inFile);
		} 
		catch (FileNotFoundException e){
			System.err.println("deck.txt not found!");
			return;
		}

		//grab the number of players
		int playerCount;
		try{
			playerCount = scanner.nextInt();
		}
		catch (NoSuchElementException e){
			System.out.println("deck.txt format is off! Player count should be first!");
			return;
		}

		scanner.nextLine();

		// Add all of the cards to the deck.
		while(scanner.hasNextLine()){
			String tmp = scanner.nextLine();
			if (tmp.charAt(0) == 'w'){
				deck.add(new UnoWildCard("Wild", -1));
			}
			else if (tmp.charAt(2) == 's'){
				deck.add(new UnoSkipCard(cardConvert.get(tmp.charAt(0)), -1));
			}
			else{
				deck.add(new UnoNumberCard(cardConvert.get(tmp.charAt(0)), 
							Character.getNumericValue(tmp.charAt(2))));
			}
		}
		scanner.close();

        //Shuffle the deck
        Collections.shuffle(deck);

		//initialize game
		UnoGame game = new UnoGame(deck, playerCount);

		// Loop while there is no winner and the game's deck is not empty.
		while(game.getWinner() == -1 && !game.isDeckEmpty()){
			// get info about the turn
			int player = game.getCurrentPlayer();
			UnoCard topCard = game.getLastPlayed();
			List<UnoCard> hand = game.getPlayerHand();

			// get input
			int toPlay = promptInput(player, topCard, hand, stdin);
			UnoCard myCard = hand.remove(toPlay);

			// If playing the card wasn't legal, readd the card to hand
			// This doesn't draw two for me!
			if(myCard.play(game, player, stdin) == false){
				System.out.println("   Illegal card! +2");
				hand.add(myCard);
			} 
			game.nextPlayer();
		}
		if(game.getWinner() == -1){
			System.out.println("The game was a draw!");
		}
		else{
			System.out.println("Player " + game.getWinner() + " won!");
		}
		stdin.close();
	
	}
}
		
